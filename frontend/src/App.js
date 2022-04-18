import "./App.css";
import React from "react";
import axios from "axios";
import CreateComponent from "./components/createComponent";
import ListWorkspace from "./components/listWorkspace";
import * as dd from "dingtalk-jsapi";
import { message, Descriptions, Layout } from "antd";
import CreateDoc from "./components/createDoc";
import AddMember from "./components/addMember";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      domain: "",
      corpId: "",
      authCode: "",
      userId: "",
      userName: "",
      unionId: "",
      workspaceId: "",
      url: "",
      showWorkspaceInfo: "none",
      workspaceName: "",
      nodeId: "",
      nodeUrl: "",
      nodeName: "",
      showNodeInfo: "none",
      docType: "",
      docKey: "",
      permission: "",
    };
  }

  render() {
    const { Header, Sider, Content, Footer } = Layout;
    if (this.state.userId === "") {
      // 免登操作
      this.login();
    }
    return (
      <Layout>
        <Header className="header backgroundColor">
          <div className="logo">
            <img
              src="https://img.alicdn.com/imgextra/i3/O1CN01Mpftes1gwqxuL0ZQE_!!6000000004207-2-tps-240-240.png"
              className="headImg"
            />
            <span className="headMsg">文档开放能力体验</span>
            <div style={{ float: "right" }}>
              <img src="../../user.png" className="headRightImg"></img>
              <span className="headRightMsg">{this.state.userName}</span>
            </div>
          </div>
        </Header>
        <Layout>
          <Sider
            className="backgroundColor"
            style={{
              minHeight: document.documentElement.clientHeight - 200,
            }}
          >
            <br />
            <p>
              <CreateComponent
                createWorkspace={this.createWorkspace.bind(this)}
              />
            </p>
            <br />
            <p>
              <ListWorkspace
                unionId={this.state.unionId}
                domain={this.state.domain}
                chooseWorkspace={this.chooseWorkspace.bind(this)}
              ></ListWorkspace>
            </p>
            <br />
            <p>
              <CreateDoc
                hasChooseWorkspace={this.hasChooseWorkspace.bind(this)}
                createDoc={this.createNode.bind(this)}
              ></CreateDoc>
            </p>
            <br />
            <p>
              <AddMember
                domain={this.state.domain}
                addUserPermission={this.addUserPermission.bind(this)}
                choosePermission={this.choosePermission.bind(this)}
              ></AddMember>
            </p>
            <br />
            {/* <p>
              <AppendRows appendRows={this.appendRows.bind(this)}></AppendRows>
            </p> */}
          </Sider>
          <Content
            style={{
              margin: "24px 16px",
              padding: 24,
              background: "#fff",
              minHeight: document.documentElement.clientHeight - 200,
            }}
          >
            <div style={{ display: this.state.showWorkspaceInfo }}>
              <Descriptions title="当前团队空间信息">
                <Descriptions.Item label="名称">
                  {this.state.workspaceName}
                </Descriptions.Item>
                <Descriptions.Item label="打开链接">
                  <a href={this.state.url}>团队空间链接</a>
                </Descriptions.Item>
              </Descriptions>
            </div>
            <br />
            <div style={{ display: this.state.showNodeInfo }}>
              <Descriptions title="当前创建文档信息">
                <Descriptions.Item label="名称">
                  {this.state.nodeName}
                </Descriptions.Item>
                <Descriptions.Item label="打开链接">
                  <a href={this.state.nodeUrl}>文档链接</a>
                </Descriptions.Item>
              </Descriptions>
            </div>
          </Content>
        </Layout>
        <Footer style={{ textAlign: "center" }}>
          更多接口使用可查看
          <a href="https://open.dingtalk.com/document/orgapp-server/dingtalk-document-overview">
            文档链接
          </a>
          ，问题咨询请加入文档开放咨询群，群号: 34822165
        </Footer>
      </Layout>
    );
  }

  /**
   * 创建团队空间接口方法
   * @param {string} workspaceName  团队空间名称
   * @param {strng} description     团队空间描述
   */
  createWorkspace(workspaceName, description = "") {
    console.log(this.state);
    const request = {
      operatorId: this.state.unionId,
      name: workspaceName,
      description,
    };

    console.log(request);
    axios
      .post(
        this.state.domain + "/biz/workspace/create",
        JSON.stringify(request),
        {
          headers: { "Content-Type": "application/json" },
        }
      )
      .then((res) => {
        if (res.data.success) {
          const { workspaceId, name, url } = res.data.data;
          this.setState({
            workspaceId,
            url,
            workspaceName: name,
          });
          message.success("创建团队空间成功");
          this.setState({
            showWorkspaceInfo: "block",
          });
        } else {
          message.error(res.data.errorMsg);
        }
      })
      .catch((err) => {
        alert("createWorkspace err, " + JSON.stringify(err));
      });
  }

  /**
   * 创建团队空间文档
   * @param {string} nodeName 文档名称
   * @param {string} nodeType 文档类型
   */
  createNode(nodeName, nodeType) {
    const request = {
      operatorId: this.state.unionId,
      name: nodeName,
      docType: nodeType,
      workspaceId: this.state.workspaceId,
    };
    console.log(request);
    axios
      .post(this.state.domain + "/biz/node/create", JSON.stringify(request), {
        headers: { "Content-Type": "application/json" },
      })
      .then((res) => {
        if (res.data.success) {
          const { nodeId, url, docKey } = res.data.data;
          this.setState({
            nodeId,
            nodeUrl: url,
            nodeName: request.name,
            docKey,
            docType: request.docType,
          });
          message.success("创建文档成功");
          this.setState({
            showNodeInfo: "block",
          });
        } else {
          message.error(res.data.errorMsg);
        }
      })
      .catch((err) => {
        alert("createNode err, " + JSON.stringify(err));
      });
  }

  /**
   * 添加表格数据
   * @param {string} values 待添加数据
   * @returns
   */
  appendRows(values) {
    // if (this.state.docKey === "" || this.state.docType !== "WORKBOOK") {
    //   message.error("目前只支持表格添加数据，请创建一个表格");
    //   return;
    // }
    // const request = {
    //   operatorId: this.state.unionId,
    //   workbookId: this.state.docKey,
    //   values,
    // };

    // console.log(request);
    // axios
    //   .post(
    //     this.state.domain + "/biz/sheet/appendRows",
    //     JSON.stringify(request),
    //     {
    //       headers: { "Content-Type": "application/json" },
    //     }
    //   )
    //   .then((res) => {
    //     if (res.data.success) {
    //       message.success("添加数据成功");
    //     } else {
    //       message.error(res.data.errorMsg);
    //     }
    //   })
    //   .catch((err) => {
    //     alert("appendRows err, " + JSON.stringify(err));
    //   });
    message.success(
      "该接口目前处于定向发布阶段，可进入文档开放咨询群了解更多信息，群号: 34822165"
    );
  }

  chooseWorkspace(workspaceInfo) {
    console.log(workspaceInfo);
    this.setState({
      workspaceName: workspaceInfo.name,
      workspaceId: workspaceInfo.workspaceId,
      url: workspaceInfo.url,
      showWorkspaceInfo: "block",
    });
  }

  addUserPermission(userInfo) {
    console.log(userInfo);
    if (this.state.nodeId === "" || this.state.workspaceId === "") {
      message.error("请先创建一个文档");
      return;
    }

    const roleType =
      this.state.permission === "" ? "ONLY_VIEWER" : this.state.permission;
    const request = {
      operatorId: this.state.unionId,
      nodeId: this.state.nodeId,
      workspaceId: this.state.workspaceId,
      members: [
        {
          memberId: userInfo.unionid,
          memberType: "USER",
          roleType: roleType,
        },
      ],
    };
    axios
      .post(
        this.state.domain + "/biz/node/addMembers",
        JSON.stringify(request),
        {
          headers: { "Content-Type": "application/json" },
        }
      )
      .then((res) => {
        if (res.data.success) {
          message.success("文档权限添加成功，可打开文档查看");
        } else {
          message.error(res.data.errorMsg);
        }
      })
      .catch((err) => {
        alert("createNode err, " + JSON.stringify(err));
      });
    this.setState({
      permission: "",
    });
  }

  choosePermission(value) {
    console.log(value);
    this.setState({
      permission: value,
    });
  }

  hasChooseWorkspace() {
    if (this.state.workspaceId === "") {
      return false;
    } else {
      return true;
    }
  }

  login() {
    axios
      .get(this.state.domain + "/getCorpId")
      .then((res) => {
        console.log(res);
        if (res.data) {
          this.loginAction(res.data);
        }
      })
      .catch((error) => {
        alert("corpId err, " + JSON.stringify(error));
      });
  }

  //登录操作
  loginAction(corpId) {
    let _this = this;
    console.log(dd.runtime.permission.requestAuthCode);
    dd.runtime.permission.requestAuthCode({
      corpId: corpId, //企业 corpId
      onSuccess: function (res) {
        // 调用成功时回调
        axios
          .get(_this.state.domain + "/login?authCode=" + res.code)
          .then((res) => {
            if (res && res.data.success) {
              let userId = res.data.data.userId;
              let userName = res.data.data.userName;
              let unionId = res.data.data.unionId;
              message.success("登录成功，你好" + userName);
              _this.setState({
                userId: userId,
                userName: userName,
                unionId: unionId,
              });
            } else {
              alert("login failed --->" + JSON.stringify(res));
            }
          })
          .catch((error) => {
            alert("httpRequest failed --->" + JSON.stringify(error));
          });
      },
      onFail: function (err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err));
      },
    });
  }
}

