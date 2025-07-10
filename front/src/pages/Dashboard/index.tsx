import React, { useState, useEffect } from 'react';
import {
  Row,
  Col,
  Card,
  Statistic,
  Progress,
  Table,
  Tag,
  Space,
  Button,
  Typography,
  Divider,
  List,
  Avatar,
  Badge,
} from 'antd';
import {
  FundOutlined,
  ExperimentOutlined,
  SettingOutlined,
  ShoppingOutlined,
  TransactionOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined,
  EyeOutlined,
  EditOutlined,
  DeleteOutlined,
} from '@ant-design/icons';
import { Line, Pie, Column } from '@ant-design/plots';
import request from '../../utils/request';

const { Title, Text } = Typography;

const Dashboard: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [statistics, setStatistics] = useState([
    { title: '基金总数', value: 0, prefix: <FundOutlined />, color: '#1890ff', change: 0, changeType: 'up' },
    { title: '因子数量', value: 0, prefix: <ExperimentOutlined />, color: '#52c41a', change: 0, changeType: 'up' },
    { title: '策略数量', value: 0, prefix: <SettingOutlined />, color: '#faad14', change: 0, changeType: 'up' },
    { title: '产品数量', value: 0, prefix: <ShoppingOutlined />, color: '#f5222d', change: 0, changeType: 'up' },
  ]);
  const [recentTrades, setRecentTrades] = useState<any[]>([]);
  const [strategyPerformance, setStrategyPerformance] = useState<any[]>([]);
  const [productMap, setProductMap] = useState<{ [id: number]: string }>({});

  useEffect(() => {
    setLoading(true);
    Promise.all([
      request.get('/funds', { params: { page: 0, size: 1 } }),
      request.get('/factors', { params: { page: 0, size: 1 } }),
      request.get('/strategies', { params: { page: 0, size: 1 } }),
      request.get('/products', { params: { page: 0, size: 1000 } }), // 拉全量产品
      request.get('/trade-orders', { params: { page: 0, size: 5, sort: 'createdAt,desc' } }),
      request.get('/strategy-backtests', { params: { page: 0, size: 5, sort: 'createdAt,desc' } }),
    ]).then(([
      fundsRes, factorsRes, strategiesRes, productsRes, tradesRes, backtestsRes
    ]) => {
      // 统计量兼容 data 为数组或对象
      const getCount = (res: any) =>
        Array.isArray(res.data.data)
          ? res.data.data.length
          : res.data.data?.totalElements || 0;

      setStatistics([
        { title: '基金总数', value: getCount(fundsRes), prefix: <FundOutlined />, color: '#1890ff', change: 0, changeType: 'up' },
        { title: '因子数量', value: getCount(factorsRes), prefix: <ExperimentOutlined />, color: '#52c41a', change: 0, changeType: 'up' },
        { title: '策略数量', value: getCount(strategiesRes), prefix: <SettingOutlined />, color: '#faad14', change: 0, changeType: 'up' },
        { title: '产品数量', value: getCount(productsRes), prefix: <ShoppingOutlined />, color: '#f5222d', change: 0, changeType: 'up' },
      ]);

      // 构建产品ID到名称的映射
      const products = Array.isArray(productsRes.data.data)
        ? productsRes.data.data
        : productsRes.data.data?.content || [];
      const map: { [id: number]: string } = {};
      products.forEach((p: any) => {
        map[p.id] = p.productName || p.name || '-';
      });
      setProductMap(map);

      // 最近交易兼容 data 为数组或对象
      const trades = Array.isArray(tradesRes.data.data)
        ? tradesRes.data.data
        : tradesRes.data.data?.content || [];
      setRecentTrades(trades.map((t: any) => ({
        key: t.id,
        orderNo: t.orderNo || t.id,
        product: map[t.productId] || '-', // 用映射查找产品名称
        amount: t.amount,
        status: t.status,
        time: t.createdAt,
      })));

      // 策略表现兼容 data 为数组或对象
      const backtests = Array.isArray(backtestsRes.data.data)
        ? backtestsRes.data.data
        : backtestsRes.data.data?.content || [];
      setStrategyPerformance(backtests.map((b: any) => ({
        key: b.id,
        strategy: b.strategyName || b.strategyId,
        return: b.annualReturn,
        risk: b.volatility,
        sharpe: b.sharpeRatio,
      })));
    }).finally(() => setLoading(false));
  }, []);

  // 表格列定义
  const tradeColumns = [
    { title: '订单号', dataIndex: 'orderNo', key: 'orderNo' },
    { title: '产品名称', dataIndex: 'product', key: 'product' },
    { title: '金额', dataIndex: 'amount', key: 'amount', render: (amount: number) => `¥${amount?.toLocaleString?.() ?? '-'}` },
    { title: '状态', dataIndex: 'status', key: 'status', render: (status: string) => (<Tag color={status === 'completed' ? 'green' : 'orange'}>{status === 'completed' ? '已完成' : '处理中'}</Tag>) },
    { title: '时间', dataIndex: 'time', key: 'time' },
  ];
  const strategyColumns = [
    { title: '策略名称', dataIndex: 'strategy', key: 'strategy' },
    { title: '年化收益率', dataIndex: 'return', key: 'return', render: (value: number) => value !== undefined ? `${value}%` : '-' },
    { title: '风险指标', dataIndex: 'risk', key: 'risk', render: (value: number) => value !== undefined ? `${value}%` : '-' },
    { title: '夏普比率', dataIndex: 'sharpe', key: 'sharpe', render: (value: number) => value !== undefined ? value : '-' },
  ];

  return (
    <div style={{ padding: 24 }}>
      <Row gutter={24} style={{ marginBottom: 24 }}>
        {statistics.map((stat, idx) => (
          <Col key={stat.title} xs={24} sm={12} md={6}>
            <Card bordered={false} style={{ textAlign: 'center', minHeight: 110 }}>
              <Statistic
                title={stat.title}
                value={stat.value}
                prefix={stat.prefix}
                valueStyle={{ color: stat.color, fontWeight: 600 }}
                // 不显示增幅箭头
              />
            </Card>
          </Col>
        ))}
      </Row>
      <Row gutter={24}>
        <Col xs={24} md={12}>
          <Card title="最近交易" bordered={false} style={{ minHeight: 350 }}>
            <Table
              columns={tradeColumns}
              dataSource={recentTrades}
              loading={loading}
              pagination={false}
              size="small"
              scroll={{ x: 600 }}
            />
          </Card>
        </Col>
        <Col xs={24} md={12}>
          <Card title="策略表现" bordered={false} style={{ minHeight: 350 }}>
            <Table
              columns={strategyColumns}
              dataSource={strategyPerformance}
              loading={loading}
              pagination={false}
              size="small"
              scroll={{ x: 600 }}
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Dashboard; 