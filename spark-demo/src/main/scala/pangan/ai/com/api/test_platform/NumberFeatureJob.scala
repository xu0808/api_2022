package pangan.ai.com.api.test_platform

import com.alibaba.fastjson.JSONArray
import org.apache.spark.sql.SparkSession
import pangan.ai.com.api.test_platform.busy.NumberFeatureBusy
import pangan.ai.com.api.test_platform.model.NumberFeatureSqls
import pangan.ai.com.api.test_platform.util.DataUtil

object NumberFeatureJob {

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("NuberFeatureJob")
      .master("local[1]")
      .getOrCreate()

    sqlExcute(spark)
    spark.stop()
  }

  def sqlExcute(spark: SparkSession) {
    DataUtil.init(spark)
    api(spark)

  }

  def api(spark: SparkSession): Unit = {
    val sqls: NumberFeatureSqls = NumberFeatureBusy.createSql()
    //    // 完整个数、最大值、最小值、极差、平均数
    val json = DataUtil.rowToJson(spark.sql(sqls.getAggSql))
    json.put("col", "age")
    json.put("partition", "20200906")
    // 众数、中位数、无效值、缺失个数
    // 众数(至少出现1次，最多取并列最多的十个数值)
    val modeDf = spark.sql(sqls.getModeSql)
    var mode = new JSONArray()
    if(modeDf.count()>0){
      val max = modeDf.head().getLong(1)
      for(row <- modeDf.collect()){
        val num = row.getLong(1)
        if(num == max){
          mode.add(row.get(0))
        }
      }
    }
    json.put("mode", mode)
    // 中位数(偶数个取中间两个平均值)
    val medianDf = spark.sql(sqls.getMedianSql)
    medianDf.createOrReplaceTempView("median")
    val total = medianDf.count()
    val midRn:Long = total/2
    var whereConf = s"where rn='$midRn'"
    if(total%2==0){
      whereConf = s"$whereConf or rn='${midRn-1}'"
    }
    val median = spark.sql(s"select avg(median) from median $whereConf").head().get(0)
    json.put("median", median)

    //无效值
    val checkRangeSql = sqls.getCheckRangeSql
    if(null !=checkRangeSql ){
      json.put("checkRange", DataUtil.exceptParse(spark.sql(sqls.getCheckRangeSql), false))
    }
    // 缺失个数
    json.put("nullNum", spark.sql(sqls.getIsNullNumSql).head().getLong(0))
    print(json)
  }
}