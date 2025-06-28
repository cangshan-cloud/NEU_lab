import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getUserPositionList, deleteUserPosition } from '../../api/trade';
import type { UserPosition } from '../../types';

const { Search } = Input;

const UserPositionList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<UserPosition[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getUserPositionList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取用户持仓列表失败');
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
      content: '确定要删除这个用户持仓吗？',
      onOk: async () => {
        try {
          const response = await deleteUserPosition(id);
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
      title: '持仓份额',
      dataIndex: 'shares',
      key: 'shares',
      render: (value: number) => value.toFixed(2),
    },
    {
      title: '持仓成本',
      dataIndex: 'cost',
      key: 'cost',
      render: (value: number) => `¥${value.toLocaleString()}`,
    },
    {
      title: '平均成本价',
      dataIndex: 'avgCostPrice',
      key: 'avgCostPrice',
      render: (value: number) => value ? `¥${value.toFixed(4)}` : '-',
    },
    {
      title: '市值',
      dataIndex: 'marketValue',
      key: 'marketValue',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '盈亏',
      dataIndex: 'profitLoss',
      key: 'profitLoss',
      render: (value: number) => {
        if (value === null || value === undefined) return '-';
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>¥{value.toLocaleString()}</span>;
      },
    },
    {
      title: '盈亏率',
      dataIndex: 'profitLossRate',
      key: 'profitLossRate',
      render: (value: number) => {
        if (value === null || value === undefined) return '-';
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>{(value * 100).toFixed(2)}%</span>;
      },
    },
    {
      title: '更新时间',
      dataIndex: 'updatedAt',
      key: 'updatedAt',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: UserPosition) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/trade/position/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/trade/position/edit/${record.id}`}
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
    item.userId.toString().includes(searchText) ||
    item.productId.toString().includes(searchText)
  );

  return (
    <Card title="用户持仓管理" extra={
      <Space>
        <Search
          placeholder="搜索用户ID或产品ID"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/trade/position/add'}
        >
          新增持仓
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

export default UserPositionList; 