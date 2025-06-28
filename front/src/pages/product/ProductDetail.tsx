import React, { useState, useEffect } from 'react';
import { Card, Descriptions, Tag, Button, Space, message, Spin } from 'antd';
import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeftOutlined, EditOutlined } from '@ant-design/icons';
import { getProductById } from '../../api/product';
import type { Product } from '../../types';

const ProductDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [product, setProduct] = useState<Product | null>(null);

  const fetchData = async () => {
    if (!id) return;
    setLoading(true);
    try {
      const response = await getProductById(parseInt(id));
      if (response.data.code === 200) {
        setProduct(response.data.data);
      }
    } catch (error) {
      message.error('获取产品详情失败');
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

  if (!product) {
    return (
      <Card>
        <div style={{ textAlign: 'center', padding: '50px' }}>
          产品不存在
        </div>
      </Card>
    );
  }

  return (
    <div>
      <Card 
        title="产品详情" 
        extra={
          <Space>
            <Button 
              icon={<ArrowLeftOutlined />} 
              onClick={() => navigate('/product/list')}
            >
              返回列表
            </Button>
            <Button 
              type="primary" 
              icon={<EditOutlined />}
              onClick={() => navigate(`/product/edit/${id}`)}
            >
              编辑产品
            </Button>
          </Space>
        }
      >
        <Descriptions bordered column={2}>
          <Descriptions.Item label="产品ID">{product.id}</Descriptions.Item>
          <Descriptions.Item label="产品代码">{product.productCode}</Descriptions.Item>
          <Descriptions.Item label="产品名称">{product.productName}</Descriptions.Item>
          <Descriptions.Item label="产品类型">{product.productType}</Descriptions.Item>
          <Descriptions.Item label="风险等级">
            <Tag color={
              product.riskLevel === 'LOW' ? 'green' : 
              product.riskLevel === 'MEDIUM' ? 'orange' : 'red'
            }>
              {product.riskLevel}
            </Tag>
          </Descriptions.Item>
          <Descriptions.Item label="目标收益">
            {product.targetReturn ? `${(product.targetReturn * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="最大回撤">
            {product.maxDrawdown ? `${(product.maxDrawdown * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="投资期限">{product.investmentHorizon}</Descriptions.Item>
          <Descriptions.Item label="最小投资">
            {product.minInvestment ? `¥${product.minInvestment.toLocaleString()}` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="最大投资">
            {product.maxInvestment ? `¥${product.maxInvestment.toLocaleString()}` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="管理费率">
            {product.managementFee ? `${(product.managementFee * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="业绩费率">
            {product.performanceFee ? `${(product.performanceFee * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="认购费率">
            {product.subscriptionFee ? `${(product.subscriptionFee * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="赎回费率">
            {product.redemptionFee ? `${(product.redemptionFee * 100).toFixed(2)}%` : '-'}
          </Descriptions.Item>
          <Descriptions.Item label="状态">
            <Tag color={product.status === 'ACTIVE' ? 'green' : 'red'}>
              {product.status === 'ACTIVE' ? '启用' : '禁用'}
            </Tag>
          </Descriptions.Item>
          <Descriptions.Item label="创建时间">
            {new Date(product.createdAt).toLocaleString()}
          </Descriptions.Item>
          <Descriptions.Item label="更新时间">
            {new Date(product.updatedAt).toLocaleString()}
          </Descriptions.Item>
          <Descriptions.Item label="产品描述" span={2}>
            {product.description || '-'}
          </Descriptions.Item>
        </Descriptions>
      </Card>
    </div>
  );
};

export default ProductDetail; 