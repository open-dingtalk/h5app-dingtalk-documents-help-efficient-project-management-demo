package com.dingtalk.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.aliyun.dingboot.common.token.ITokenManager;
import com.aliyun.dingtalkdoc_1_0.Client;
import com.aliyun.dingtalkdoc_1_0.models.AddWorkspaceDocMembersHeaders;
import com.aliyun.dingtalkdoc_1_0.models.AddWorkspaceDocMembersRequest;
import com.aliyun.dingtalkdoc_1_0.models.AddWorkspaceDocMembersRequest.AddWorkspaceDocMembersRequestMembers;
import com.aliyun.dingtalkdoc_1_0.models.AddWorkspaceDocMembersResponse;
import com.aliyun.dingtalkdoc_1_0.models.AppendRowsHeaders;
import com.aliyun.dingtalkdoc_1_0.models.AppendRowsRequest;
import com.aliyun.dingtalkdoc_1_0.models.AppendRowsResponse;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceDocHeaders;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceDocRequest;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceDocResponse;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceHeaders;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceRequest;
import com.aliyun.dingtalkdoc_1_0.models.CreateWorkspaceResponse;
import com.aliyun.dingtalkdoc_1_0.models.GetRelatedWorkspacesHeaders;
import com.aliyun.dingtalkdoc_1_0.models.GetRelatedWorkspacesRequest;
import com.aliyun.dingtalkdoc_1_0.models.GetRelatedWorkspacesResponse;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsHeaders;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsRequest;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsResponse;
import com.aliyun.dingtalkdoc_1_0.models.SearchWorkspaceDocsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListidRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse.UserGetResponse;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.model.AddDocMembersRequest;
import com.dingtalk.model.MemberInfo;
import com.dingtalk.model.SearchRequest;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qwh.shuwu
 * @date 2022/3/16 2:57 下午
 */
@Service
public class BizManager {

    private static final String DEPT_GET_URL = "https://oapi.dingtalk.com/topapi/v2/department/listsub";

    private static final String USER_GET_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";

    private static final String LIST_USER_URL = "https://oapi.dingtalk.com/topapi/user/listid";

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ITokenManager tokenManager;

