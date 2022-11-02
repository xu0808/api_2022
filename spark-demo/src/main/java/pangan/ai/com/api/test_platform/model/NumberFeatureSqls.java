package pangan.ai.com.api.test_platform.model;

public class NumberFeatureSqls {
    public String aggSql;
    public String modeSql;
    public String medianSql;
    public String checkRangeSql;
    public String isNullNumSql;

    public String getAggSql() {
        return aggSql;
    }

    public void setAggSql(String aggSql) {
        this.aggSql = aggSql;
    }

    public String getModeSql() {
        return modeSql;
    }

    public void setModeSql(String modeSql) {
        this.modeSql = modeSql;
    }

    public String getMedianSql() {
        return medianSql;
    }

    public void setMedianSql(String medianSql) {
        this.medianSql = medianSql;
    }

    public String getCheckRangeSql() {
        return checkRangeSql;
    }

    public void setCheckRangeSql(String checkRangeSql) {
        this.checkRangeSql = checkRangeSql;
    }

    public String getIsNullNumSql() {
        return isNullNumSql;
    }

    public void setIsNullNumSql(String isNullNumSql) {
        this.isNullNumSql = isNullNumSql;
    }

    @Override
    public String toString() {
        return "NumberFeatureSqls{"  +  '\n' +
                "aggSql='" + aggSql + '\'' +  '\n' +
                ", modeSql='" + modeSql + '\'' +  '\n' +
                ", medianSql='" + medianSql + '\'' +  '\n' +
                ", checkRangeSql='" + checkRangeSql + '\'' +  '\n' +
                ", isNullNumSql='" + isNullNumSql + '\'' +  '\n' +
                '}';
    }
}
