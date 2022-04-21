import React from "react";
import { Modal, Button, Table, message, Select } from "antd";
import axios from "axios";

class AddMember extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      userInfoList: [],
    };
  }

  hideModel = () => {
    this.setState({
      visible: false,
    });
  };

  showList = () => {
    axios
      .get(this.props.domain + "/biz/users", {})
      .then((res) => {
        if (res.data.success) {
          let userList = res.data.data;
          console.log(userList);
          this.setState({
            visible: true,
            userInfoList: userList,
          });
        } else {
          message.error(res.data.errorMsg);
        }
      })
      .catch((err) => {
        alert("getUserInfo err, " + JSON.stringify(err));
      });
  };

  render() {
    const { Option } = Select;
    const columns = [
      {
        title: "用户名称",
        dataIndex: "name",
        key: "name",
      },
      {
        title: "待添加权限",
        render: (text) => (
          <Select
            style={{ width: 120 }}
            onChange={(value) => {
              this.props.choosePermission(value);
            }}
          >
            <Option value="ONLY_VIEWER">ONLY_VIEWER</Option>
            <Option value="VIEWER">VIEWER</Option>
            <Option value="EDITOR">EDITOR</Option>
          </Select>
        ),
      },
      {
        title: "操作",
        render: (text) => (
          <Button
            type="primary"
            onClick={() => {
              this.hideModel();
              this.props.addUserPermission(text);
            }}
          >
            给此用户添加权限
          </Button>
        ),
      },
    ];
    return (
      <div>
        <Button ghost style={{ border: "none" }} onClick={this.showList}>
          添加用户权限
        </Button>
        <Modal
          title="人员信息列表"
          visible={this.state.visible}
          onOk={this.hideModel}
          okText="确认"
          onCancel={this.hideModel}
          cancelText="取消"
        >
          <Table
            rowKey={(record) => record.unionid}
            columns={columns}
            dataSource={this.state.userInfoList}
          ></Table>
        </Modal>
      </div>
    );
  }
}

export default AddMember;
