package pangan.ai.com.api.test_platform.model;

import com.alibaba.fastjson.JSONArray;

public class FeatureModel extends BaseModel {
    public String col;
    public String checkRange;
    public JSONArray checkEnum;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getCheckRange() {
        return checkRange;
    }

    public void setCheckRange(String checkRange) {
        this.checkRange = checkRange;
    }

    public JSONArray getCheckEnum() {
        return null!=checkEnum?checkEnum:new JSONArray();
    }

    public void setCheckEnum(JSONArray checkEnum) {
        this.checkEnum = checkEnum;
    }
}