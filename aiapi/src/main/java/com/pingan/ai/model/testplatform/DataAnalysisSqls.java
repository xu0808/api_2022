package com.pingan.ai.model.testplatform;

public class DataAnalysisSqls extends BaseModel {
    public String aggKey;
    public String aggSql;
    public String partsSumSql;
    public String countPartSql;
    public String countPartsSql;
    public String checkKeySql;
    public String checkLengthSql;

    public String getAggKey() {
        return aggKey;
    }

    public void setAggKey(String aggKey) {
        this.aggKey = aggKey;
    }

    public String getAggSql() {
        return aggSql;
    }

    public void setAggSql(String aggSql) {
        this.aggSql = aggSql;
    }

    public String getPartsSumSql() {
        return partsSumSql;
    }

    public void setPartsSumSql(String partsSumSql) {
        this.partsSumSql = partsSumSql;
    }

    public String getCountPartSql() {
        return countPartSql;
    }

    public void setCountPartSql(String countPartSql) {
        this.countPartSql = countPartSql;
    }

    public String getCountPartsSql() {
        return countPartsSql;
    }

    public void setCountPartsSql(String countPartsSql) {
        this.countPartsSql = countPartsSql;
    }

    public String getCheckKeySql() {
        return checkKeySql;
    }

    public void setCheckKeySql(String checkKeySql) {
        this.checkKeySql = checkKeySql;
    }

    public String getCheckLengthSql() {
        return checkLengthSql;
    }

    public void setCheckLengthSql(String checkLengthSql) {
        this.checkLengthSql = checkLengthSql;
    }

    @Override
    public String toString() {
        return "DataAnalysisSqls{" + '\n'+
                "aggKey='" + aggKey + '\'' + '\n'+
                ", aggSql='" + aggSql + '\''+ '\n'+
                ", partsSumSql='" + partsSumSql + '\'' + '\n'+
                ", countPartSql='" + countPartSql + '\'' + '\n'+
                ", countPartsSql='" + countPartsSql + '\'' + '\n'+
                ", checkKeySql='" + checkKeySql + '\'' + '\n'+
                ", checkLengthSql='" + checkLengthSql + '\'' + '\n'+
                '}';
    }
}