import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getTradeRecordList, deleteTradeRecord } from '../../api/trade';
import type { TradeRecord } from '../../types';

const { Search } = Input;

const TradeRecordList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<TradeRecord[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getTradeRecordList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
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
          'SUCCESS': 'green',
          'FAILED': 'red',
          'PENDING': 'orange',
        };
        const statusMap: Record<string, string> = {
          'SUCCESS': '成功',
          'FAILED': '失败',
          'PENDING': '处理中',
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
      render: (_: any, record: TradeRecord) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/trade/record/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/trade/record/edit/${record.id}`}
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
    item.recordNo.toLowerCase().includes(searchText.toLowerCase()) ||
    item.tradeType.toLowerCase().includes(searchText.toLowerCase()) ||
    item.status.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="交易记录管理" extra={
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
          onClick={() => window.location.href = '/trade/record/add'}
        >
          新增记录
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

export default TradeRecordList; 