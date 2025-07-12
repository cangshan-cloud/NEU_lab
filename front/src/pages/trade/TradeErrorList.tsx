import React, { useEffect, useState } from 'react';
import { Table, Button, Select, message, Modal, Tag, Form, Input, Space } from 'antd';
import { getDeliveryOrders, handleTradeError } from '../../api/trade';
import { getProducts } from '../../api/fund';
import { EyeOutlined, EditOutlined } from '@ant-design/icons';
import { useTrackEvent } from '../../utils/request';
import { tradeOrderApi } from '../../api/trade';

const TradeErrorModal: React.FC<{
  open: boolean;
  mode: 'view' | 'edit';
  data?: any;
  onOk?: (values: any) => void;
  onCancel: () => void;
}> = ({ open, mode, data, onOk, onCancel }) => {
  const [form] = Form.useForm();
  React.useEffect(() => {
    if (open) {
      form.setFieldsValue(data);
    }
  }, [open, data, form]);
  const isView = mode === 'view';
  return (
    <Modal
      open={open}
      title={isView ? '查看差错处理' : '编辑差错处理'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnClose
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="差错ID" name="id"><Input disabled /></Form.Item>
        <Form.Item label="交割单ID" name="deliveryOrderId"><Input disabled /></Form.Item>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="差错类型" name="errorType"><Select disabled={isView} options={[
          { value: 'AMOUNT', label: '金额差错' },
          { value: 'PRODUCT', label: '产品差错' },
          { value: 'OTHER', label: '其他' },
        ]} /></Form.Item>
        <Form.Item label="原金额" name="originalAmount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="现金额" name="currentAmount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="状态" name="status"><Select disabled={isView} options={Object.entries(statusMap).map(([value, label]) => ({ value, label }))} /></Form.Item>
        <Form.Item label="备注" name="remark"><Input.TextArea disabled={isView} /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const TradeErrorList: React.FC = () => {
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [products, setProducts] = useState<{ label: string; value: number }[]>([]);
  const [modal, setModal] = useState<{ visible: boolean; deliveryOrderId?: number }>({ visible: false });
  const [selectedProduct, setSelectedProduct] = useState<number | undefined>();
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const track = useTrackEvent();

  // 状态映射表
  const statusMap: Record<string, string> = {
    PENDING: '待处理',
    RESOLVED: '已处理',
    REJECTED: '已驳回',
  };

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await getDeliveryOrders({ status: 'FAILED' });
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setData(list);
      setTotal(list.length);
    } catch (e) {
      message.error('获取失败交割单失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    getProducts().then(res => setProducts((res.data || []).map((item: any) => ({
      label: item.productName,
      value: item.id
    }))));
  }, []);

  useEffect(() => {
    track('view', '/trade-errors');
  }, [track]);
  // 筛选、导出、查看详情等操作可用track('click', '/trade-errors', { buttonId: 'export' })等

  const handleReplace = async () => {
    track('click', '/trade-errors', { buttonId: 'replace', deliveryOrderId: modal.deliveryOrderId, newProductId: selectedProduct });
    if (!modal.deliveryOrderId || !selectedProduct) return;
    try {
      await handleTradeError({ deliveryOrderId: modal.deliveryOrderId, newProductId: selectedProduct });
      message.success('差错处理成功，已重新下发');
      setModal({ visible: false });
      setSelectedProduct(undefined);
      fetchData();
    } catch (e) {
      message.error('差错处理失败');
    }
  };

  const handleView = (record: any) => {
    track('click', '/trade-errors', { buttonId: 'view', deliveryOrderId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/trade-errors', { buttonId: 'edit', deliveryOrderId: record.id });
    setModalData(record);
    setModalMode('edit');
    setModalOpen(true);
  };

  const columns = [
    { title: '交割单ID', dataIndex: 'id', key: 'id' },
    { title: '用户ID', dataIndex: 'userId', key: 'userId' },
    { title: '产品ID', dataIndex: 'productId', key: 'productId' },
    { title: '类型', dataIndex: 'tradeType', key: 'tradeType', render: (v: string) => v === 'BUY' ? <Tag color="green">买入</Tag> : <Tag color="red">卖出</Tag> },
    { title: '金额', dataIndex: 'amount', key: 'amount' },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => <Tag>{statusMap[status] || status}</Tag>,
    },
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
    <div>
      <Table rowKey="id" loading={loading} columns={columns} dataSource={data} pagination={{ pageSize: 10 }} scroll={{ x: 1000 }} />
      <TradeErrorModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
            // 假设有 tradeOrderApi.update 或 handleTradeError
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
      <Modal
        open={modal.visible}
        title="选择替代标的"
        onCancel={() => setModal({ visible: false })}
        onOk={handleReplace}
        okButtonProps={{ disabled: !selectedProduct }}
      >
        <Select
          style={{ width: '100%' }}
          placeholder="请选择替代标的"
          options={products}
          value={selectedProduct}
          onChange={setSelectedProduct}
        />
      </Modal>
    </div>
  );
};

export default TradeErrorList; 