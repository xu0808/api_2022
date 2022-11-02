package pangan.ai.com.api.test_platform.model;

import com.alibaba.fastjson.JSONArray;

public class EnumParam {
    String colName;
    JSONArray enums;

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public JSONArray getEnums() {
        return enums;
    }

    public void setEnums(JSONArray enums) {
        this.enums = enums;
    }

    public EnumParam(String col, JSONArray array){
        colName = col;
        enums= array;
    }
}
