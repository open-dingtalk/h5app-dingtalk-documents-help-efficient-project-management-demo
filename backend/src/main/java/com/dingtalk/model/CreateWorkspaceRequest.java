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
public class CreateWorkspaceRequest {

    /**
     * 空间名称
     */
    @NotNull(message = "workspace name must not be null")
    @Size(min = 1, max = 50)
    private String name;

    /**
     * 空间描述
     */
    @Size(min = 0, max = 50)
    private String description;

    /**
     * 操作用户unionId
     */
    @NotBlank(message = "operatorId must not be blank")
    private String operatorId;

}
