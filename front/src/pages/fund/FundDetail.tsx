import React from 'react';
import { Card, Descriptions, Tag } from 'antd';
import { useParams } from 'react-router-dom';

const FundDetail: React.FC = () => {
  const { id } = useParams();

  return (
    <Card title="基金详情">
      <Descriptions bordered>
        <Descriptions.Item label="基金ID">{id}</Descriptions.Item>
        <Descriptions.Item label="基金代码">110022</Descriptions.Item>
        <Descriptions.Item label="基金名称">易方达消费行业股票</Descriptions.Item>
        <Descriptions.Item label="基金类型">
          <Tag color="red">股票型</Tag>
        </Descriptions.Item>
        <Descriptions.Item label="风险等级">
          <Tag color="red">高风险</Tag>
        </Descriptions.Item>
        <Descriptions.Item label="基金规模">350.50亿元</Descriptions.Item>
      </Descriptions>
    </Card>
  );
};

export default FundDetail; 