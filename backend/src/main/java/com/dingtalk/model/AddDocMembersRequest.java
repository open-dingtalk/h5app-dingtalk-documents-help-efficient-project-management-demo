package com.dingtalk.model;

import java.util.List;

import lombok.Data;

/**
 * @author qwh.shuwu
 * @date 2022/4/13 8:24 下午
 */
@Data
public class AddDocMembersRequest {

    private String workspaceId;

    private String nodeId;

    private String operatorId;

    private List<MemberInfo> members;
}
