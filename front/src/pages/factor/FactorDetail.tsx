import React from 'react';
import { Card, Descriptions, Tag } from 'antd';
import { useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { useTrackEvent } from '../../utils/request';

const FactorDetail: React.FC = () => {
  const { id } = useParams();
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/factors/detail');
  }, [track]);
  // 编辑、导出、查看历史等操作可用track('click', '/factors/detail', { buttonId: 'edit' })等

  return (
    <Card title="因子详情">
      <Descriptions bordered>
        <Descriptions.Item label="因子ID">{id}</Descriptions.Item>
        <Descriptions.Item label="因子代码">PE</Descriptions.Item>
        <Descriptions.Item label="因子名称">市盈率</Descriptions.Item>
        <Descriptions.Item label="因子分类">估值因子</Descriptions.Item>
        <Descriptions.Item label="更新频率">每日</Descriptions.Item>
        <Descriptions.Item label="状态">
          <Tag color="green">正常</Tag>
        </Descriptions.Item>
      </Descriptions>
    </Card>
  );
};

export default FactorDetail; 