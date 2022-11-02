package pangan.ai.com.api.test_platform.busy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import pangan.ai.com.api.test_platform.model.BaseModel;
import pangan.ai.com.api.test_platform.model.EnumParam;

public class CommonBusy {
    public static Object parseJson(String json, Class<?> clazz) {
        // 非空验证
        if (StringUtils.isEmpty(json)) {
            System.out.println("params json string is empty :" + json);
            return null;
        }
        Object model0 = JSON.parseObject(json, clazz);
        // json验证
        if (null == model0) {
            System.out.println("params json string format wrong:" + json);
            return null;
        }
        BaseModel model = (BaseModel)model0;
        // 最基本参数验证
        if (StringUtils.isEmpty(model.getApiCode())
                || StringUtils.isEmpty( model.getAppId())
                || StringUtils.isEmpty(model.getUserName())
                || StringUtils.isEmpty(model.getToken())
                || null == model.getExecuteId()
                || null == model.getCaseId()
                || StringUtils.isEmpty(model.getDatabase())
                || StringUtils.isEmpty(model.getPartition())
        ) {
            System.out.println("params[apiCode;appId;userName;token;excuteId;caseId;database;table;partition] is need:" + json);
            return null;
        }
        return model;
    }

    public static EnumParam parseEnumParam(JSONObject obj) {
        return parseEnumParam(obj, false);
    }
    public static EnumParam parseEnumParam(JSONObject obj,boolean isAgg) {
        if(1 != obj.size()){
            return null;
        }
        String colName = obj.keySet().toArray()[0].toString();
        if(StringUtils.isEmpty(colName)){
            System.out.println("EnumParam : colName is empty = " +obj);
            return null;
        }
        JSONArray enums = obj.getJSONArray(colName);
        if (null == enums || enums.isEmpty()) {
            System.out.println("EnumParam : enums is empty = " +obj);
            return null;
        }

        for (String s:enums.toJavaList(String.class)) {
            if (StringUtils.isEmpty(s)){
                System.out.println("EnumParam : enum is empty = " +obj);
                return null;
            }
            // 判断是否指定聚合函数
            if(isAgg){
                boolean checkAgg = false;
                for (String func : BaseModel.AGG_FUNCTIONS) {
                    if (func.equals(s)) {
                        checkAgg = true;
                        break;
                    }
                }
                if(!checkAgg){
                    System.out.println("EnumParam : enum is empty = " +obj);
                    return null;
                }
            }

        }
        return new EnumParam(colName, enums);
    }

    public static String enumConfSql(EnumParam enumParam) {
        StringBuilder sb = new StringBuilder();
        for(String e:enumParam.getEnums().toJavaList(String.class)){
            sb.append(String.format(" or %s='%s'", enumParam.getColName(), e));
        }
        return sb.substring(4);
    }
}