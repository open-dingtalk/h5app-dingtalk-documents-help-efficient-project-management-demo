import { Button, Select, Form, Modal, Input, message } from "antd";
import React from "react";

class CreateDoc extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      nodeName: "",
      nodeType: "DOC",
    };
  }

  createDoc = () => {
    this.setState({
      visible: false,
    });
    this.props.createDoc(this.state.nodeName, this.state.nodeType);
  };

  hideModel = () => {
    this.setState({
      visible: false,
    });
  };

  showModal = () => {
    if (!this.props.hasChooseWorkspace()) {
      message.error("请先创建一个空间或查询选定一个空间");
    } else {
      this.setState({
        nodeName: "",
        visible: true,
      });
    }
  };

  onNameChange = (e) => {
    const { value } = e.target;
    this.setState({ nodeName: value });
  };

  onTypeChange = (value) => {
    console.log(value);
    this.setState({ nodeType: value });
  };

  render() {
    const { Option } = Select;
    return (
      <div>
        <Button ghost style={{ border: "none" }} onClick={this.showModal}>
          在当前空间下创建一个文档
        </Button>
        <Modal
          title="文档信息"
          visible={this.state.visible}
          onOk={this.createDoc}
          onCancel={this.hideModel}
          okText="确认"
          cancelText="取消"
        >
          <Form name="basic">
            <Form.Item
              label="文档名称"
              name="docName"
              defaultValue=""
              rules={[{ required: true, message: "Please input docName" }]}
            >
              <Input
                onChange={(e) => {
                  const { value } = e.target;
                  this.setState({ nodeName: value });
                }}
              />
            </Form.Item>
            <Form.Item label="文档类型" name="docType">
              <Select defaultValue="DOC" onChange={this.onTypeChange}>
                <Option value="DOC">文档</Option>
                <Option value="WORKBOOK">表格</Option>
                <Option value="MIND">脑图</Option>
                <Option value="FOLDER">文件夹</Option>
              </Select>
            </Form.Item>
          </Form>
        </Modal>
      </div>
    );
  }
}

export default CreateDoc;
