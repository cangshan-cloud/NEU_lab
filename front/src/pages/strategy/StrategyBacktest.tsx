import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Input, Button, InputNumber, DatePicker, Card, message, Spin, Descriptions, Space } from 'antd';
import { backtestStrategy } from '../../api/strategy';
import type { StrategyBacktestDTO, StrategyBacktestVO } from '../../api/strategy';
import dayjs from 'dayjs';
import { useTrackEvent } from '../../utils/request';

const { RangePicker } = DatePicker;

const StrategyBacktest: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/strategy-backtest');
  }, [track]);
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [form] = Form.useForm<StrategyBacktestDTO>();
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<StrategyBacktestVO | null>(null);

  const formatPercent = (v?: number) => v !== undefined && v !== null ? `${(v * 100).toFixed(2)}%` : '-';
  const formatTime = (t?: string) => t ? new Date(t).toLocaleString() : '-';

  const onFinish = async (values: any) => {
    if (!id) return;
    setLoading(true);
    try {
      const dto: StrategyBacktestDTO = {
        strategyId: Number(id),
        backtestName: values.backtestName,
        backtestType: values.backtestType,
        startDate: values.dates[0].format('YYYY-MM-DD'),
        endDate: values.dates[1].format('YYYY-MM-DD'),
        initialCapital: values.initialCapital,
        parameters: values.parameters,
      };
      const res = await backtestStrategy(Number(id), dto);
      setResult(res.data.data);
      message.success('回测成功');
    } catch {
      message.error('回测失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="策略回测" extra={<Button onClick={() => navigate(-1)}>返回</Button>}>
      <Spin spinning={loading}>
        {!result ? (
          <Form form={form} layout="vertical" onFinish={onFinish} initialValues={{}}>
            <Form.Item
              name="backtestName"
              label="回测名称"
              rules={[{ required: true, message: '请输入回测名称' }]}
            >
              <Input placeholder="请输入回测名称" />
            </Form.Item>
            <Form.Item
              name="backtestType"
              label="回测类型"
              rules={[{ required: true, message: '请输入回测类型' }]}
            >
              <Input placeholder="请输入回测类型" />
            </Form.Item>
            <Form.Item
              name="dates"
              label="回测区间"
              rules={[{ required: true, message: '请选择回测区间' }]}
            >
              <RangePicker style={{ width: '100%' }} />
            </Form.Item>
            <Form.Item
              name="initialCapital"
              label="初始资金"
              rules={[{ required: true, message: '请输入初始资金' }]}
            >
              <InputNumber min={0} style={{ width: '100%' }} placeholder="请输入初始资金" />
            </Form.Item>
            <Form.Item
              name="parameters"
              label="参数"
            >
              <Input.TextArea rows={2} placeholder="请输入参数" />
            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit">发起回测</Button>
            </Form.Item>
          </Form>
        ) : (
          <>
            <Descriptions bordered column={2} title="回测结果">
              <Descriptions.Item label="回测名称">{result.backtestName}</Descriptions.Item>
              <Descriptions.Item label="回测类型">{result.backtestType}</Descriptions.Item>
              <Descriptions.Item label="回测区间">{result.startDate} ~ {result.endDate}</Descriptions.Item>
              <Descriptions.Item label="初始资金">{result.initialCapital}</Descriptions.Item>
              <Descriptions.Item label="最终价值">{result.finalValue}</Descriptions.Item>
              <Descriptions.Item label="总收益率">{formatPercent(result.totalReturn)}</Descriptions.Item>
              <Descriptions.Item label="年化收益率">{formatPercent(result.annualReturn)}</Descriptions.Item>
              <Descriptions.Item label="最大回撤">{formatPercent(result.maxDrawdown)}</Descriptions.Item>
              <Descriptions.Item label="夏普比率">{result.sharpeRatio}</Descriptions.Item>
              <Descriptions.Item label="波动率">{result.volatility}</Descriptions.Item>
              <Descriptions.Item label="胜率">{formatPercent(result.winRate)}</Descriptions.Item>
              <Descriptions.Item label="回测结果">{result.backtestResult}</Descriptions.Item>
              <Descriptions.Item label="状态">{result.status}</Descriptions.Item>
              <Descriptions.Item label="创建时间">{formatTime(result.createdAt)}</Descriptions.Item>
              <Descriptions.Item label="更新时间">{formatTime(result.updatedAt)}</Descriptions.Item>
              <Descriptions.Item label="明细">{result.results}</Descriptions.Item>
            </Descriptions>
            <Space style={{ marginTop: 24 }}>
              <Button type="primary" onClick={() => navigate(`/strategies/backtest-list/${id}`)}>查看回测历史</Button>
              <Button onClick={() => navigate(-1)}>返回</Button>
            </Space>
          </>
        )}
      </Spin>
    </Card>
  );
};

export default StrategyBacktest; 