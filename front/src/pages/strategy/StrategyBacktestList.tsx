import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getStrategyBacktestList, deleteStrategyBacktest } from '../../api/strategy';
import type { StrategyBacktest } from '../../types';

const { Search } = Input;

const StrategyBacktestList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<StrategyBacktest[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getStrategyBacktestList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取策略回测列表失败');
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
      content: '确定要删除这个策略回测吗？',
      onOk: async () => {
        try {
          const response = await deleteStrategyBacktest(id);
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
      title: '策略ID',
      dataIndex: 'strategyId',
      key: 'strategyId',
      width: 100,
    },
    {
      title: '回测名称',
      dataIndex: 'backtestName',
      key: 'backtestName',
    },
    {
      title: '回测期间',
      key: 'period',
      render: (_: any, record: StrategyBacktest) => (
        <span>
          {new Date(record.startDate).toLocaleDateString()} - {new Date(record.endDate).toLocaleDateString()}
        </span>
      ),
    },
    {
      title: '总收益率',
      dataIndex: 'totalReturn',
      key: 'totalReturn',
      render: (value: number) => value ? `${(value * 100).toFixed(2)}%` : '-',
    },
    {
      title: '年化收益率',
      dataIndex: 'annualReturn',
      key: 'annualReturn',
      render: (value: number) => value ? `${(value * 100).toFixed(2)}%` : '-',
    },
    {
      title: '最大回撤',
      dataIndex: 'maxDrawdown',
      key: 'maxDrawdown',
      render: (value: number) => value ? `${(value * 100).toFixed(2)}%` : '-',
    },
    {
      title: '夏普比率',
      dataIndex: 'sharpeRatio',
      key: 'sharpeRatio',
      render: (value: number) => value ? value.toFixed(3) : '-',
    },
    {
      title: '胜率',
      dataIndex: 'winRate',
      key: 'winRate',
      render: (value: number) => value ? `${(value * 100).toFixed(2)}%` : '-',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'COMPLETED' ? 'green' : 'orange'}>
          {status === 'COMPLETED' ? '已完成' : '进行中'}
        </Tag>
      ),
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: StrategyBacktest) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/strategy/backtest/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/strategy/backtest/edit/${record.id}`}
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
    item.backtestName.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="策略回测管理" extra={
      <Space>
        <Search
          placeholder="搜索回测"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/strategy/backtest/add'}
        >
          新增回测
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

export default StrategyBacktestList; 