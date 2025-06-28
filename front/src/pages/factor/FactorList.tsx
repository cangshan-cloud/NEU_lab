import React from 'react';
import { Card, Table, Tag } from 'antd';

const FactorList: React.FC = () => {
  const columns = [
    {
      title: '因子代码',
      dataIndex: 'factorCode',
      key: 'factorCode',
    },
    {
      title: '因子名称',
      dataIndex: 'factorName',
      key: 'factorName',
    },
    {
      title: '因子分类',
      dataIndex: 'factorCategory',
      key: 'factorCategory',
    },
    {
      title: '更新频率',
      dataIndex: 'updateFrequency',
      key: 'updateFrequency',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'ACTIVE' ? 'green' : 'red'}>
          {status === 'ACTIVE' ? '正常' : '停用'}
        </Tag>
      ),
    },
  ];

  return (
    <Card title="因子列表">
      <Table columns={columns} dataSource={[]} rowKey="id" />
    </Card>
  );
};

export default FactorList; 