import React, { useEffect, useState } from 'react';
import { Card, Table, message, Tag, Button, Modal, Form, Input, Popconfirm } from 'antd';
import { fundManagerApi } from '../../api/fund';
import type { FundManager } from '../../types';

const FundManagerList: React.FC = () => {
  const [data, setData] = useState<FundManager[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editingManager, setEditingManager] = useState<FundManager | null>(null);
  const [form] = Form.useForm();

  const statusColorMap: Record<string, string> = {
    ACTIVE: 'green',
    INACTIVE: 'red',
  };

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const response = await fundManagerApi.getList();
      let arr: FundManager[] = [];
      if (Array.isArray(response.data.data)) arr = response.data.data;
      else if (
        response.data.data &&
        typeof response.data.data === 'object' &&
        'content' in response.data.data &&
        Array.isArray((response.data.data as any).content)
      ) {
        arr = (response.data.data as any).content;
      } else arr = [];
      setData(arr);
    } catch (error) {
      message.error('获取基金经理列表失败');
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingManager(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record: FundManager) => {
    setEditingManager(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await fundManagerApi.delete(id);
      message.success('删除成功');
      fetchData();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      if (editingManager) {
        await fundManagerApi.update(editingManager.id, values);
        message.success('编辑成功');
      } else {
        await fundManagerApi.create(values);
        message.success('新增成功');
      }
      setModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  const columns = [
    { title: '经理ID', dataIndex: 'id', key: 'id' },
    { title: '经理代码', dataIndex: 'managerCode', key: 'managerCode' },
    { title: '经理姓名', dataIndex: 'managerName', key: 'managerName' },
    { title: '性别', dataIndex: 'gender', key: 'gender' },
    { title: '学历', dataIndex: 'education', key: 'education' },
    { title: '从业年限', dataIndex: 'experienceYears', key: 'experienceYears' },
    { title: '所属公司ID', dataIndex: 'companyId', key: 'companyId' },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={statusColorMap[status] || 'default'}>
          {status === 'ACTIVE' ? '正常' : status === 'INACTIVE' ? '停用' : status}
        </Tag>
      ),
    },
    { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt' },
    { title: '更新时间', dataIndex: 'updatedAt', key: 'updatedAt' },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: FundManager) => (
        <>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Popconfirm title="确定要删除该经理吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <Card title="基金经理列表" extra={<Button type="primary" onClick={handleAdd}>新增经理</Button>}>
      <Table
        bordered
        columns={columns}
        dataSource={data}
        rowKey="id"
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
        }}
        scroll={{ x: 'max-content' }}
        rowClassName={(_, idx) => (idx % 2 === 0 ? 'table-row-light' : 'table-row-dark')}
      />
      <Modal
        title={editingManager ? '编辑经理' : '新增经理'}
        open={modalVisible}
        onOk={handleOk}
        onCancel={() => setModalVisible(false)}
        destroyOnClose
      >
        <Form form={form} layout="vertical">
          <Form.Item name="managerCode" label="经理代码" rules={[{ required: true, message: '请输入经理代码' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="managerName" label="经理姓名" rules={[{ required: true, message: '请输入经理姓名' }]}>
            <Input />
          </Form.Item>
          <Form.Item name="gender" label="性别">
            <Input />
          </Form.Item>
          <Form.Item name="education" label="学历">
            <Input />
          </Form.Item>
          <Form.Item name="experienceYears" label="从业年限">
            <Input />
          </Form.Item>
          <Form.Item name="companyId" label="所属公司ID">
            <Input />
          </Form.Item>
          <Form.Item name="status" label="状态">
            <Input />
          </Form.Item>
        </Form>
      </Modal>
    </Card>
  );
};

export default FundManagerList; 