import React from 'react';
import { Card, Table, Tag } from 'antd';
import type { FundCompany } from '../../types';

const FundCompanyList: React.FC = () => {
  const columns = [
    {
      title: '公司代码',
      dataIndex: 'companyCode',
      key: 'companyCode',
    },
    {
      title: '公司名称',
      dataIndex: 'companyName',
      key: 'companyName',
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
    <Card title="基金公司列表">
      <Table columns={columns} dataSource={[]} rowKey="id" />
    </Card>
  );
};

export default FundCompanyList; 