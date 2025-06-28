import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getCapitalFlowList, deleteCapitalFlow } from '../../api/trade';
import type { CapitalFlow } from '../../types';

const { Search } = Input;

const CapitalFlowList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<CapitalFlow[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getCapitalFlowList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取资金流水列表失败');
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
      content: '确定要删除这个资金流水吗？',
      onOk: async () => {
        try {
          const response = await deleteCapitalFlow(id);
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
      title: '流水号',
      dataIndex: 'flowNo',
      key: 'flowNo',
    },
    {
      title: '用户ID',
      dataIndex: 'userId',
      key: 'userId',
      width: 100,
    },
    {
      title: '流水类型',
      dataIndex: 'flowType',
      key: 'flowType',
      render: (flowType: string) => {
        const colorMap: Record<string, string> = {
          'DEPOSIT': 'green',
          'WITHDRAW': 'red',
          'BUY': 'blue',
          'SELL': 'orange',
          'FEE': 'purple',
        };
        const typeMap: Record<string, string> = {
          'DEPOSIT': '充值',
          'WITHDRAW': '提现',
          'BUY': '买入',
          'SELL': '卖出',
          'FEE': '手续费',
        };
        return <Tag color={colorMap[flowType] || 'default'}>{typeMap[flowType] || flowType}</Tag>;
      },
    },
    {
      title: '金额',
      dataIndex: 'amount',
      key: 'amount',
      render: (value: number) => {
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>¥{value.toLocaleString()}</span>;
      },
    },
    {
      title: '变动前余额',
      dataIndex: 'balanceBefore',
      key: 'balanceBefore',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '变动后余额',
      dataIndex: 'balanceAfter',
      key: 'balanceAfter',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '订单ID',
      dataIndex: 'orderId',
      key: 'orderId',
      width: 100,
      render: (value: number) => value || '-',
    },
    {
      title: '产品ID',
      dataIndex: 'productId',
      key: 'productId',
      width: 100,
      render: (value: number) => value || '-',
    },
    {
      title: '流水状态',
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
      title: '流水时间',
      dataIndex: 'flowTime',
      key: 'flowTime',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: CapitalFlow) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/trade/flow/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/trade/flow/edit/${record.id}`}
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
    item.flowNo.toLowerCase().includes(searchText.toLowerCase()) ||
    item.flowType.toLowerCase().includes(searchText.toLowerCase()) ||
    item.status.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="资金流水管理" extra={
      <Space>
        <Search
          placeholder="搜索流水号、类型或状态"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/trade/flow/add'}
        >
          新增流水
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

export default CapitalFlowList; 