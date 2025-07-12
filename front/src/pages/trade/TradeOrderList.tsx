import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input, Popconfirm, Form, Select } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getTradeOrderList, deleteTradeOrder, batchSubmitTradeOrders, batchRejectTradeOrders, tradeOrderApi } from '../../api/trade';
import type { TradeOrder } from '../../types';
import { useTrackEvent } from '../../utils/request';

const { Search } = Input;

const statusMap: Record<string, string> = {
  PENDING: '待处理',
  PROCESSING: '处理中',
  SUCCESS: '成功',
  COMPLETED: '已完成',
  FAILED: '失败',
  CANCELLED: '已取消',
  REJECTED: '已驳回',
};

const orderTypeOptions = [
  { value: 'ALL', label: '全部' },
  { value: 'CREATE', label: '建仓单' },
  { value: 'REBALANCE', label: '调仓单' },
  { value: 'ERROR', label: '差错处理单' },
] as const;

function getOrderType(order: any) {
  // 业务规则：remark包含关键字
  if (order.remark?.includes('调仓')) return 'REBALANCE';
  if (order.remark?.includes('差错')) return 'ERROR';
  if (order.remark?.includes('建仓')) return 'CREATE';
  return 'CREATE'; // 默认归为建仓单
}

const TradeOrderModal: React.FC<{
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
      title={isView ? '查看交易单' : '编辑交易单'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnHidden
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="订单编号" name="orderNo"><Input disabled /></Form.Item>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="交易类型" name="tradeType"><Select disabled={isView} options={[
          { value: 'BUY', label: '买入' },
          { value: 'SELL', label: '卖出' },
        ]} /></Form.Item>
        <Form.Item label="交易金额" name="amount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="交易份额" name="shares"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="订单状态" name="status"><Select disabled={isView} options={Object.entries(statusMap).map(([value, label]) => ({ value, label }))} /></Form.Item>
        <Form.Item label="备注" name="remark"><Input.TextArea disabled={isView} /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
        <Form.Item label="更新时间" name="updatedAt"><Input disabled /></Form.Item>
        <Form.Item label="处理时间" name="processedAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const TradeOrderList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<TradeOrder[]>([]);
  const [searchText, setSearchText] = useState('');
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const [activeType, setActiveType] = useState('ALL');
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/trade-orders');
  }, [track]);
  // 下单、撤单、筛选、导出、查看详情等操作可用track('click', '/trade-orders', { buttonId: 'order' })等

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await getTradeOrderList();
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setDataSource(list);
      setTotal(list.length);
    } catch (error) {
      message.error('获取交易订单列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = (id: number) => {
    track('click', '/trades', { buttonId: 'delete', orderId: id });
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个交易订单吗？',
      onOk: async () => {
        try {
          const response = await deleteTradeOrder(id);
          if (response.data.code === 200) {
            message.success('删除成功');
            fetchData();
          }
        } catch (error) {
          message.error('删除失败');
        }
      },
    });
  };

  const handleBatchSubmit = async () => {
    track('click', '/trades', { buttonId: 'batchSubmit', orderIds: selectedRowKeys });
    if (selectedRowKeys.length === 0) return message.warning('请先选择交易单');
    try {
      await batchSubmitTradeOrders(selectedRowKeys as number[]);
      message.success('批量下发成功');
      setSelectedRowKeys([]);
      fetchData();
    } catch (e) {
      message.error('批量下发失败');
    }
  };

  const handleBatchReject = async () => {
    track('click', '/trades', { buttonId: 'batchReject', orderIds: selectedRowKeys });
    if (selectedRowKeys.length === 0) return message.warning('请先选择交易单');
    try {
      await batchRejectTradeOrders(selectedRowKeys as number[]);
      message.success('批量驳回成功');
      setSelectedRowKeys([]);
      fetchData();
    } catch (e) {
      message.error('批量驳回失败');
    }
  };

  const handleView = (record: any) => {
    track('click', '/trades', { buttonId: 'view', orderId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/trades', { buttonId: 'edit', orderId: record.id });
    setModalData(record);
    setModalMode('edit');
    setModalOpen(true);
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: '订单号',
      dataIndex: 'orderNo',
      key: 'orderNo',
    },
    {
      title: '用户ID',
      dataIndex: 'userId',
      key: 'userId',
      width: 100,
    },
    {
      title: '产品ID',
      dataIndex: 'productId',
      key: 'productId',
      width: 100,
    },
    {
      title: '交易类型',
      dataIndex: 'tradeType',
      key: 'tradeType',
      render: (tradeType: string) => {
        const colorMap: Record<string, string> = {
          'BUY': 'green',
          'SELL': 'red',
        };
        const typeMap: Record<string, string> = {
          'BUY': '买入',
          'SELL': '卖出',
        };
        return <Tag color={colorMap[tradeType] || 'default'}>{typeMap[tradeType] || tradeType}</Tag>;
      },
    },
    {
      title: '交易金额',
      dataIndex: 'amount',
      key: 'amount',
      render: (value: number) => `¥${value.toLocaleString()}`,
    },
    {
      title: '交易份额',
      dataIndex: 'shares',
      key: 'shares',
      render: (value: number) => value ? value.toFixed(2) : '-',
    },
    {
      title: '订单状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => {
        const colorMap: Record<string, string> = {
          PENDING: 'orange',
          PROCESSING: 'blue',
          SUCCESS: 'green',
          COMPLETED: 'green',
          FAILED: 'red',
          CANCELLED: 'red',
          REJECTED: 'red',
        };
        return <Tag color={colorMap[status] || 'default'}>{statusMap[status] || status}</Tag>;
      },
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '处理时间',
      dataIndex: 'processedAt',
      key: 'processedAt',
      render: (date: string) => date ? new Date(date).toLocaleString() : '-',
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: any) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => handleView(record)}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          >
            编辑
          </Button>
          <Button 
            type="link" 
            danger 
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.id)}
          >
            删除
          </Button>
          <Popconfirm title="确定下发该单？" onConfirm={async () => {
            track('click', '/trades', { buttonId: 'submit', orderId: record.id });
            await batchSubmitTradeOrders([record.id]);
            message.success('下发成功');
            fetchData();
          }}><Button size="small">下发</Button></Popconfirm>
          <Popconfirm title="确定驳回该单？" onConfirm={async () => {
            track('click', '/trades', { buttonId: 'reject', orderId: record.id });
            await batchRejectTradeOrders([record.id]);
            message.success('驳回成功');
            fetchData();
          }}><Button size="small" danger>驳回</Button></Popconfirm>
        </Space>
      ),
    },
  ];

  // 分类统计数量
  const countMap = {
    CREATE: dataSource.filter(item => getOrderType(item) === 'CREATE').length,
    REBALANCE: dataSource.filter(item => getOrderType(item) === 'REBALANCE').length,
    ERROR: dataSource.filter(item => getOrderType(item) === 'ERROR').length,
  };

  // 下拉筛选过滤
  const filteredData = dataSource.filter(item => {
    const matchType = activeType === 'ALL' || getOrderType(item) === activeType;
    const matchSearch = item.orderNo.toLowerCase().includes(searchText.toLowerCase()) ||
      item.tradeType.toLowerCase().includes(searchText.toLowerCase()) ||
      item.status.toLowerCase().includes(searchText.toLowerCase());
    return matchType && matchSearch;
  });

  return (
    <Card title="交易订单管理" extra={
      <Space>
        <Select
          value={activeType}
          onChange={setActiveType}
          style={{ width: 140 }}
          options={orderTypeOptions.map(opt => ({
            ...opt,
            label: `${opt.label}${opt.value !== 'ALL' ? `（${countMap[opt.value as 'CREATE' | 'REBALANCE' | 'ERROR'] || 0}）` : ''}`
          }))}
        />
        <Search
          placeholder="搜索订单"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => { 
            track('click', '/trades', { buttonId: 'add' });
            setModalData(undefined); 
            setModalMode('edit'); 
            setModalOpen(true); 
          }}
        >
          新增订单
        </Button>
        <Button type="primary" onClick={handleBatchSubmit} disabled={selectedRowKeys.length === 0}>批量下发</Button>
        <Button danger onClick={handleBatchReject} disabled={selectedRowKeys.length === 0}>批量驳回</Button>
      </Space>
    } style={{ maxWidth: 1200, margin: '0 auto' }}>
      <Table
        columns={columns}
        dataSource={filteredData}
        loading={loading}
        rowKey="id"
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
        }}
        rowSelection={{ selectedRowKeys, onChange: setSelectedRowKeys }}
        scroll={{ x: 1400 }}
      />
      <TradeOrderModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
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

export default TradeOrderList; 