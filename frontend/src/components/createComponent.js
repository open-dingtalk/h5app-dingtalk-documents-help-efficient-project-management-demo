import React from "react";
import { Modal, Button, Form, Input } from "antd";

class CreateComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      workspaceName: "",
      description: "",
    };
    this.formRef = React.createRef();
  }

  createWorkspace = () => {
    this.setState({
      visible: false,
    });
    this.props.createWorkspace(
      this.state.workspaceName,
      this.state.description
    );
  };

  hideModel = () => {
    this.setState({
      visible: false,
    });
  };

  showModal = () => {
    this.setState({
      visible: true,
    });
  };

  onNameChange = (e) => {
    const { value } = e.target;
    this.setState({ workspaceName: value });
  };

  onDescriptionChange = (e) => {
    const { value } = e.target;
    this.setState({ description: value });
  };

  render() {
    return (
      <div>
        <Button ghost style={{ border: "none" }} onClick={this.showModal}>
          创建团队空间
        </Button>
        <Modal
          title="团队空间信息"
          visible={this.state.visible}
          onOk={this.createWorkspace}
          onCancel={this.hideModel}
          okText="确认"
          cancelText="取消"
        >
          <Form name="basic">
            <Form.Item
              label="团队空间名称"
              name="workspaceName"
              rules={[
                { required: true, message: "Please input workspaceName" },
              ]}
            >
              <Input onChange={this.onNameChange} />
            </Form.Item>
            <Form.Item label="团队空间描述" name="description">
              <Input onChange={this.onDescriptionChange} />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    );
  }
}

export default CreateComponent;
