package pangan.ai.com.api.test_platform.busy;

import org.apache.commons.lang.StringUtils;
import pangan.ai.com.api.test_platform.model.*;

public class NumberFeatureBusy {

    public static void main(String[] args) {
        NumberFeatureSqls sqls = createSql();
        System.out.println("sqls = " + sqls.toString());
    }

    public static NumberFeatureSqls createSql() {
        String json = "{\n" +
                "    \"apiCode\":\"AI0002\",\n" +
                "    \"appId\":30201,\n" +
                "    \"userName\":\"zhangjiayan\",\n" +
                "    \"token\":\"zhangjiayan\",\n" +
                "    \"executeId\":1,\n" +
                "    \"caseId\":1,\n" +
                "    \"database\":\"default\",\n" +
                "    \"table\":\"data\",\n" +
                "    \"partCol\":\"dt\",\n" +
                "    \"partition\":\"20200906\",\n" +
                "    \"keyCol\":\"id\",\n" +
                "    \"col\":\"age\",\n" +
                "    \"checkRange\":\"age>'3' and age<'12'\"\n" +
                "}";
        System.out.println("json = " + json);
        /*
         * 一、解析josn参数
         */
        FeatureModel model =  (FeatureModel)CommonBusy.parseJson(json, FeatureModel.class);
        if(null ==model){
            return null;
        }
        // 最基本参数验证
        String col = model.getCol();
        if (StringUtils.isEmpty(col)){
            System.out.println("params[col] is need:" + col);
            return null;
        }

        /*
         * 二、组装sql
         */
        // 默认fromSql
        String partCol = model.getPartCol();
        //String table = String.format("%s.%s", model.getDatabase(), model.getTable());
        // 临时直接使用表名
        String table = model.getTable();
        String partWhere = String.format("%s='%s'", partCol, model.getPartition());
        String fromSql = String.format("from %s where %s", table, partWhere);
        // 完整个数、最大值、最小值、极差、平均数、标准差、偏度、峰度
        String aggSql = String.format("select count(%s) num,max(%s) max,min(%s) min,max(%s)-min(%s) range,avg(%s) avg,stddev(%s) std,skewness(%s) skewness,kurtosis(%s) kurtosis %s", col, col,col,col, col,col,col,col, col, fromSql);
        // 众数、中位数、无效值、缺失个数
        // 众数(至少出现1次，最多取并列最多的十个数值)
        String modeSql = String.format("select * from (select %s mode,count(1) num %s group by %s having count(1)>1) order by num desc limit 10",col, fromSql,col);
        // 中位数(偶数个取中间两个平均值)
        String medianSql = String.format("select %s median,row_number() over (partition by %s order by %s) rn %s",col,col, col, fromSql);

        String checkRange = model.getCheckRange();
        String checkRangeSql = null;
        if(!StringUtils.isEmpty(checkRange)){
            String exceptKey = !StringUtils.isEmpty(model.getKeyCol())?String.format("%s,", model.getKeyCol()):"";
            checkRangeSql = String.format("select %s%s %s and !(%s)", exceptKey,col,fromSql, checkRange);
        }
        // 缺失个数
        String isNullNumSql = String.format("select count(1) is_null_num %s and %s is null", fromSql,col);

        NumberFeatureSqls sqls = new NumberFeatureSqls();
        sqls.setAggSql(aggSql);
        sqls.setModeSql(modeSql);
        sqls.setMedianSql(medianSql);
        sqls.setCheckRangeSql(checkRangeSql);
        sqls.setIsNullNumSql(isNullNumSql);
        return sqls;
    }
}