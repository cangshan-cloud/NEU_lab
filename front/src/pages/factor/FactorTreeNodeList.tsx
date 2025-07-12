import React, { useEffect, useState } from 'react';
import { Tree, Button, Modal, Form, Input, message, Popconfirm, Table, Upload } from 'antd';
import { factorTreeNodeApi, batchImportFactorTreeNodes } from '../../api/factor';
import type { FactorTreeNode } from '../../types';
import { getFactorTreeNodes, addFactorTreeNode, updateFactorTreeNode, deleteFactorTreeNode } from '../../api/factor';
import { useTrackEvent } from '../../utils/request';

function buildTree(nodes: FactorTreeNode[], parentId: number | null = null): any[] {
  return nodes
    .filter(n => n.parentId === parentId)
    .map(n => ({
      key: n.id,
      title: n.nodeName,
      children: buildTree(nodes, n.id),
      data: n
    }));
}

const FactorTreeNodeList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/factor-tree-nodes');
  }, [track]);
  const [data, setData] = useState<FactorTreeNode[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editNode, setEditNode] = useState<FactorTreeNode | null>(null);
  const [json, setJson] = useState('');
  const [form] = Form.useForm();

  const fetchData = async () => {
    setLoading(true);
    const res = await factorTreeNodeApi.getList();
    setData(res.data?.data?.content || res.data?.data || []);
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleImport = async (file: any) => {
    try {
      const text = await file.text();
      const json = JSON.parse(text);
      if (!Array.isArray(json)) throw new Error('请上传JSON数组文件');
      await batchImportFactorTreeNodes(json);
      message.success('批量导入成功');
      fetchData();
    } catch (e) {
      message.error('请上传格式正确的JSON数组文件');
    }
    return false;
  };

  const handleAdd = (parentId?: number) => {
    setEditNode({ treeId: 0, parentId: parentId || null, nodeName: '', status: 'active' } as FactorTreeNode);
    form.resetFields();
  };

  const handleEdit = (node: FactorTreeNode) => {
    setEditNode(node);
    form.setFieldsValue(node);
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteFactorTreeNode(id);
    message.success('删除成功');
    fetchData();
  };

  const handleOk = async () => {
    const values = await form.validateFields();
    if (editNode && editNode.id) {
      await updateFactorTreeNode({ ...editNode, ...values });
      message.success('修改成功');
    } else {
      await addFactorTreeNode({ ...editNode, ...values });
      message.success('新增成功');
    }
    setEditNode(null);
    fetchData();
  };

  return (
    <div>
      <Upload beforeUpload={handleImport} showUploadList={false} accept=".json">
        <Button type="primary" style={{marginBottom: 16}}>批量导入</Button>
      </Upload>
      <Table
        rowKey="id"
        columns={[
          { title: '节点ID', dataIndex: 'id' },
          { title: '节点名称', dataIndex: 'nodeName' },
          { title: '父节点ID', dataIndex: 'parentId' },
          { title: '因子ID', dataIndex: 'factorId' },
          { title: '顺序', dataIndex: 'nodeOrder' }
        ]}
        dataSource={data}
        loading={loading}
        pagination={false}
        size="small"
      />
      <Modal
        title={editNode && editNode.id ? '编辑节点' : '新增节点'}
        open={!!editNode}
        onOk={handleOk}
        onCancel={() => setEditNode(null)}
        destroyOnClose
      >
        <Form form={form} layout="vertical">
          <Form.Item name="nodeName" label="节点名称" rules={[{ required: true, message: '请输入节点名称' }]}> <Input /> </Form.Item>
          <Form.Item name="nodeType" label="节点类型"> <Input /> </Form.Item>
          <Form.Item name="weight" label="权重"> <Input /> </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default FactorTreeNodeList; 