import React, { useState } from 'react';
import { Form, Input, Button, message, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import { productApi } from '../../api/product';

const ProductAdd: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      await productApi.create(values);
      message.success('产品创建成功！');
      navigate('/product');
    } catch (e) {
      message.error('产品创建失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="新建产品" style={{ maxWidth: 500, margin: '40px auto' }}>
      <Form layout="vertical" onFinish={onFinish}>
        <Form.Item label="产品名称" name="name" rules={[{ required: true, message: '请输入产品名称' }]}> 
          <Input placeholder="请输入产品名称" />
        </Form.Item>
        <Form.Item label="产品类型" name="type" rules={[{ required: true, message: '请输入产品类型' }]}> 
          <Input placeholder="请输入产品类型" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading} block>
            提交
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default ProductAdd; 