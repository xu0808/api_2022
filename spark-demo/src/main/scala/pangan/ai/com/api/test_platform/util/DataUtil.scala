package pangan.ai.com.api.test_platform.util

import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.{struct, to_json}

object DataUtil {
  // 异常采样默认数值为10
  val exampleNum = 10

  // isSum 计算异常数使用sum还是count
  def exceptParse(df: DataFrame, isSum: Boolean): JSONObject = {
    val exception = new JSONObject
    exception.put("total", 0)
    val count = df.count()
    if (0 == count) {
      return exception
    }
    var total = count
    if (isSum) {
      exception.put("total", DataUtil.countParse(df.agg(Map("num" -> "sum"))))
    } else {
      exception.put("total", count)
    }
    exception.put("info", DataUtil.rowsToJson(df.limit(exampleNum).toDF()))
    return exception
  }

  // 单行结果json输出
  def countParse(df: DataFrame): Long = {
    return df.head().getLong(0)
  }

  // 单行结果json输出
  def rowToJson(df: DataFrame): JSONObject = {
    val array = rowsToJson(df: DataFrame)
    if (0 == array.size()) {
      return new JSONObject
    }
    return array.getJSONObject(0)
  }

  // 多行结果json输出
  def rowsToJson(df: DataFrame): JSONArray = {
    var array = new JSONArray
    val count = df.count()
    if (0 == count) {
      return array
    }
    val jsonDf = df.select(to_json(struct("*")) as 'json)
    for (row <- jsonDf.collect()) {
      array.add(JSON.parseObject(row.getString(0)))
    }
    return array
  }

  def init(spark: SparkSession) {
    val data0 = spark.read.format("json").load("data/people.json")
    data0.createOrReplaceTempView("data0")
    val data = spark.sql("select id,name,age,day,'20200906' as dt from data0")
    data.createOrReplaceTempView("data")
  }
}