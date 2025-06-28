import React, { useState, useEffect } from 'react';
import { Card, Descriptions, Tag, Button, Space, message, Spin } from 'antd';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeftOutlined, EditOutlined } from '@ant-design/icons';
import { getStrategyById } from '../../api/strategy';
import type { Strategy } from '../../types';

const StrategyDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [strategy, setStrategy] = useState<Strategy | null>(null);

  const fetchData = async () => {
    if (!id) return;
    setLoading(true);
    try {
      const response = await getStrategyById(parseInt(id));
      if (response.data.code === 200) {
        setStrategy(response.data.data);
      }
    } catch (error) {
      message.error('获取策略详情失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  if (loading) {
    return (
      <div style={{ textAlign: 'center', padding: '50px' }}>
        <Spin size="large" />
      </div>
    );
  }

  if (!strategy) {
    return (
      <Card>
        <div style={{ textAlign: 'center', padding: '50px' }}>
          策略不存在
        </div>
      </Card>
    );
  }

  return (
    <div>
      <Card 
        title="策略详情" 
        extra={
          <Space>
            <Button 
              icon={<ArrowLeftOutlined />} 
              onClick={() => navigate('/strategy/list')}
            >
              返回列表
            </Button>
            <Button 
              type="primary" 
              icon={<EditOutlined />}
              onClick={() => navigate(`/strategy/edit/${id}`)}
            >
              编辑策略
            </Button>
          </Space>
        }
      >
        <Descriptions bordered column={2}>
          <Descriptions.Item label="策略ID">{strategy.id}</Descriptions.Item>
          <Descriptions.Item label="策略代码">{strategy.strategyCode}</Descriptions.Item>
          <Descriptions.Item label="策略名称">{strategy.strategyName}</Descriptions.Item>
          <Descriptions.Item label="策略类型">{strategy.strategyType}</Descriptions.Item>
          <Descriptions.Item label="风险等级">
            <Tag color={
              strategy.riskLevel === 'LOW' ? 'green' : 
              strategy.riskLevel === 'MEDIUM' ? 'orange' : 'red'
            }>
              {strategy.riskLevel}
            </Tag>
          </Descriptions.Item>
          <Descriptions.Item label="目标收益">
            {strategy.targetReturn ? `${(strategy.targetReturn * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="最大回撤">
            {strategy.maxDrawdown ? `${(strategy.maxDrawdown * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="投资期限">{strategy.investmentHorizon}</Descriptions.Item>
          <Descriptions.Item label="状态">
            <Tag color={strategy.status === 'ACTIVE' ? 'green' : 'red'}>
              {strategy.status === 'ACTIVE' ? '启用' : '禁用'}
            </Tag>
          </Descriptions.Item>
          <Descriptions.Item label="创建时间">
            {new Date(strategy.createdAt).toLocaleString()}
          </Descriptions.Item>
          <Descriptions.Item label="更新时间">
            {new Date(strategy.updatedAt).toLocaleString()}
          </Descriptions.Item>
          <Descriptions.Item label="描述" span={2}>
            {strategy.description || '-'}
          </Descriptions.Item>
        </Descriptions>
      </Card>
    </div>
  );
};

export default StrategyDetail; 