export default App;

{
  /* <div className="content">
        <div className="header">
          <img
            src="https://img.alicdn.com/imgextra/i3/O1CN01Mpftes1gwqxuL0ZQE_!!6000000004207-2-tps-240-240.png"
            className="headImg"
          />
          文档开放体验
        </div>
        <div className="App">
          <div>
            <h2>文档开放能力体验</h2>
            <p>
              <CreateComponent
                createWorkspace={this.createWorkspace.bind(this)}
              />
            </p>
            <div style={{ display: this.state.showWorkspaceInfo }}>
              <Descriptions title="当前团队空间信息">
                <Descriptions.Item label="名称">
                  {this.state.workspaceName}
                </Descriptions.Item>
                <Descriptions.Item label="打开链接">
                  <a href={this.state.url}>团队空间链接</a>
                </Descriptions.Item>
              </Descriptions>
            </div>
            <p>
              <ListWorkspace
                unionId={this.state.unionId}
                domain={this.state.domain}
                chooseWorkspace={this.chooseWorkspace.bind(this)}
              ></ListWorkspace>
            </p>
            <p>
              <CreateDoc
                hasChooseWorkspace={this.hasChooseWorkspace.bind(this)}
                createDoc={this.createNode.bind(this)}
              ></CreateDoc>
            </p>
            <div style={{ display: this.state.showNodeInfo }}>
              <Descriptions title="当前创建文档信息">
                <Descriptions.Item label="名称">
                  {this.state.nodeName}
                </Descriptions.Item>
                <Descriptions.Item label="打开链接">
                  <a href={this.state.nodeUrl}>文档链接</a>
                </Descriptions.Item>
              </Descriptions>
            </div>
          </div>
        </div>
      </div> */
}
