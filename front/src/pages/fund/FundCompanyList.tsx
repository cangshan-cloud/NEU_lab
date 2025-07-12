import React, { useEffect, useState } from 'react';
import { Card, Table, message, Spin, Tag, Button, Modal, Form, Input, Popconfirm } from 'antd';
import { fundCompanyApi } from '../../api/fund';
import type { FundCompany } from '../../types';
import { useTrackEvent } from '../../utils/request';

const FundCompanyList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/fund-companies');
  }, [track]);
  // 新增、编辑、筛选、查看详情等操作可用track('click', '/fund-companies', { buttonId: 'add' })等
  const [data, setData] = useState<FundCompany[]>([]);
  const [loading, setLoading] = useState(true);
  const [modalVisible, setModalVisible] = useState(false);
  const [editingCompany, setEditingCompany] = useState<FundCompany | null>(null);
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
      const response = await fundCompanyApi.getAll();
      let arr: FundCompany[] = [];
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
      message.error('获取基金公司列表失败');
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingCompany(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record: FundCompany) => {
    setEditingCompany(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await fundCompanyApi.delete(id);
      message.success('删除成功');
      fetchData();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      if (editingCompany) {
        await fundCompanyApi.update(editingCompany.id, values);
        message.success('编辑成功');
      } else {
        await fundCompanyApi.create(values);
        message.success('新增成功');
      }
      setModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  const columns = [
    { title: '公司ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: '公司代码', dataIndex: 'companyCode', key: 'companyCode' },
    { title: '公司名称', dataIndex: 'companyName', key: 'companyName' },
    { title: '公司简称', dataIndex: 'companyShortName', key: 'companyShortName' },
    { title: '成立日期', dataIndex: 'establishmentDate', key: 'establishmentDate' },
    { title: '注册资本', dataIndex: 'registeredCapital', key: 'registeredCapital' },
    { title: '法定代表人', dataIndex: 'legalRepresentative', key: 'legalRepresentative' },
    { title: '联系电话', dataIndex: 'contactPhone', key: 'contactPhone' },
    { title: '公司网站', dataIndex: 'website', key: 'website' },
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
      render: (_: any, record: FundCompany) => (
        <>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Popconfirm title="确定要删除该公司吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <Card title="基金公司列表" extra={<Button type="primary" onClick={handleAdd}>新增公司</Button>}>
      <Spin spinning={loading}>
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
          title={editingCompany ? '编辑公司' : '新增公司'}
          open={modalVisible}
          onOk={handleOk}
          onCancel={() => setModalVisible(false)}
          destroyOnClose
        >
          <Form form={form} layout="vertical">
            <Form.Item name="companyCode" label="公司代码" rules={[{ required: true, message: '请输入公司代码' }]}>
              <Input />
            </Form.Item>
            <Form.Item name="companyName" label="公司名称" rules={[{ required: true, message: '请输入公司名称' }]}>
              <Input />
            </Form.Item>
            <Form.Item name="companyShortName" label="公司简称">
              <Input />
            </Form.Item>
            <Form.Item name="establishmentDate" label="成立日期">
              <Input />
            </Form.Item>
            <Form.Item name="registeredCapital" label="注册资本">
              <Input />
            </Form.Item>
            <Form.Item name="legalRepresentative" label="法定代表人">
              <Input />
            </Form.Item>
            <Form.Item name="contactPhone" label="联系电话">
              <Input />
            </Form.Item>
            <Form.Item name="website" label="公司网站">
              <Input />
            </Form.Item>
            <Form.Item name="status" label="状态">
              <Input />
            </Form.Item>
          </Form>
        </Modal>
      </Spin>
    </Card>
  );
};

export default FundCompanyList; 