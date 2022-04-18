import React from "react";
import { message, Button, Modal, Table } from "antd";
import axios from "axios";

class ListWorkspace extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      workspaceInfoList: [],
    };
  }

  hideModel = () => {
    this.setState({
      visible: false,
    });
  };

  showList = () => {
    axios
      .get(this.props.domain + "/biz/workspaces", {
        params: {
          operatorId: this.props.unionId,
        },
      })
      .then((res) => {
        if (res.data.success) {
          let workspaceList = res.data.data.workspaces;
          let size = workspaceList.length;
          if (size >= 20) {
            workspaceList = workspaceList.slice(0, 20);
          }
          console.log(workspaceList);
          this.setState({
            visible: true,
            workspaceInfoList: workspaceList,
          });
        } else {
          message.error(res.data.errorMsg);
        }
      })
      .catch((err) => {
        alert("createWorkspace err, " + JSON.stringify(err));
      });
  };

  render() {
    const columns = [
      {
        title: "空间名称",
        dataIndex: "name",
        key: "name",
      },
      {
        title: "打开链接",
        dataIndex: "url",
        key: "url",
        render: (text) => <a href={text}>打开链接</a>,
      },
      {
        title: "操作",
        render: (text) => (
          <Button
            type="primary"
            onClick={() => {
              this.hideModel();
              this.props.chooseWorkspace(text);
            }}
          >
            选定此空间
          </Button>
        ),
      },
    ];
    return (
      <div>
        <Button ghost style={{ border: "none" }} onClick={this.showList}>
          查询团队空间列表
        </Button>
        <Modal
          title="团队空间列表"
          visible={this.state.visible}
          onOk={this.hideModel}
          okText="确认"
          onCancel={this.hideModel}
          cancelText="取消"
        >
          <Table
            rowKey={(record) => record.workspaceId}
            columns={columns}
            dataSource={this.state.workspaceInfoList}
          ></Table>
        </Modal>
      </div>
    );
  }
}

export default ListWorkspace;
