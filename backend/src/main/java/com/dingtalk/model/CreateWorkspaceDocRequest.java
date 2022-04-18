package com.dingtalk.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author qwh.shuwu
 * @date 2022/3/16 3:18 下午
 */
@Data
public class CreateWorkspaceDocRequest {

    /**
     * 文档归属的空间Id
     */
    @NotBlank(message = "workspaceId must not be blank")
    private String workspaceId;

    /**
     * 空间名称
     */
    @NotNull(message = "node name must not be null")
    @Size(min = 1, max = 50)
    private String name;

    /**
     * 创建的文档类型
     */
    @NotBlank(message = "docType must not be blank")
    private String docType;

    /**
     * 操作用户unionId
     */
    @NotBlank(message = "operatorId must not be blank")
    private String operatorId;

}
