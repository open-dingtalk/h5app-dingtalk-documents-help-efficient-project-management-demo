import React from "react";
import { Modal, Button, Form, Input } from "antd";

class AppendRows extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      first: "test",
      second: "test",
      third: "test",
    };
  }

  onValueChange = (e, index) => {
    const { value } = e.target;
    if (index === 1) {
      this.setState({ first: value });
    } else if (index === 2) {
      this.setState({ second: value });
    } else {
      this.setState({ third: value });
    }
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

  appendRows = () => {
    this.setState({
      visible: false,
    });
    let valueArr = [this.state.first, this.state.second, this.state.third];

    let value = new Array(valueArr);
    console.log(value);
    console.log(JSON.stringify(value));
    this.props.appendRows(JSON.stringify(value));
  };

  render() {
    return (
      <div>
        <Button ghost style={{ border: "none" }} onClick={this.showModal}>
          添加表单数据到表格
        </Button>
        <Modal
          title="添加表格内容信息"
          visible={this.state.visible}
          onOk={this.appendRows}
          onCancel={this.hideModel}
          okText="确认"
          cancelText="取消"
        >
          <Form name="basic">
            <Form.Item label="第一列" name="第一列">
              <Input onChange={(e) => this.onValueChange(e, 1)} />
            </Form.Item>
            <Form.Item label="第二列" name="第二列">
              <Input onChange={(e) => this.onValueChange(e, 2)} />
            </Form.Item>
            <Form.Item label="第三列" name="第三列">
              <Input onChange={(e) => this.onValueChange(e, 3)} />
            </Form.Item>
          </Form>
        </Modal>
      </div>
    );
  }
}

export default AppendRows;
