package com.dingtalk.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.aliyun.dingtalkdoc_1_0.models.AddWorkspaceDocMembersResponse;
import com.aliyun.dingtalkdoc_1_0.models.AppendRowsResponse;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceDocResponse;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceDocResponseBody;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceResponse;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceResponseBody;
import com.aliyun.dingtalkdoc_1_0.models.GetRelatedWorkspacesResponse;
import com.aliyun.dingtalkdoc_1_0.models.GetRelatedWorkspacesResponseBody;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsResponse;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsResponseBody;
import com.dingtalk.api.response.OapiUserListidResponse.ListUserByDeptResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse.DeptBaseResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse.UserGetResponse;
import com.dingtalk.model.AddDocMembersRequest;
import com.dingtalk.model.AppendSheetRowsRequest;
import com.dingtalk.model.CreateWorkspaceDocRequest;
import com.dingtalk.model.CreateWorkspaceRequest;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.model.SearchRequest;
import com.dingtalk.service.BizManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qwh.shuwu
 * @date 2022/3/16 2:52 下午
 */
@RestController
@RequestMapping("/biz")
public class BizController {

    @Autowired
    private BizManager bizManager;

    @PostMapping("/workspace/create")
    public RpcServiceResult<CreateWorkspaceResponseBody> createWorkspace(@Validated @RequestBody CreateWorkspaceRequest request) {

        CreateWorkspaceResponse workspace = bizManager.createWorkspace(request.getName(), request.getDescription(),
            request.getOperatorId());
        if(workspace == null) {
            return RpcServiceResult.getFailureResult("-1", "创建空间失败");
        }
        return RpcServiceResult.getSuccessResult(workspace.getBody());
    }

    @PostMapping("/node/create")
    public RpcServiceResult<CreateWorkspaceDocResponseBody> createWorkspaceNode(@Validated @RequestBody CreateWorkspaceDocRequest request) {
        CreateWorkspaceDocResponse workspaceDoc = bizManager.createWorkspaceDoc(request.getName(), request.getDocType(),
            request.getOperatorId(), request.getWorkspaceId());
        if(workspaceDoc == null) {
            return RpcServiceResult.getFailureResult("-1", "创建团队空间节点失败");
        }
        return RpcServiceResult.getSuccessResult(workspaceDoc.getBody());
    }

    @GetMapping("/workspaces")
    public RpcServiceResult<GetRelatedWorkspacesResponseBody> getRelatedWorkspace(@RequestParam String operatorId) {
        GetRelatedWorkspacesResponse relatedWorkspace = bizManager.getRelatedWorkspace(operatorId);
        if(relatedWorkspace == null) {
            return RpcServiceResult.getFailureResult("-1", "查询相关空间失败");
        }
        return RpcServiceResult.getSuccessResult(relatedWorkspace.getBody());
    }

    @PostMapping("/sheet/appendRows")
    public RpcServiceResult<AppendRowsResponse> appendSheetRows(@RequestBody AppendSheetRowsRequest request) {
        AppendRowsResponse appendRowsResponse = bizManager.appendRows(request.getOperatorId(), request.getWorkbookId(),
            request.getValues());
        if(appendRowsResponse == null) {
            return RpcServiceResult.getFailureResult("-1", "添加信息失败");
        }
        return RpcServiceResult.getSuccessResult(appendRowsResponse);
    }

    //@PostMapping("/search")
    //public RpcServiceResult<SearchWorkspaceDocsResponseBody> search(@Validated @RequestBody SearchRequest request) {
    //    SearchWorkspaceDocsResponse searchWorkspaceDocsResponse = bizManager.search(request);
    //    if(searchWorkspaceDocsResponse == null) {
    //        return RpcServiceResult.getFailureResult("-1", "搜索文档失败");
    //    }
    //    return RpcServiceResult.getSuccessResult(searchWorkspaceDocsResponse.getBody());
    //}

    @GetMapping("/users")
    public RpcServiceResult<List<OapiV2UserGetResponse.UserGetResponse>> getOrgMember() {
        List<DeptBaseResponse> deptList = bizManager.getDeptList(1L);
        int deptNum = Math.min(deptList.size(), 5);
        List<DeptBaseResponse> deptChooseList = deptList.subList(0, deptNum);
        Set<String> userIdChooseList = new HashSet<>();
        for(DeptBaseResponse deptBaseResponse: deptChooseList) {
            ListUserByDeptResponse deptUserList = bizManager.getDeptUserList(deptBaseResponse.getDeptId());
            List<String> useridList = deptUserList.getUseridList();
            userIdChooseList.addAll(useridList);
        }
        int chooseUserIdNum = Math.min(userIdChooseList.size(), 15);
        List<String> collect = new ArrayList<>(userIdChooseList);
        List<UserGetResponse> res = new ArrayList<>();
        for(int i = 0;i<chooseUserIdNum;i++) {
            String userId = collect.get(i);
            UserGetResponse userInfo = bizManager.getUserInfo(userId);
            res.add(userInfo);
        }
        return RpcServiceResult.getSuccessResult(res);
    }

    @PostMapping("/node/addMembers")
    public RpcServiceResult<Void> addWorkspaceMembers(@RequestBody AddDocMembersRequest request) {
        System.out.println(request);
        boolean res = bizManager.addWorkspaceDocMembers(request);
        if(!res) {
            return RpcServiceResult.getFailureResult("-1", "添加用户权限失败");
        }
        return RpcServiceResult.getSuccessResult(null);
    }
}
