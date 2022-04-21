package com.dingtalk.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author qwh.shuwu
 * @date 2022/4/13 4:09 下午
 */
@Data
public class SearchRequest {


    /**
     * 空间Id
     */
    @NotBlank(message = "workspaceId name must not be null")
    private String workspaceId;

    /**
     * 空间描述
     */
    @NotBlank(message = "workspaceId name must not be null")
    @Size(min = 0, max = 50)
    private String keyword;

    /**
     * 操作用户unionId
     */
    @NotBlank(message = "operatorId must not be blank")
    private String operatorId;

    /**
     * 最大结果数
     */
    @NotNull(message = "maxResults must not be blank")
    @Max(value = 50)
    private Integer maxResults;

    /**
     * 翻页查询Id
     */
    private String loadMoreId;
}
