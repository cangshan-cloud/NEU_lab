import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Input, Button, message, InputNumber, Select, Card, Spin } from 'antd';
import { createStrategy, updateStrategy, fetchStrategyDetail } from '../../api/strategy';
import type { StrategyDTO, StrategyVO } from '../../api/strategy';
import { useTrackEvent } from '../../utils/request';

const { Option } = Select;

const StrategyEdit: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/strategies/edit');
  }, [track]);
  const { id } = useParams<{ id?: string }>();
  const navigate = useNavigate();
  const [form] = Form.useForm<StrategyDTO>();
  const [loading, setLoading] = useState(false);
  const isEdit = !!id;

  useEffect(() => {
    if (isEdit && id) {
      setLoading(true);
      fetchStrategyDetail(Number(id))
        .then(res => {
          const d = res.data.data;
          form.setFieldsValue({
            strategyCode: d.strategyCode || '',
            strategyName: d.strategyName || '',
            strategyType: d.strategyType === '量化' ? 'QUANT' : d.strategyType === '主动' ? 'ACTIVE' : d.strategyType || '',
            riskLevel: d.riskLevel === '低' ? 'LOW' : d.riskLevel === '中' ? 'MEDIUM' : d.riskLevel === '高' ? 'HIGH' : d.riskLevel || '',
            targetReturn: d.targetReturn ?? undefined,
            maxDrawdown: d.maxDrawdown ?? undefined,
            investmentHorizon: d.investmentHorizon || '',
            description: d.description || '',
            factorTreeId: d.factorTreeId ?? undefined,
            parameters: d.parameters || '',
            status: d.status || 'ACTIVE',
          });
        })
        .catch(() => message.error('获取策略信息失败'))
        .finally(() => setLoading(false));
    } else {
      form.resetFields();
      form.setFieldsValue({ status: 'ACTIVE' });
    }
  }, [isEdit, id, form]);

  const onFinish = async (values: StrategyDTO) => {
    console.log('表单提交数据:', values);
    setLoading(true);
    try {
      if (isEdit && id) {
        await updateStrategy(Number(id), values);
        message.success('编辑成功');
        console.log('跳转到详情页');
        navigate(`/strategy/detail/${id}`);
      } else {
        const res = await createStrategy(values);
        message.success('新建成功');
        console.log('跳转到策略列表');
        navigate('/strategies');
      }
    } catch (e) {
      message.error('操作失败');
      console.error('onFinish error', e);
      // 临时加：即使失败也跳转，便于排查
      navigate('/strategies');
    } finally {
      setLoading(false);
      console.log('onFinish finally');
    }
  };

  return (
    <Card title={isEdit ? '编辑策略' : '新建策略'}>
      <Spin spinning={loading}>
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item
            name="strategyCode"
            label="策略代码"
            rules={[{ required: true, message: '请输入策略代码' }]}
          >
            <Input placeholder="请输入策略代码" />
          </Form.Item>
          <Form.Item
            name="strategyName"
            label="策略名称"
            rules={[{ required: true, message: '请输入策略名称' }]}
          >
            <Input placeholder="请输入策略名称" />
          </Form.Item>
          <Form.Item
            name="strategyType"
            label="类型"
            rules={[{ required: true, message: '请选择类型' }]}
          >
            <Select placeholder="请选择类型">
              <Option value="QUANT">量化</Option>
              <Option value="ACTIVE">主动</Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="riskLevel"
            label="风险等级"
            rules={[{ required: true, message: '请选择风险等级' }]}
          >
            <Select placeholder="请选择风险等级">
              <Option value="LOW">低</Option>
              <Option value="MEDIUM">中</Option>
              <Option value="HIGH">高</Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="targetReturn"
            label="目标收益率"
            rules={[{ required: true, message: '请输入目标收益率' }]}
          >
            <InputNumber min={0} max={1} step={0.01} style={{ width: '100%' }} addonAfter="%" placeholder="请输入目标收益率" />
          </Form.Item>
          <Form.Item
            name="maxDrawdown"
            label="最大回撤"
            rules={[{ required: true, message: '请输入最大回撤' }]}
          >
            <InputNumber min={0} max={1} step={0.01} style={{ width: '100%' }} addonAfter="%" placeholder="请输入最大回撤" />
          </Form.Item>
          <Form.Item
            name="investmentHorizon"
            label="投资期限"
          >
            <Input placeholder="请输入投资期限" />
          </Form.Item>
          <Form.Item
            name="description"
            label="描述"
          >
            <Input.TextArea rows={3} placeholder="请输入描述" />
          </Form.Item>
          <Form.Item
            name="factorTreeId"
            label="因子树ID"
          >
            <InputNumber min={1} style={{ width: '100%' }} placeholder="请输入因子树ID" />
          </Form.Item>
          <Form.Item
            name="parameters"
            label="参数"
          >
            <Input.TextArea rows={2} placeholder="请输入参数" />
          </Form.Item>
          <Form.Item
            name="status"
            label="状态"
            rules={[{ required: true, message: '请选择状态' }]}
          >
            <Select placeholder="请选择状态">
              <Option value="ACTIVE">启用</Option>
              <Option value="INACTIVE">禁用</Option>
            </Select>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading}>{isEdit ? '保存' : '创建'}</Button>
          </Form.Item>
          <Form.Item>
            <Button style={{ marginLeft: 8 }} onClick={() => navigate(-1)}>取消</Button>
          </Form.Item>
        </Form>
      </Spin>
    </Card>
  );
};

export default StrategyEdit; 