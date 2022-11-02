package com.pingan.ai.busy.testplatform;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import com.pingan.ai.model.testplatform.*;

public class DataAnalysisBusy {

    private static final String[] AGG_FUNCTIONS = {"avg", "max", "min"};
    public static final String json = "{\n" +
//            "    \"apiCode\":\"AI0001\",\n" +
            "    \"appId\":30201,\n" +
            "    \"userName\":\"zhangjiayan\",\n" +
            "    \"token\":\"zhangjiayan\",\n" +
            "    \"executeId\":1,\n" +
            "    \"caseId\":1,\n" +
            "    \"database\":\"default\",\n" +
            "    \"table\":\"data\",\n" +
            "    \"partCol\":\"dt\",\n" +
            "    \"partition\":\"20200906\",\n" +
            "    \"needCount\":1,\n" +
            "    \"checkKey\":1,\n" +
            "    \"keyCol\":\"id\",\n" +
            "    \"partitions\":[\"20200906\",\"20200907\"],\n" +
            "    \"aggs\":{\"age\":[\"avg\",\"max\",\"min\"]},\n" +
            "    \"partsSum\":{\"age\":[\"20200906\",\"20200907\"]},\n" +
            "    \"checkLength\":{\"name\":[12,13]}\n" +
            "}";

    public static void main(String[] args) {
        DataAnalysisSqls sqls = createSql();
        System.out.println("sqls = " + sqls.toString());
    }

    public static DataAnalysisSqls createSql() {
        System.out.println("json = " + json);
        /*
         * 一、解析josn参数
         */
        DataAnalysisModel model =  (DataAnalysisModel)CommonBusy.parseJson(json, DataAnalysisModel.class);
        if(null ==model){
            return null;
        }

        /*
         * 二、组装sql
         */
        DataAnalysisSqls sqls = new DataAnalysisSqls();
        boolean existSql = false;
        // 默认fromSql
        String partCol = model.getPartCol();
        //String table = String.format("%s.%s", model.getDatabase(), model.getTable());
        // 临时直接使用表名
        String table = model.getTable();
        String partWhere = String.format("%s='%s'", partCol, model.getPartition());
        String fromSql = String.format("from %s where %s", table, partWhere);

        //agg 指定列的avg、max、min
        EnumParam colAggs = CommonBusy.parseEnumParam(model.getAggs(), true);
        if(null != colAggs){
            String col = colAggs.getColName();
            StringBuilder sb = new StringBuilder();
            for(String function:colAggs.getEnums().toJavaList(String.class)){
                sb.append(String.format(",%s(%s) as %s", function, col, function));
            }
            sqls.setAggKey(col);
            sqls.setAggSql(String.format("select %s %s", sb.substring(1), fromSql));
            existSql = true;
        }

        // 多分区分组sum
        EnumParam partsSum = CommonBusy.parseEnumParam(model.getPartsSum());
        if(null != partsSum){
            String partsConf = CommonBusy.enumConfSql(partsSum);
            sqls.setPartsSumSql(String.format("select %s,sum(%s) sum from %s where %s group by %s", partCol, partsSum.getColName(), table,partsConf, partCol));
            existSql = true;
        }

        //count(单分区)
        if (1 == model.getNeedCount()) {
            sqls.setCountPartSql(String.format("select count(1) num %s", fromSql));
            existSql = true;
        }

        //key_unique 主键唯一
        String keyCol = model.getKeyCol();
        if (1 == model.getCheckKey() && !StringUtils.isEmpty(keyCol)) {
            sqls.setCheckKeySql(String.format("select %s,count(1) num %s group by id having count(1)>1", keyCol, fromSql));
            existSql = true;
        }

        //count(多分区)
        JSONArray partitions = model.getPartitions();
        if (0 < partitions.size()) {
            String partsConf = CommonBusy.enumConfSql(new EnumParam(partCol, model.getPartitions()));
            String countPartsSql = String.format("select %s,count(1) num from %s where %s group by %s", partCol, table, partsConf, partCol);
            sqls.setCountPartsSql(countPartsSql);
            existSql = true;
        }

        //非法length 列长度值清单
        EnumParam checkLength = CommonBusy.parseEnumParam(model.getPartsSum());
        if(null != checkLength){
            StringBuilder sb = new StringBuilder();
            String exceptKey = !StringUtils.isEmpty(keyCol)?String.format("max(%s) %s,", keyCol, keyCol):"";
            String col = checkLength.getColName();
            for(String len: checkLength.getEnums().toJavaList(String.class)){
                sb.append(String.format("and length(%s)!='%s'", col, len));
            }
            sqls.setCheckLengthSql(String.format("select %slength(%s) length,count(1) num %s %s group by length(%s)", exceptKey, col, fromSql, sb.toString(), col));
            existSql = true;
        }

        if (!existSql) {
            System.out.println("no sql from params");
            return null;
        }

        return sqls;
    }
}