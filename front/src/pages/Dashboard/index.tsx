import React, { useEffect, useState } from 'react';
import { Row, Col, Card, Statistic, Table, Progress, Typography } from 'antd';
import { 
  FundOutlined, 
  ExperimentOutlined, 
  SettingOutlined, 
  ShoppingOutlined,
  TransactionOutlined,
  ArrowUpOutlined,
  ArrowDownOutlined
} from '@ant-design/icons';
import * as echarts from 'echarts';

const { Title } = Typography;

interface DashboardStats {
  totalFunds: number;
  totalFactors: number;
  totalStrategies: number;
  totalProducts: number;
  totalOrders: number;
  totalAssets: number;
  dailyReturn: number;
  monthlyReturn: number;
}

const Dashboard: React.FC = () => {
  const [stats, setStats] = useState<DashboardStats>({
    totalFunds: 0,
    totalFactors: 0,
    totalStrategies: 0,
    totalProducts: 0,
    totalOrders: 0,
    totalAssets: 0,
    dailyReturn: 0,
    monthlyReturn: 0,
  });

  useEffect(() => {
    // 模拟数据加载
    setStats({
      totalFunds: 1250,
      totalFactors: 156,
      totalStrategies: 89,
      totalProducts: 45,
      totalOrders: 2340,
      totalAssets: 125000000,
      dailyReturn: 0.0234,
      monthlyReturn: 0.156,
    });

    // 初始化图表
    initCharts();
  }, []);

  const initCharts = () => {
    // 收益趋势图
    const returnChart = echarts.init(document.getElementById('returnChart'));
    const returnOption = {
      title: {
        text: '收益趋势',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        data: ['日收益率', '累计收益率'],
        top: 30
      },
      xAxis: {
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月']
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '{value}%'
        }
      },
      series: [
        {
          name: '日收益率',
          type: 'line',
          data: [2.1, 1.8, 2.3, 1.9, 2.5, 2.2],
          smooth: true
        },
        {
          name: '累计收益率',
          type: 'line',
          data: [2.1, 3.9, 6.2, 8.1, 10.6, 12.8],
          smooth: true
        }
      ]
    };
    returnChart.setOption(returnOption);

    // 资产配置饼图
    const assetChart = echarts.init(document.getElementById('assetChart'));
    const assetOption = {
      title: {
        text: '资产配置',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        top: 'middle'
      },
      series: [
        {
          name: '资产配置',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 35, name: '股票型基金' },
            { value: 25, name: '债券型基金' },
            { value: 20, name: '混合型基金' },
            { value: 15, name: '货币型基金' },
            { value: 5, name: '其他' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    assetChart.setOption(assetOption);

    // 清理函数
    return () => {
      returnChart.dispose();
      assetChart.dispose();
    };
  };

  const recentOrdersColumns = [
    {
      title: '订单编号',
      dataIndex: 'orderNo',
      key: 'orderNo',
    },
    {
      title: '产品名称',
      dataIndex: 'productName',
      key: 'productName',
    },
    {
      title: '交易类型',
      dataIndex: 'tradeType',
      key: 'tradeType',
      render: (type: string) => (
        <span style={{ color: type === 'BUY' ? '#52c41a' : '#ff4d4f' }}>
          {type === 'BUY' ? '买入' : '卖出'}
        </span>
      ),
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
      render: (status: string) => {
        const statusMap = {
          PENDING: { text: '待处理', color: '#faad14' },
          PROCESSING: { text: '处理中', color: '#1890ff' },
          SUCCESS: { text: '成功', color: '#52c41a' },
          FAILED: { text: '失败', color: '#ff4d4f' },
        };
        const config = statusMap[status as keyof typeof statusMap];
        return <span style={{ color: config.color }}>{config.text}</span>;
      },
    },
  ];

  const recentOrdersData = [
    {
      key: '1',
      orderNo: 'ORD202401150001',
      productName: '价值投资产品A',
      tradeType: 'BUY',
      amount: 100000,
      status: 'SUCCESS',
    },
    {
      key: '2',
      orderNo: 'ORD202401150002',
      productName: '成长投资产品B',
      tradeType: 'BUY',
      amount: 150000,
      status: 'SUCCESS',
    },
    {
      key: '3',
      orderNo: 'ORD202401150003',
      productName: 'FOF稳健产品C',
      tradeType: 'BUY',
      amount: 50000,
      status: 'PROCESSING',
    },
  ];

  return (
    <div>
      <Title level={2}>系统概览</Title>
      
      {/* 统计卡片 */}
      <Row gutter={[24, 24]} style={{ marginBottom: 32 }}>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="基金总数"
              value={stats.totalFunds}
              prefix={<FundOutlined />}
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="因子总数"
              value={stats.totalFactors}
              prefix={<ExperimentOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="策略总数"
              value={stats.totalStrategies}
              prefix={<SettingOutlined />}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="产品总数"
              value={stats.totalProducts}
              prefix={<ShoppingOutlined />}
              valueStyle={{ color: '#fa8c16' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="总资产"
              value={stats.totalAssets}
              precision={0}
              prefix="¥"
              suffix="元"
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={4}>
          <Card hoverable>
            <Statistic
              title="交易订单"
              value={stats.totalOrders}
              prefix={<TransactionOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
      </Row>

      {/* 收益统计 */}
      <Row gutter={[24, 24]} style={{ marginBottom: 32 }}>
        <Col xs={24} sm={12} md={8} lg={6} xl={6}>
          <Card hoverable>
            <Statistic
              title="日收益率"
              value={stats.dailyReturn * 100}
              precision={2}
              prefix={stats.dailyReturn > 0 ? <ArrowUpOutlined /> : <ArrowDownOutlined />}
              suffix="%"
              valueStyle={{ color: stats.dailyReturn > 0 ? '#3f8600' : '#cf1322' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={6}>
          <Card hoverable>
            <Statistic
              title="月收益率"
              value={stats.monthlyReturn * 100}
              precision={2}
              prefix={stats.monthlyReturn > 0 ? <ArrowUpOutlined /> : <ArrowDownOutlined />}
              suffix="%"
              valueStyle={{ color: stats.monthlyReturn > 0 ? '#3f8600' : '#cf1322' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={6}>
          <Card hoverable>
            <Statistic
              title="年化收益率"
              value={12.5}
              precision={2}
              prefix={<ArrowUpOutlined />}
              suffix="%"
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} md={8} lg={6} xl={6}>
          <Card hoverable>
            <Statistic
              title="最大回撤"
              value={-8.2}
              precision={1}
              prefix={<ArrowDownOutlined />}
              suffix="%"
              valueStyle={{ color: '#cf1322' }}
            />
          </Card>
        </Col>
      </Row>

      {/* 图表区域 */}
      <Row gutter={[24, 24]} style={{ marginBottom: 32 }}>
        <Col xs={24} lg={16}>
          <Card title="收益趋势" hoverable>
            <div id="returnChart" style={{ height: 350 }} />
          </Card>
        </Col>
        <Col xs={24} lg={8}>
          <Card title="资产配置" hoverable>
            <div id="assetChart" style={{ height: 350 }} />
          </Card>
        </Col>
      </Row>

      {/* 最近交易订单 */}
      <Card title="最近交易订单" hoverable>
        <Table
          columns={recentOrdersColumns}
          dataSource={recentOrdersData}
          pagination={false}
          size="middle"
          scroll={{ x: 800 }}
        />
      </Card>
    </div>
  );
};

export default Dashboard; 