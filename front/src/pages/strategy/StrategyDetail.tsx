import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { fetchStrategyDetail } from '../../api/strategy';
import type { StrategyVO } from '../../api/strategy';
import { Card, Descriptions, Button, message, Spin, Tag, Space } from 'antd';

const StrategyDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [data, setData] = useState<StrategyVO | null>(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!id) return;
    setLoading(true);
    fetchStrategyDetail(Number(id))
      .then(res => setData(res.data.data))
      .catch(() => message.error('获取详情失败'))
      .finally(() => setLoading(false));
  }, [id]);

  const formatPercent = (v?: number) => v !== undefined && v !== null ? `${(v * 100).toFixed(2)}%` : '-';
  const formatTime = (t?: string) => t ? new Date(t).toLocaleString() : '-';

  return (
    <Card title="策略详情" extra={<Button onClick={() => navigate(-1)}>返回列表</Button>}>
      {loading ? <Spin /> : data && (
        <>
          <Descriptions bordered column={2}>
            <Descriptions.Item label="策略代码">{data.strategyCode}</Descriptions.Item>
            <Descriptions.Item label="策略名称">{data.strategyName}</Descriptions.Item>
            <Descriptions.Item label="类型">{data.strategyType}</Descriptions.Item>
            <Descriptions.Item label="风险等级">
              <Tag color={data.riskLevel === '低' ? 'green' : data.riskLevel === '中' ? 'orange' : 'red'}>{data.riskLevel}</Tag>
            </Descriptions.Item>
            <Descriptions.Item label="目标收益率">{formatPercent(data.targetReturn)}</Descriptions.Item>
            <Descriptions.Item label="最大回撤">{formatPercent(data.maxDrawdown)}</Descriptions.Item>
            <Descriptions.Item label="投资期限">{data.investmentHorizon}</Descriptions.Item>
            <Descriptions.Item label="状态">
              <Tag color={data.status === 'ACTIVE' ? 'green' : 'red'}>{data.status === 'ACTIVE' ? '启用' : '禁用'}</Tag>
            </Descriptions.Item>
            <Descriptions.Item label="因子树ID">{data.factorTreeId}</Descriptions.Item>
            <Descriptions.Item label="参数">{data.parameters}</Descriptions.Item>
            <Descriptions.Item label="描述" span={2}>{data.description}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{formatTime(data.createdAt)}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{formatTime(data.updatedAt)}</Descriptions.Item>
          </Descriptions>
          <Space style={{ marginTop: 24 }}>
            <Button type="primary" onClick={() => navigate(`/strategies/edit/${data.id}`)}>编辑</Button>
            <Button onClick={() => navigate(`/strategies/backtest/${data.id}`)}>回测</Button>
            <Button onClick={() => navigate(`/strategies/backtest-list/${data.id}`)}>回测历史</Button>
          </Space>
        </>
      )}
    </Card>
  );
};

export default StrategyDetail; 