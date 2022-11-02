package pangan.ai.com.api.test_platform

import com.alibaba.fastjson.{JSONArray, JSONObject}
import org.apache.spark.sql.{DataFrame, SparkSession}
import pangan.ai.com.api.test_platform.busy.EnumFeatureBusy
import pangan.ai.com.api.test_platform.model.EnumFeatureSqls
import pangan.ai.com.api.test_platform.util.DataUtil

object EnumFeatureJob {

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("EnumFeatureJob")
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
    val sqls: EnumFeatureSqls = EnumFeatureBusy.createSql()
    val json = new JSONObject
    json.put("col", "age")
    json.put("partition", "20200906")
    // 完整个数(全部行数)
    json.put("count", spark.sql(sqls.getTotalSql).head().getLong(0))
    // 缺失个数
    json.put("nullNum", spark.sql(sqls.getIsNullNumSql).head().getLong(0))
    // enum_values 取值范围
    val checkEnumSql = sqls.getCheckEnumSql
    if (null != checkEnumSql) {
      json.put("checkEnum", DataUtil.exceptParse(spark.sql(checkEnumSql), true))
    }
    // 最多计数、最少计数、分类计数、分类占比
    // 分组统计（仅包含有效取值，最多20个）
    val groupDf = spark.sql(sqls.getGroupSql).persist()
    groupDf.createOrReplaceTempView("group")
    val aggRow = spark.sql("select max(num) max,min(num) min,sum(num) sum from group").head()
    json.put("maxEnum", aggRow.getLong(0))
    json.put("minEnum", aggRow.getLong(1))
    val total = aggRow.getLong(2)
    json.put("totalEnum", total)
    val enums = new JSONArray
    for (row <- groupDf.collect()) {
      val jsonEnum = new JSONObject
      jsonEnum.put("enum", row.get(0))
      jsonEnum.put("num", row.getLong(1))
      jsonEnum.put("ratio", row.getLong(1) / total)
      enums.add(jsonEnum)
    }
    json.put("ratioEnum", enums)
    print(json)
  }
}