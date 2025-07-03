import React, { useState, useEffect } from 'react';
import { Button, Form, InputNumber, Select, message, Card } from 'antd';
import { rebalancePortfolio } from '../../api/trade';
import { getPortfolios, getProducts } from '../../api/fund';

const PortfolioRebalance: React.FC = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [portfolios, setPortfolios] = useState<{ label: string; value: number }[]>([]);
  const [products, setProducts] = useState<{ label: string; value: number }[]>([]);

  useEffect(() => {
    getPortfolios().then(res => {
      const raw: any = res.data;
      const list = Array.isArray(raw) ? raw : (raw && Array.isArray(raw.records) ? raw.records : []);
      const total = (raw && typeof raw.total === 'number') ? raw.total : list.length;
      setPortfolios(list.map((item: any) => ({
        label: item.portfolioName,
        value: item.id
      })));
    });
    getProducts().then(res => setProducts((res.data || []).map((item: any) => ({
      label: item.productName,
      value: item.id
    }))));
  }, []);

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      await rebalancePortfolio(values);
      message.success('组合调仓指令已下发');
      form.resetFields();
    } catch (e) {
      message.error('调仓失败');
    }
    setLoading(false);
  };

  return (
    <Card title="组合批量调仓" style={{ maxWidth: 700, margin: '0 auto' }}>
      <Form form={form} onFinish={onFinish} layout="vertical">
        <Form.Item name="portfolioId" label="选择组合" rules={[{ required: true }]}> 
          <Select options={portfolios} placeholder="请选择组合" />
        </Form.Item>
        <Form.List name="items" rules={[{ validator: (_, value) => value && value.length > 0 ? Promise.resolve() : Promise.reject('请至少添加一个标的') }]}> 
          {(fields, { add, remove }) => (
            <>
              {fields.map(({ key, name }) => (
                <div key={key} style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
                  <Form.Item name={[name, 'productId']} label="标的" rules={[{ required: true }]}> 
                    <Select options={products} style={{ width: 200 }} placeholder="请选择标的" />
                  </Form.Item>
                  <Form.Item name={[name, 'weight']} label="权重" rules={[{ required: true, type: 'number', min: 0, max: 1 }]}> 
                    <InputNumber min={0} max={1} step={0.01} style={{ width: 120 }} placeholder="0~1" />
                  </Form.Item>
                  <Button onClick={() => remove(name)} danger type="link">删除</Button>
                </div>
              ))}
              <Button type="dashed" onClick={() => add()} block>
                添加标的
              </Button>
            </>
          )}
        </Form.List>
        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading}>
            批量调仓
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default PortfolioRebalance; 