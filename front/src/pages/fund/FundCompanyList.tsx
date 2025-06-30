import React, { useEffect, useState } from 'react';
import { Card, Table, message, Spin } from 'antd';
import { fundCompanyApi } from '../../api/fund';
import type { FundCompany } from '../../types';

const FundCompanyList: React.FC = () => {
  const [data, setData] = useState<FundCompany[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const response = await fundCompanyApi.getAll();
        console.log('API响应：', response);
        
        // 后端返回结构：{code: 0, message: "success", data: [...]}
        if (response.data && Array.isArray(response.data.data)) {
          setData(response.data.data);
        } else {
          setData([]);
          message.warning('数据格式异常');
        }
      } catch (error) {
        console.error('获取基金公司列表失败：', error);
        message.error('获取基金公司列表失败');
        setData([]);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const columns = [
    {
      title: '公司ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: '公司名称',
      dataIndex: 'name',
      key: 'name',
      width: 200,
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      width: 180,
    },
    {
      title: '更新时间',
      dataIndex: 'updatedAt',
      key: 'updatedAt',
      width: 180,
    },
  ];

  return (
    <Card title="基金公司列表">
      <Spin spinning={loading}>
        <Table 
          columns={columns} 
          dataSource={data} 
          rowKey="id"
          pagination={{
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total) => `共 ${total} 条记录`,
          }}
          scroll={{ x: 1000 }}
        />
      </Spin>
    </Card>
  );
};

export default FundCompanyList; 