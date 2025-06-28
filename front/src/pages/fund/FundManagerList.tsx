import React from 'react';
import { Card, Table, Tag } from 'antd';

const FundManagerList: React.FC = () => {
  const columns = [
    {
      title: '经理代码',
      dataIndex: 'managerCode',
      key: 'managerCode',
    },
    {
      title: '经理姓名',
      dataIndex: 'managerName',
      key: 'managerName',
    },
    {
      title: '从业年限',
      dataIndex: 'experienceYears',
      key: 'experienceYears',
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
    <Card title="基金经理列表">
      <Table columns={columns} dataSource={[]} rowKey="id" />
    </Card>
  );
};

export default FundManagerList; 