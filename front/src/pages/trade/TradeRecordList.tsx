import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input, Form, Select } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getTradeRecordList, deleteTradeRecord, tradeRecordApi } from '../../api/trade';
import type { TradeRecord } from '../../types';
import { useTrackEvent } from '../../utils/request';

const { Search } = Input;

const statusMap: Record<string, string> = {
  SUCCESS: '成功',
  FAILED: '失败',
  REJECTED: '已驳回',
};

const TradeRecordModal: React.FC<{
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
      title={isView ? '查看交易记录' : '编辑交易记录'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnHidden
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="交易记录编号" name="recordNo"><Input disabled /></Form.Item>
        <Form.Item label="订单ID" name="orderId"><Input disabled /></Form.Item>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="交易类型" name="tradeType"><Select disabled={isView} options={[
          { value: 'BUY', label: '买入' },
          { value: 'SELL', label: '卖出' },
        ]} /></Form.Item>
        <Form.Item label="交易金额" name="amount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="交易份额" name="shares"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="交易价格" name="price"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="手续费" name="fee"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="交易状态" name="status"><Select disabled={isView} options={Object.entries(statusMap).map(([value, label]) => ({ value, label }))} /></Form.Item>
        <Form.Item label="交易时间" name="tradeTime"><Input disabled /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const TradeRecordList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<TradeRecord[]>([]);
  const [searchText, setSearchText] = useState('');
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/trade-records');
  }, [track]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await getTradeRecordList();
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setDataSource(list);
      setTotal(list.length);
    } catch (error) {
      message.error('获取交易记录列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = (id: number) => {
    track('click', '/trade-records', { buttonId: 'delete', recordId: id });
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个交易记录吗？',
      onOk: async () => {
        try {
          const response = await deleteTradeRecord(id);
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

  const handleView = (record: any) => {
    track('click', '/trade-records', { buttonId: 'view', recordId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/trade-records', { buttonId: 'edit', recordId: record.id });
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
      title: '记录号',
      dataIndex: 'recordNo',
      key: 'recordNo',
    },
    {
      title: '订单ID',
      dataIndex: 'orderId',
      key: 'orderId',
      width: 100,
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
      title: '交易价格',
      dataIndex: 'price',
      key: 'price',
      render: (value: number) => value ? `¥${value.toFixed(4)}` : '-',
    },
    {
      title: '手续费',
      dataIndex: 'fee',
      key: 'fee',
      render: (value: number) => value ? `¥${value.toFixed(2)}` : '-',
    },
    {
      title: '交易状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => {
        const colorMap: Record<string, string> = {
          SUCCESS: 'green',
          FAILED: 'red',
          REJECTED: 'orange',
        };
        const statusMap: Record<string, string> = {
          SUCCESS: '成功',
          FAILED: '失败',
          REJECTED: '已驳回',
        };
        return <Tag color={colorMap[status] || 'default'}>{statusMap[status] || status}</Tag>;
      },
    },
    {
      title: '交易时间',
      dataIndex: 'tradeTime',
      key: 'tradeTime',
      render: (date: string) => new Date(date).toLocaleString(),
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
        </Space>
      ),
    },
  ];

  return (
    <Card
      title="交易记录管理"
      extra={
        <Space>
          <Search
            placeholder="搜索记录"
            allowClear
            onSearch={setSearchText}
            style={{ width: 200 }}
          />
          <Button
            type="primary"
            icon={<PlusOutlined />}
            onClick={() => { setModalData(undefined); setModalMode('edit'); setModalOpen(true); track('click', '/trade-records', { buttonId: 'add' }); }}
          >
            新增记录
          </Button>
        </Space>
      }
      style={{ maxWidth: 1200, margin: '0 auto' }}
    >
      <Table
        columns={columns}
        dataSource={dataSource.filter(item =>
          item.recordNo.toLowerCase().includes(searchText.toLowerCase()) ||
          item.tradeType.toLowerCase().includes(searchText.toLowerCase()) ||
          item.status.toLowerCase().includes(searchText.toLowerCase())
        )}
        loading={loading}
        rowKey="id"
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
          total: total,
        }}
        scroll={{ x: 1400 }}
      />
      <TradeRecordModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
            await tradeRecordApi.update(modalData.id, values);
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

export default TradeRecordList;