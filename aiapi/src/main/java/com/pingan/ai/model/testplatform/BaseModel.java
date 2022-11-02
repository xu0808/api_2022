package com.pingan.ai.model.testplatform;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
@Data
public class BaseModel {
    public static final String DEFAULT_PATITION = "dt";
    public static final String[] AGG_FUNCTIONS = {"avg","max","min"};
    public String apiCode;
    public String appId;
    public String userName;
    public String token;
    public Long executeId;
    public Long caseId;
    public String database;
    public String table;
    public String partCol;
    public String partition;
    public String keyCol;

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExecuteId() {
        return executeId;
    }

    public void setExecuteId(Long executeId) {
        this.executeId = executeId;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPartCol() {
        return partCol;
    }

    public void setPartCol(String partCol) {
        this.partCol = partCol;
    }

    public String getPartition() {
        return StringUtils.isEmpty(partition)? DEFAULT_PATITION:partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getKeyCol() {
        return keyCol;
    }

    public void setKeyCol(String keyCol) {
        this.keyCol = keyCol;
    }
}