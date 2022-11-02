package com.pingan.ai.model.testplatform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataAnalysisModel extends BaseModel {
    public Integer needCount;
    public Integer checkKey;
    public JSONArray partitions;
    public JSONObject aggs;
    public JSONObject partsSum;
    public JSONObject checkLength;

    public Integer getNeedCount() {
        return needCount;
    }

    public void setNeedCount(Integer needCount) {
        this.needCount = needCount;
    }

    public Integer getCheckKey() {
        return checkKey;
    }

    public void setCheckKey(Integer checkKey) {
        this.checkKey = checkKey;
    }

    public JSONArray getPartitions() {
        return null == partitions?new JSONArray():partitions;
    }

    public void setPartitions(JSONArray partitions) {
        this.partitions = partitions;
    }

    public JSONObject getAggs() {
        return null==aggs?new JSONObject():aggs;
    }

    public void setAggs(JSONObject aggs) {
        this.aggs = aggs;
    }

    public JSONObject getPartsSum() {
        return null==partsSum?new JSONObject():partsSum;
    }

    public void setPartsSum(JSONObject partsSum) {
        this.partsSum = partsSum;
    }

    public JSONObject getCheckLength() {
        return checkLength;
    }

    public void setCheckLength(JSONObject checkLength) {
        this.checkLength = checkLength;
    }
}