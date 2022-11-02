package com.pingan.ai.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "BaseParam")
public class BaseParam implements Serializable {
    private static final long serialVersionUID = 8841143276870083920L;
    @ApiModelProperty(value = "apiCode")
    @NotBlank(message = "接口编号不能为空")
    private String apiCode;
    @ApiModelProperty(value = "appId")
    @NotBlank(message = "应用编号")
    private String appId;
    @ApiModelProperty(value = "userName")
    @NotBlank(message = "登录用户名不能为空")
    private String userName;
    @ApiModelProperty(value = "token")
    @NotBlank(message = "认证token")
    private String token;
    @ApiModelProperty(value = "executeId")
    @NotNull(message = "测试平台执行标识不能为null")
    private Long executeId;
    @ApiModelProperty(value = "caseId")
    @NotNull(message = "测试平台案例标识不能为null")
    private Long caseId;
    @ApiModelProperty(value = "database")
    @NotBlank(message = "hive库不能为空")
    private String database;
    @ApiModelProperty(value = "table")
    @NotBlank(message = "hive表不能为空")
    private String table;
    @ApiModelProperty(value = "partCol")
    private String partCol;
    @ApiModelProperty(value = "partition")
    @NotBlank(message = "分区不能为空")
    private String partition;
    @ApiModelProperty(value = "keyCol")
    private String keyCol;
}