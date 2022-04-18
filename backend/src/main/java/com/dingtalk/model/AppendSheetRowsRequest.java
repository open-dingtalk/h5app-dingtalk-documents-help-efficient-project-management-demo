package com.dingtalk.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author qwh.shuwu
 * @date 2022/3/22 5:00 下午
 */
@Data
public class AppendSheetRowsRequest {

    /**
     * 操作用户unionId
     */
    @NotBlank(message = "operatorId must not be blank")
    private String workbookId;

    /**
     * 接口操作数据
     */
    @NotBlank(message = "values must not be blank")
    private String values;

    /**
     * 操作用户unionId
     */
    @NotBlank(message = "operatorId must not be blank")
    private String operatorId;
}