    /**
     * 创建团队空间
     *
     * @param name        团队空间名称
     * @param description 团队空间描述
     * @param operatorId  操作用户unionId
     * @return 团队空间信息
     */
    public CreateWorkspaceResponse createWorkspace(String name, String description, String operatorId) {

        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            CreateWorkspaceHeaders createWorkspaceHeaders = new CreateWorkspaceHeaders();
            createWorkspaceHeaders.setXAcsDingtalkAccessToken(accessToken);
            CreateWorkspaceRequest createWorkspaceRequest = new CreateWorkspaceRequest();
            createWorkspaceRequest.setName(name);
            createWorkspaceRequest.setDescription(description);
            createWorkspaceRequest.setOperatorId(operatorId);
            return client.createWorkspaceWithOptions(createWorkspaceRequest,
                createWorkspaceHeaders, new RuntimeOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateWorkspaceDocResponse createWorkspaceDoc(String name, String docType, String operatorId,
        String workspaceId) {
        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            CreateWorkspaceDocHeaders createWorkspaceDocHeaders = new CreateWorkspaceDocHeaders();
            createWorkspaceDocHeaders.setXAcsDingtalkAccessToken(accessToken);
            CreateWorkspaceDocRequest createWorkspaceDocRequest = new CreateWorkspaceDocRequest();
            createWorkspaceDocRequest.setName(name);
            createWorkspaceDocRequest.setDocType(docType);
            createWorkspaceDocRequest.setOperatorId(operatorId);
            return client.createWorkspaceDocWithOptions(workspaceId,
                createWorkspaceDocRequest, createWorkspaceDocHeaders, new RuntimeOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetRelatedWorkspacesResponse getRelatedWorkspace(String operatorId) {
        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            GetRelatedWorkspacesHeaders getRelatedWorkspacesHeaders = new GetRelatedWorkspacesHeaders();
            getRelatedWorkspacesHeaders.setXAcsDingtalkAccessToken(accessToken);
            GetRelatedWorkspacesRequest getRelatedWorkspacesRequest = new GetRelatedWorkspacesRequest();
            getRelatedWorkspacesRequest.setOperatorId(operatorId);
            getRelatedWorkspacesRequest.setIncludeRecent(Boolean.FALSE);
            return client.getRelatedWorkspacesWithOptions(
                getRelatedWorkspacesRequest, getRelatedWorkspacesHeaders, new RuntimeOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AppendRowsResponse appendRows(String operatorId, String workspaceId, String values) {
        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            AppendRowsHeaders appendRowsHeaders = new AppendRowsHeaders();
            appendRowsHeaders.setXAcsDingtalkAccessToken(accessToken);
            AppendRowsRequest appendRowsRequest = new AppendRowsRequest();
            JSONArray jsonArray = JSON.parseArray(values);
            List<List<String>> appValues = new ArrayList<>();
            jsonArray.forEach(value -> {
                JSONArray array = (JSONArray)value;
                List<String> strings = new ArrayList<>();
                for (Object o : array) {
                    strings.add((String)o);
                }
                appValues.add(strings);
            });
            appendRowsRequest.setValues(appValues);
            appendRowsRequest.setOperatorId(operatorId);
            return client.appendRowsWithOptions(workspaceId, "Sheet1", appendRowsRequest,
                appendRowsHeaders,
                new RuntimeOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SearchWorkspaceDocsResponse search(SearchRequest request) {

        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            SearchWorkspaceDocsHeaders searchWorkspaceDocsHeaders = new SearchWorkspaceDocsHeaders();
            searchWorkspaceDocsHeaders.setXAcsDingtalkAccessToken(accessToken);
            SearchWorkspaceDocsRequest searchWorkspaceDocsRequest = new SearchWorkspaceDocsRequest();
            searchWorkspaceDocsRequest.setOperatorId(request.getOperatorId());
            searchWorkspaceDocsRequest.setWorkspaceId(request.getWorkspaceId());
            searchWorkspaceDocsRequest.setKeyword(request.getKeyword());
            searchWorkspaceDocsRequest.setMaxResults(request.getMaxResults());
            searchWorkspaceDocsRequest.setNextToken(request.getLoadMoreId());

            return client.searchWorkspaceDocsWithOptions(
                searchWorkspaceDocsRequest, searchWorkspaceDocsHeaders, new RuntimeOptions());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addWorkspaceDocMembers(AddDocMembersRequest request) {
        try {
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            Client client = createClient();
            AddWorkspaceDocMembersHeaders addWorkspaceDocMembersHeaders = new AddWorkspaceDocMembersHeaders();
            addWorkspaceDocMembersHeaders.setXAcsDingtalkAccessToken(accessToken);
            AddWorkspaceDocMembersRequest addWorkspaceDocMembersRequest = new AddWorkspaceDocMembersRequest();
            addWorkspaceDocMembersRequest.setOperatorId(request.getOperatorId());
            List<AddWorkspaceDocMembersRequestMembers> list = new ArrayList<>();
            for (MemberInfo member : request.getMembers()) {
                AddWorkspaceDocMembersRequestMembers requestMember = new AddWorkspaceDocMembersRequestMembers();
                requestMember.setMemberId(member.getMemberId());
                requestMember.setMemberType(member.getMemberType());
                requestMember.setRoleType(member.getRoleType());
                list.add(requestMember);
            }
            addWorkspaceDocMembersRequest.setMembers(list);

            AddWorkspaceDocMembersResponse addWorkspaceDocMembersResponse = client.addWorkspaceDocMembersWithOptions(
                request.getWorkspaceId(), request.getNodeId(), addWorkspaceDocMembersRequest,
                addWorkspaceDocMembersHeaders, new RuntimeOptions());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OapiV2DepartmentListsubResponse.DeptBaseResponse> getDeptList(Long deptId) {
        try {
            // 获取access_token
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());

            DingTalkClient client = new DefaultDingTalkClient(DEPT_GET_URL);
            OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
            req.setDeptId(deptId);
            req.setLanguage("zh_CN");
            OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
            if (rsp.getErrcode() == 0) {
                return rsp.getResult();
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OapiUserListidResponse.ListUserByDeptResponse getDeptUserList(Long deptId) {
        try {
            // 获取access_token
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            DingTalkClient client = new DefaultDingTalkClient(LIST_USER_URL);
            OapiUserListidRequest req = new OapiUserListidRequest();
            req.setDeptId(deptId);
            OapiUserListidResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());

            return rsp.getResult();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OapiV2UserGetResponse.UserGetResponse getUserInfo(String userId) {
        try {
            // 获取access_token
            String accessToken = tokenManager.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
            DingTalkClient client = new DefaultDingTalkClient(USER_GET_URL);
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userId);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, accessToken);
            System.out.println(rsp.getBody());

            return rsp.getResult();

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用 Token 初始化账号Client
     *
     * @return Client
     * @throws Exception error
     */
    public Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }
}
