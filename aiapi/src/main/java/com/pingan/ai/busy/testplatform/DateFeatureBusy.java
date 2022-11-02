package com.pingan.ai.busy.testplatform;

import org.apache.commons.lang3.StringUtils;
import com.pingan.ai.model.testplatform.*;

public class DateFeatureBusy {
    public static final String json = "{\n" +
            "    \"apiCode\":\"AI0004\",\n" +
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
            "    \"col\":\"day\",\n" +
            "    \"checkRange\":\"day>'20200906' and day<'20200909'\"\n" +
            "}";
    public static void main(String[] args) {
        EnumFeatureSqls sqls = createSql();
        System.out.println("sqls = " + sqls.toString());
    }

    public static EnumFeatureSqls createSql() {
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
        // 完整个数(全部行数)
        String totalSql = String.format("select count(1) num %s", fromSql);
        // 缺失个数
        String isNullNumSql = String.format("select count(1) is_null_num %s and %s is null", fromSql,col);

        // enum_values 取值范围
        String checkEnumSql = null;
        String checkRange = model.getCheckRange();
        if(!StringUtils.isEmpty(checkRange)){
            String exceptKey = !StringUtils.isEmpty(model.getKeyCol())?String.format("max(%s) %s,", model.getKeyCol(), model.getKeyCol()):"";
            checkEnumSql = String.format("select * from (select %s,count(1) num %s and !(%s) group by %s) limit 10",col , fromSql, checkRange, col);
        }

        // 最多计数、最少计数、分类计数、分类占比
        // 分组统计（仅包含有效取值，最多20个）
        checkRange = null==checkRange? String.format("and (%s)",checkRange):"";
        String groupSql = String.format("select *  from (select %s col,count(1) num %s %s group by %s) limit 20",col , fromSql,checkRange, col);

        EnumFeatureSqls sqls = new EnumFeatureSqls();
        sqls.setTotalSql(totalSql);
        sqls.setIsNullNumSql(isNullNumSql);
        sqls.setCheckEnumSql(checkEnumSql);
        sqls.setGroupSql(groupSql);
        return sqls;
    }
}
