import React, { useEffect, useState } from 'react';
import { Card, Table, message, Tag, Button, Modal, Form, Input, Select, Popconfirm, Row, Col, Space, Tabs } from 'antd';
import { productReviewApi } from '../../api/product';
import type { ProductReview, Product } from '../../types';

const { Option } = Select;
const { TextArea } = Input;
const { TabPane } = Tabs;

const ProductReviewList: React.FC = () => {
  const [data, setData] = useState<ProductReview[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [reviewModalVisible, setReviewModalVisible] = useState(false);
  const [selectedReview, setSelectedReview] = useState<ProductReview | null>(null);
  const [form] = Form.useForm();
  const [reviewForm] = Form.useForm();
  const [activeTab, setActiveTab] = useState('all');

  const statusColorMap: Record<string, string> = {
    PENDING: 'orange',
    APPROVED: 'green',
    REJECTED: 'red',
  };

  const statusTextMap: Record<string, string> = {
    PENDING: '待审核',
    APPROVED: '已通过',
    REJECTED: '已拒绝',
  };

  const reviewTypeOptions = [
    { label: '初审', value: 'INITIAL' },
    { label: '终审', value: 'FINAL' },
  ];

  const localUser = localStorage.getItem('user');
  const user = localUser ? JSON.parse(localUser) : null;
  const isAdmin = !!user && Number(user.roleId || user.role_id) === 1;

  useEffect(() => {
    fetchData();
  }, [activeTab]);

  const fetchData = async () => {
    try {
      setLoading(true);
      let response;
      
      switch (activeTab) {
        case 'pending':
          response = await productReviewApi.getPending();
          break;
        case 'approved':
          response = await productReviewApi.getByStatus('APPROVED');
          break;
        case 'rejected':
          response = await productReviewApi.getByStatus('REJECTED');
          break;
        default:
          response = await productReviewApi.getAll();
      }
      
      let arr: ProductReview[] = [];
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
      message.error('获取审核列表失败');
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setSelectedReview(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record: ProductReview) => {
    setSelectedReview(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await productReviewApi.delete(id);
      message.success('删除成功');
      fetchData();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      if (selectedReview) {
        await productReviewApi.update(selectedReview.id, values);
        message.success('编辑成功');
      } else {
        await productReviewApi.create(values);
        message.success('新增成功');
      }
      setModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  // 审核操作
  const handleReview = (record: ProductReview) => {
    setSelectedReview(record);
    reviewForm.resetFields();
    setReviewModalVisible(true);
  };

  const handleReviewOk = async () => {
    try {
      const values = await reviewForm.validateFields();
      if (values.action === 'approve') {
        await productReviewApi.approve(selectedReview!.id, values);
        message.success('审核通过');
      } else {
        await productReviewApi.reject(selectedReview!.id, values);
        message.success('审核拒绝');
      }
      setReviewModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  const columns = [
    { title: '审核ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: '产品ID', dataIndex: 'productId', key: 'productId' },
    { title: '审核人ID', dataIndex: 'reviewerId', key: 'reviewerId' },
    { 
      title: '审核类型', 
      dataIndex: 'reviewType', 
      key: 'reviewType',
      render: (type: string) => {
        const option = reviewTypeOptions.find(opt => opt.value === type);
        return option ? option.label : type;
      }
    },
    {
      title: '审核状态',
      dataIndex: 'reviewStatus',
      key: 'reviewStatus',
      render: (status: string) => (
        <Tag color={statusColorMap[status] || 'default'}>
          {statusTextMap[status] || status}
        </Tag>
      ),
    },
    { title: '审核意见', dataIndex: 'reviewComment', key: 'reviewComment' },
    { title: '审核时间', dataIndex: 'reviewDate', key: 'reviewDate' },
    { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt' },
    { title: '更新时间', dataIndex: 'updatedAt', key: 'updatedAt' },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: ProductReview) => (
        <>
          {record.reviewStatus === 'PENDING' && (
            <Button type="link" onClick={() => handleReview(record)}>审核</Button>
          )}
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Popconfirm title="确定要删除该审核记录吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  if (!isAdmin) {
    return <div style={{padding: 32, textAlign: 'center', color: '#888'}}>无权限访问</div>;
  }

  return (
    <Card title="产品审核管理">
      <Tabs activeKey={activeTab} onChange={setActiveTab}>
        <TabPane tab="全部审核" key="all" />
        <TabPane tab="待审核" key="pending" />
        <TabPane tab="已通过" key="approved" />
        <TabPane tab="已拒绝" key="rejected" />
      </Tabs>

      <Table
        bordered
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
        }}
        scroll={{ x: 'max-content' }}
        rowClassName={(_, idx) => (idx % 2 === 0 ? 'table-row-light' : 'table-row-dark')}
      />
      
      {/* 新增/编辑审核记录弹窗 */}
      <Modal
        title={selectedReview ? '编辑审核记录' : '新增审核记录'}
        open={modalVisible}
        onOk={handleOk}
        onCancel={() => setModalVisible(false)}
        destroyOnClose
        width={600}
      >
        <Form form={form} layout="vertical">
          <Form.Item name="productId" label="产品ID" rules={[{ required: true, message: '请输入产品ID' }]}>
            <Input placeholder="请输入产品ID" />
          </Form.Item>
          <Form.Item name="reviewerId" label="审核人ID" rules={[{ required: true, message: '请输入审核人ID' }]}>
            <Input placeholder="请输入审核人ID" />
          </Form.Item>
          <Form.Item name="reviewType" label="审核类型">
            <Select placeholder="请选择审核类型" allowClear>
              {reviewTypeOptions.map(option => (
                <Option key={option.value} value={option.value}>{option.label}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item name="reviewStatus" label="审核状态">
            <Select placeholder="请选择审核状态" allowClear>
              <Option value="PENDING">待审核</Option>
              <Option value="APPROVED">已通过</Option>
              <Option value="REJECTED">已拒绝</Option>
            </Select>
          </Form.Item>
          <Form.Item name="reviewComment" label="审核意见">
            <TextArea rows={3} placeholder="请输入审核意见" />
          </Form.Item>
        </Form>
      </Modal>

      {/* 审核操作弹窗 */}
      <Modal
        title="审核操作"
        open={reviewModalVisible}
        onOk={handleReviewOk}
        onCancel={() => setReviewModalVisible(false)}
        destroyOnClose
        width={500}
      >
        <Form form={reviewForm} layout="vertical">
          <Form.Item name="action" label="审核结果" rules={[{ required: true, message: '请选择审核结果' }]}>
            <Select placeholder="请选择审核结果">
              <Option value="approve">通过</Option>
              <Option value="reject">拒绝</Option>
            </Select>
          </Form.Item>
          <Form.Item name="reviewComment" label="审核意见" rules={[{ required: true, message: '请输入审核意见' }]}>
            <TextArea rows={4} placeholder="请输入审核意见" />
          </Form.Item>
        </Form>
      </Modal>
    </Card>
  );
};

export default ProductReviewList; 