import React, { useEffect, useState } from 'react';
import { Table, Card, Select, Button, Tag, Form, Space, Modal, Input, message } from 'antd';
import { getDeliveryOrders } from '../../api/trade';
import { getProducts } from '../../api/fund';
import { getUserList } from '../../api/user';
import { EyeOutlined, EditOutlined } from '@ant-design/icons';
import { useTrackEvent } from '../../utils/request';
import { tradeOrderApi } from '../../api/trade';

// 状态映射表
const statusMap: Record<string, string> = {
  SUCCESS: '成功',
  FAILED: '失败',
  PENDING: '处理中',
  REJECTED: '已驳回',
};

const DeliveryOrderModal: React.FC<{
  open: boolean;
  mode: 'view' | 'edit';
  data?: any;
  onOk?: (values: any) => void;
  onCancel: () => void;
}> = ({ open, mode, data, onOk, onCancel }) => {
  const [form] = Form.useForm();
  useEffect(() => {
    if (open) {
      form.setFieldsValue(data);
    }
  }, [open, data, form]);
  const isView = mode === 'view';
  return (
    <Modal
      open={open}
      title={isView ? '查看交割单' : '编辑交割单'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnClose
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="交割单ID" name="id"><Input disabled /></Form.Item>
        <Form.Item label="订单ID" name="orderId"><Input disabled /></Form.Item>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="类型" name="tradeType"><Select disabled={isView} options={[
          { value: 'BUY', label: '买入' },
          { value: 'SELL', label: '卖出' },
        ]} /></Form.Item>
        <Form.Item label="金额" name="amount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="状态" name="status"><Select disabled={isView} options={Object.entries(statusMap).map(([value, label]) => ({ value, label }))} /></Form.Item>
        <Form.Item label="成交时间" name="tradeTime"><Input disabled /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const DeliveryOrderList: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [users, setUsers] = useState<{ label: string; value: number }[]>([]);
  const [products, setProducts] = useState<{ label: string; value: number }[]>([]);
  const [form] = Form.useForm();
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/delivery-orders');
  }, [track]);

  const fetchData = async (params?: any) => {
    setLoading(true);
    try {
      const res = await getDeliveryOrders(params || {});
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setData(list);
      setTotal(list.length);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    getUserList().then(res => setUsers(((res.data?.records || res.data || []) as any[]).map((item: any) => ({
      label: item.username || item.name,
      value: item.id
    }))));
    getProducts().then(res => setProducts((res.data || []).map((item: any) => ({
      label: item.productName,
      value: item.id
    }))));
  }, []);

  const onSearch = (values: any) => {
    track('click', '/delivery-orders', { buttonId: 'filter', ...values });
    fetchData(values);
  };

  const handleView = (record: any) => {
    track('click', '/delivery-orders', { buttonId: 'view', deliveryOrderId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/delivery-orders', { buttonId: 'edit', deliveryOrderId: record.id });
    setModalData(record);
    setModalMode('edit');
    setModalOpen(true);
  };

  const columns = [
    { title: '交割单ID', dataIndex: 'id', key: 'id' },
    { title: '订单ID', dataIndex: 'orderId', key: 'orderId' },
    { title: '用户ID', dataIndex: 'userId', key: 'userId' },
    { title: '产品ID', dataIndex: 'productId', key: 'productId' },
    { title: '类型', dataIndex: 'tradeType', key: 'tradeType', render: (v: string) => v === 'BUY' ? <Tag color="green">买入</Tag> : <Tag color="red">卖出</Tag> },
    { title: '金额', dataIndex: 'amount', key: 'amount' },
    { title: '状态', dataIndex: 'status', key: 'status', render: (v: string) => <Tag>{statusMap[v] || v}</Tag> },
    { title: '成交时间', dataIndex: 'tradeTime', key: 'tradeTime' },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: any) => (
        <Space size="middle">
          <Button type="link" icon={<EyeOutlined />} onClick={() => handleView(record)}>查看</Button>
          <Button type="link" icon={<EditOutlined />} onClick={() => handleEdit(record)}>编辑</Button>
        </Space>
      ),
    },
  ];

  return (
    <Card title="交割单管理" style={{ maxWidth: 1000, margin: '0 auto' }}>
      <Form form={form} layout="inline" onFinish={onSearch} style={{ marginBottom: 16 }}>
        <Form.Item name="userId" label="用户">
          <Select options={users} allowClear style={{ width: 160 }} placeholder="全部用户" />
        </Form.Item>
        <Form.Item name="productId" label="产品">
          <Select options={products} allowClear style={{ width: 160 }} placeholder="全部产品" />
        </Form.Item>
        <Form.Item name="status" label="状态">
          <Select allowClear style={{ width: 120 }} placeholder="全部状态">
            <Select.Option value="SUCCESS">成功</Select.Option>
            <Select.Option value="FAILED">失败</Select.Option>
            <Select.Option value="PENDING">处理中</Select.Option>
          </Select>
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit">筛选</Button>
        </Form.Item>
      </Form>
      <Table rowKey="id" loading={loading} columns={columns} dataSource={data} pagination={{ pageSize: 10 }} scroll={{ x: 1000 }} />
      <DeliveryOrderModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
            // 假设有 tradeOrderApi.update 或 deliveryOrderApi.update
            await tradeOrderApi.update(modalData.id, values);
            message.success('保存成功');
            setModalOpen(false);
            fetchData();
          } catch (e) {
            message.error('保存失败');
          }
        }}
        onCancel={() => setModalOpen(false)}
      />
    </Card>
  );
};

export default DeliveryOrderList; 