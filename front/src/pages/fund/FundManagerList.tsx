import React, { useEffect, useState } from 'react';
import { Card, Table } from 'antd';
import { fundManagerApi } from '../../api/fund';
import type { FundManager } from '../../types';

const FundManagerList: React.FC = () => {
  const [data, setData] = useState<FundManager[]>([]);

  useEffect(() => {
    fundManagerApi.getList().then(res => {
      // 兼容分页和非分页结构
      if (Array.isArray(res.data)) {
        setData(res.data);
      } else if (Array.isArray(res.data.data)) {
        setData(res.data.data);
      } else {
        setData([]);
      }
    });
  }, []);

  const columns = [
    {
      title: '经理ID',
      dataIndex: 'id',
      key: 'id',
    },
    {
      title: '经理姓名',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '公司ID',
      dataIndex: 'companyId',
      key: 'companyId',
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
    },
  ];

  return (
    <Card title="基金经理列表">
      <Table columns={columns} dataSource={data} rowKey="id" />
    </Card>
  );
};

export default FundManagerList; 