import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getStrategyList, deleteStrategy } from '../../api/strategy';
import type { Strategy } from '../../types';

const { Search } = Input;

const StrategyList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<Strategy[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getStrategyList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取策略列表失败');
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
      content: '确定要删除这个策略吗？',
      onOk: async () => {
        try {
          const response = await deleteStrategy(id);
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
      title: '策略代码',
      dataIndex: 'strategyCode',
      key: 'strategyCode',
    },
    {
      title: '策略名称',
      dataIndex: 'strategyName',
      key: 'strategyName',
    },
    {
      title: '策略类型',
      dataIndex: 'strategyType',
      key: 'strategyType',
    },
    {
      title: '风险等级',
      dataIndex: 'riskLevel',
      key: 'riskLevel',
      render: (riskLevel: string) => {
        const colorMap: Record<string, string> = {
          'LOW': 'green',
          'MEDIUM': 'orange',
          'HIGH': 'red',
        };
        return <Tag color={colorMap[riskLevel] || 'default'}>{riskLevel}</Tag>;
      },
    },
    {
      title: '目标收益',
      dataIndex: 'targetReturn',
      key: 'targetReturn',
      render: (value: number) => value ? `${(value * 100).toFixed(2)}%` : '-',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'ACTIVE' ? 'green' : 'red'}>
          {status === 'ACTIVE' ? '启用' : '禁用'}
        </Tag>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 250,
      render: (_: any, record: Strategy) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/strategy/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/strategy/edit/${record.id}`}
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
    item.strategyName.toLowerCase().includes(searchText.toLowerCase()) ||
    item.strategyCode.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="策略管理" extra={
      <Space>
        <Search
          placeholder="搜索策略"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/strategy/add'}
        >
          新增策略
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

export default StrategyList; 