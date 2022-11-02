package com.pingan.ai.model.testplatform;

public class EnumFeatureSqls {
    public String totalSql;
    public String isNullNumSql;
    public String checkEnumSql;
    public String groupSql;

    public String getTotalSql() {
        return totalSql;
    }

    public void setTotalSql(String totalSql) {
        this.totalSql = totalSql;
    }

    public String getIsNullNumSql() {
        return isNullNumSql;
    }

    public void setIsNullNumSql(String isNullNumSql) {
        this.isNullNumSql = isNullNumSql;
    }

    public String getCheckEnumSql() {
        return checkEnumSql;
    }

    public void setCheckEnumSql(String checkEnumSql) {
        this.checkEnumSql = checkEnumSql;
    }

    public String getGroupSql() {
        return groupSql;
    }

    public void setGroupSql(String groupSql) {
        this.groupSql = groupSql;
    }

    @Override
    public String toString() {
        return "EnumFeatureApiSql{" +  '\n' +
                "totalSql='" + totalSql + '\'' +  '\n' +
                ", isNullNumSql='" + isNullNumSql + '\'' +  '\n' +
                ", checkEnumSql='" + checkEnumSql + '\'' +  '\n' +
                ", groupSql='" + groupSql + '\'' +  '\n' +
                '}';
    }
}