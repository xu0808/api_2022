package pangan.ai.com.api.test_platform

import com.alibaba.fastjson.JSONObject
import org.apache.commons.lang.StringUtils
import org.apache.spark.sql.SparkSession
import pangan.ai.com.api.test_platform.busy.DataAnalysisBusy
import pangan.ai.com.api.test_platform.model.DataAnalysisSqls
import pangan.ai.com.api.test_platform.util.DataUtil

object DataAnalysisJob {

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder()
      .appName("DataAnalysisJob")
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
    val json = new JSONObject
    val sqls: DataAnalysisSqls = DataAnalysisBusy.createSql()
    print(sqls)
    //agg 指定列的avg、max、min
    if (!StringUtils.isEmpty(sqls.aggSql)) {
      val agg = new JSONObject
      agg.put(sqls.aggKey, DataUtil.rowToJson(spark.sql(sqls.aggSql)))
      json.put("agg", agg)
    }

    // 多分区分组sum
    if (!StringUtils.isEmpty(sqls.partsSumSql)) {
      json.put("partsSum", DataUtil.rowsToJson(spark.sql(sqls.partsSumSql)))
    }

    //count(单分区)
    if (!StringUtils.isEmpty(sqls.countPartSql)) {
      json.put("partCount", DataUtil.countParse(spark.sql(sqls.countPartSql)))
    }
    //count(多分区)
    if (!StringUtils.isEmpty(sqls.countPartsSql)) {
      json.put("partsCount", DataUtil.rowsToJson(spark.sql(sqls.countPartsSql)))
    }

    // keyUniqueSql
    if (!StringUtils.isEmpty(sqls.checkKeySql)) {
      json.put("checkKey", DataUtil.exceptParse(spark.sql(sqls.checkKeySql), true))
    }

    //非法length 列长度值清单
    if (!StringUtils.isEmpty(sqls.checkLengthSql)) {
      json.put("checkLength", DataUtil.exceptParse(spark.sql(sqls.checkLengthSql), true))
    }
    print(json)
  }
}
