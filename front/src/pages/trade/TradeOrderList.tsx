import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getTradeOrderList, deleteTradeOrder } from '../../api/trade';
import type { TradeOrder } from '../../types';

const { Search } = Input;

const TradeOrderList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<TradeOrder[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getTradeOrderList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
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
          'PENDING': 'orange',
          'PROCESSING': 'blue',
          'COMPLETED': 'green',
          'CANCELLED': 'red',
        };
        const statusMap: Record<string, string> = {
          'PENDING': '待处理',
          'PROCESSING': '处理中',
          'COMPLETED': '已完成',
          'CANCELLED': '已取消',
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
      render: (_: any, record: TradeOrder) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/trade/order/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/trade/order/edit/${record.id}`}
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

  const filteredData = dataSource.filter(item =>
    item.orderNo.toLowerCase().includes(searchText.toLowerCase()) ||
    item.tradeType.toLowerCase().includes(searchText.toLowerCase()) ||
    item.status.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="交易订单管理" extra={
      <Space>
        <Search
          placeholder="搜索订单"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/trade/order/add'}
        >
          新增订单
        </Button>
      </Space>
    }>
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
      />
    </Card>
  );
};

export default TradeOrderList; 