import React from 'react';
import { Card, Descriptions, Tag } from 'antd';
import { useParams } from 'react-router-dom';
import type { Fund, FundTag } from '../../types';
import { useEffect } from 'react';
import { useTrackEvent } from '../../utils/request';

const FundDetail: React.FC = () => {
  const { id } = useParams();
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/funds/detail');
  }, [track]);
  // 导出、批量操作等操作可用track('click', '/funds/detail', { buttonId: 'export' })等
  // 假设已通过接口获取fund对象
  const fund: Fund = {
    id: Number(id),
    fundCode: '110022',
    fundName: '易方达消费行业股票',
    tags: [
      { id: 3, tagName: '消费主题', tagCategory: 'THEME', status: 'ACTIVE', createdAt: '', updatedAt: '' },
      { id: 2, tagName: '成长投资', tagCategory: 'STYLE', status: 'ACTIVE', createdAt: '', updatedAt: '' },
    ],
    status: 'ACTIVE',
    createdAt: '',
    updatedAt: '',
  };

  return (
    <Card title="基金详情">
      <Descriptions bordered>
        <Descriptions.Item label="基金ID">{fund.id}</Descriptions.Item>
        <Descriptions.Item label="基金代码">{fund.fundCode}</Descriptions.Item>
        <Descriptions.Item label="基金名称">{fund.fundName}</Descriptions.Item>
        <Descriptions.Item label="标签">
          {fund.tags?.map(tag => (
            <Tag key={tag.id} color="blue">{tag.tagName}</Tag>
          ))}
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