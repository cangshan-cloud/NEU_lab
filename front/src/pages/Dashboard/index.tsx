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

const { Title, Text } = Typography;

const Dashboard: React.FC = () => {
  const [loading, setLoading] = useState(false);

  // 统计数据
  const statistics = [
    {
      title: '基金总数',
      value: 1256,
      prefix: <FundOutlined />,
      color: '#1890ff',
      change: 12.5,
      changeType: 'up',
    },
    {
      title: '因子数量',
      value: 89,
      prefix: <ExperimentOutlined />,
      color: '#52c41a',
      change: 8.2,
      changeType: 'up',
    },
    {
      title: '策略数量',
      value: 45,
      prefix: <SettingOutlined />,
      color: '#faad14',
      change: -2.1,
      changeType: 'down',
    },
    {
      title: '产品数量',
      value: 23,
      prefix: <ShoppingOutlined />,
      color: '#f5222d',
      change: 15.3,
      changeType: 'up',
    },
  ];

  // 最近交易数据
  const recentTrades = [
    {
      key: '1',
      orderNo: 'T20241201001',
      product: '稳健配置组合',
      amount: 100000,
      status: 'completed',
      time: '2024-12-01 10:30:00',
    },
    {
      key: '2',
      orderNo: 'T20241201002',
      product: '成长进取组合',
      amount: 50000,
      status: 'processing',
      time: '2024-12-01 11:15:00',
    },
    {
      key: '3',
      orderNo: 'T20241201003',
      product: '价值投资组合',
      amount: 200000,
      status: 'completed',
      time: '2024-12-01 14:20:00',
    },
  ];

  // 策略表现数据
  const strategyPerformance = [
    { strategy: '稳健配置', return: 8.5, risk: 3.2, sharpe: 1.8 },
    { strategy: '成长进取', return: 15.2, risk: 8.5, sharpe: 1.4 },
    { strategy: '价值投资', return: 12.1, risk: 6.8, sharpe: 1.6 },
    { strategy: '量化对冲', return: 6.8, risk: 2.1, sharpe: 2.1 },
  ];

  // 收益率趋势数据
  const returnTrendData = [
    { date: '2024-01', value: 2.1, type: '稳健配置' },
    { date: '2024-02', value: 2.8, type: '稳健配置' },
    { date: '2024-03', value: 3.2, type: '稳健配置' },
    { date: '2024-04', value: 3.8, type: '稳健配置' },
    { date: '2024-05', value: 4.2, type: '稳健配置' },
    { date: '2024-06', value: 4.8, type: '稳健配置' },
    { date: '2024-07', value: 5.1, type: '稳健配置' },
    { date: '2024-08', value: 5.8, type: '稳健配置' },
    { date: '2024-09', value: 6.2, type: '稳健配置' },
    { date: '2024-10', value: 7.1, type: '稳健配置' },
    { date: '2024-11', value: 7.8, type: '稳健配置' },
    { date: '2024-12', value: 8.5, type: '稳健配置' },
  ];

  // 资产配置数据
  const assetAllocationData = [
    { type: '股票型基金', value: 45 },
    { type: '债券型基金', value: 30 },
    { type: '混合型基金', value: 15 },
    { type: '货币型基金', value: 10 },
  ];

  // 表格列定义
  const tradeColumns = [
    {
      title: '订单号',
      dataIndex: 'orderNo',
      key: 'orderNo',
    },
    {
      title: '产品名称',
      dataIndex: 'product',
      key: 'product',
    },
    {
      title: '金额',
      dataIndex: 'amount',
      key: 'amount',
      render: (amount: number) => `¥${amount.toLocaleString()}`,
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'completed' ? 'green' : 'orange'}>
          {status === 'completed' ? '已完成' : '处理中'}
        </Tag>
      ),
    },
    {
      title: '时间',
      dataIndex: 'time',
      key: 'time',
    },
    {
      title: '操作',
      key: 'action',
      render: () => (
        <Space size="small">
          <Button type="link" size="small" icon={<EyeOutlined />}>
            查看
          </Button>
        </Space>
      ),
    },
  ];

  const strategyColumns = [
    {
      title: '策略名称',
      dataIndex: 'strategy',
      key: 'strategy',
    },
    {
      title: '年化收益率',
      dataIndex: 'return',
      key: 'return',
      render: (value: number) => `${value}%`,
    },
    {
      title: '风险指标',
      dataIndex: 'risk',
      key: 'risk',
      render: (value: number) => `${value}%`,
    },
    {
      title: '夏普比率',
      dataIndex: 'sharpe',
      key: 'sharpe',
    },
    {
      title: '操作',
      key: 'action',
      render: () => (
        <Space size="small">
          <Button type="link" size="small" icon={<EyeOutlined />}>
            详情
          </Button>
          <Button type="link" size="small" icon={<EditOutlined />}>
            编辑
          </Button>
        </Space>
      ),
    },
  ];

  return (
    <div>
      <Title level={2}>智能投顾系统仪表盘</Title>
      <Text type="secondary">欢迎使用NEU智能投顾系统，实时监控投资组合表现</Text>
      
      <Divider />
      
      {/* 统计卡片 */}
      <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
        {statistics.map((stat, index) => (
          <Col xs={24} sm={12} lg={6} key={index}>
            <Card>
              <Statistic
                title={stat.title}
                value={stat.value}
                prefix={stat.prefix}
                valueStyle={{ color: stat.color }}
                suffix={
                  <span style={{ fontSize: 14, marginLeft: 8 }}>
                    {stat.changeType === 'up' ? (
                      <ArrowUpOutlined style={{ color: '#52c41a' }} />
                    ) : (
                      <ArrowDownOutlined style={{ color: '#f5222d' }} />
                    )}
                    {Math.abs(stat.change)}%
                  </span>
                }
              />
            </Card>
          </Col>
        ))}
      </Row>

      {/* 图表区域 */}
      <Row gutter={[16, 16]} style={{ marginBottom: 24 }}>
        <Col xs={24} lg={16}>
          <Card title="收益率趋势" extra={<Button type="link">查看详情</Button>}>
            <Line
              data={returnTrendData}
              xField="date"
              yField="value"
              seriesField="type"
              smooth
              point={{
                size: 4,
                shape: 'circle',
              }}
              color="#1890ff"
              height={300}
            />
          </Card>
        </Col>
        <Col xs={24} lg={8}>
          <Card title="资产配置">
            <Pie
              data={assetAllocationData}
              angleField="value"
              colorField="type"
              radius={0.8}
              label={{
                type: 'outer',
                content: '{name} {percentage}',
              }}
              height={300}
            />
          </Card>
        </Col>
      </Row>

      {/* 表格区域 */}
      <Row gutter={[16, 16]}>
        <Col xs={24} lg={12}>
          <Card title="最近交易" extra={<Button type="link">查看全部</Button>}>
            <Table
              columns={tradeColumns}
              dataSource={recentTrades}
              pagination={false}
              size="small"
            />
          </Card>
        </Col>
        <Col xs={24} lg={12}>
          <Card title="策略表现" extra={<Button type="link">查看全部</Button>}>
            <Table
              columns={strategyColumns}
              dataSource={strategyPerformance}
              pagination={false}
              size="small"
            />
          </Card>
        </Col>
      </Row>

      {/* 系统状态 */}
      <Row gutter={[16, 16]} style={{ marginTop: 24 }}>
        <Col xs={24} lg={8}>
          <Card title="系统状态">
            <Space direction="vertical" style={{ width: '100%' }}>
              <div>
                <Text>数据库连接</Text>
                <Badge status="success" text="正常" style={{ float: 'right' }} />
              </div>
              <div>
                <Text>API服务</Text>
                <Badge status="success" text="正常" style={{ float: 'right' }} />
              </div>
              <div>
                <Text>数据同步</Text>
                <Badge status="processing" text="同步中" style={{ float: 'right' }} />
              </div>
            </Space>
          </Card>
        </Col>
        <Col xs={24} lg={8}>
          <Card title="今日概览">
            <Space direction="vertical" style={{ width: '100%' }}>
              <div>
                <Text>新增用户</Text>
                <Text strong style={{ float: 'right' }}>12</Text>
              </div>
              <div>
                <Text>交易笔数</Text>
                <Text strong style={{ float: 'right' }}>156</Text>
              </div>
              <div>
                <Text>交易金额</Text>
                <Text strong style={{ float: 'right' }}>¥2,450,000</Text>
              </div>
            </Space>
          </Card>
        </Col>
        <Col xs={24} lg={8}>
          <Card title="快速操作">
            <Space direction="vertical" style={{ width: '100%' }}>
              <Button type="primary" block>
                创建新策略
              </Button>
              <Button block>
                导入基金数据
              </Button>
              <Button block>
                生成投资报告
              </Button>
            </Space>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Dashboard; 