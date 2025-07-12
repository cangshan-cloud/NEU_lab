import React, { useEffect, useState } from 'react';
import { Card, Row, Col, Statistic, Alert, Typography, Spin, Tag } from 'antd';
import { getRealtimeAnalytics } from '../../api/analytics';
const { Title } = Typography;

// 仪表盘模拟数据
const getDashboardMockData = () => ({
  totalUsers: 50,             // 总用户数
  activeUsers: 18,            // 活跃用户数（<=总用户数）
  newUsers: 5,                // 新增用户数（<=总用户数）
  churnRate: 4.0,             // 流失率（%）
  alerts: [
    { type: 'warning', message: '今日有2笔大额异常交易待审核', time: '10:30' },
    { type: 'info', message: '本周活跃用户环比增长12%', time: '09:15' },
    { type: 'success', message: '系统运行正常，所有服务健康', time: '08:00' }
  ],
  healthStatus: {
    system: 'healthy',
    database: 'healthy', 
    cache: 'warning',
    api: 'healthy'
  },
  recentActivities: [
    { type: 'user', message: '新用户注册: user_123', time: '2分钟前' },
    { type: 'trade', message: '大额交易: 50000元', time: '5分钟前' },
    { type: 'alert', message: '风控预警触发', time: '8分钟前' }
  ]
});

const Dashboard: React.FC = () => {
  const [realtime, setRealtime] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [mockData] = useState(getDashboardMockData());

  useEffect(() => {
    getRealtimeAnalytics().then(res => {
      setRealtime(res.data.data);
    }).finally(() => setLoading(false));
  }, []);

  // 强制只用mock数据，避免后端初始数据影响页面展示
  const totalUsers = mockData.totalUsers;
  const activeUsers = mockData.activeUsers;
  const newUsers = mockData.newUsers;
  const churnRate = mockData.churnRate;
  const alerts = mockData.alerts;
  const healthStatus = mockData.healthStatus;
  const recentActivities = mockData.recentActivities;

  const getHealthColor = (status: string) => {
    switch (status) {
      case 'healthy': return '#52c41a';
      case 'warning': return '#faad14';
      case 'error': return '#ff4d4f';
      default: return '#d9d9d9';
    }
  };

  const getHealthText = (status: string) => {
    switch (status) {
      case 'healthy': return '正常';
      case 'warning': return '警告';
      case 'error': return '异常';
      default: return '未知';
    }
  };

  return (
    <div style={{ padding: 24, background: '#f7f8fa', minHeight: '100vh' }}>
      <Title level={3} style={{ marginBottom: 24 }}>仪表盘总览</Title>
      {loading ? <Spin size="large" style={{ margin: '80px auto', display: 'block' }} /> : <>
        {/* 核心指标卡片 */}
        <Row gutter={16} style={{ marginBottom: 24 }}>
          <Col span={6}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }}>
              <Statistic 
                title="总用户数" 
                value={totalUsers} 
                valueStyle={{ fontSize: 28, fontWeight: 600, color: '#1890ff' }} 
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }}>
              <Statistic 
                title="活跃用户" 
                value={activeUsers} 
                valueStyle={{ fontSize: 28, fontWeight: 600, color: '#52c41a' }} 
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }}>
              <Statistic 
                title="新增用户" 
                value={newUsers} 
                valueStyle={{ fontSize: 28, fontWeight: 600, color: '#722ed1' }} 
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }}>
              <Statistic 
                title="流失率" 
                value={churnRate} 
                suffix="%" 
                precision={2} 
                valueStyle={{ fontSize: 28, fontWeight: 600, color: '#faad14' }} 
              />
            </Card>
          </Col>
        </Row>

        <Row gutter={16}>
          {/* 系统告警/运营提示 */}
          <Col span={12}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }} title="系统告警与运营提示">
              {alerts?.map((alert: any, idx: number) => (
                <Alert 
                  key={idx} 
                  message={
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                      <span>{alert.message}</span>
                      <span style={{ fontSize: 12, color: '#999' }}>{alert.time}</span>
                    </div>
                  } 
                  type={alert.type} 
                  showIcon 
                  style={{ marginBottom: 12, borderRadius: 8 }}
                />
              ))}
            </Card>
          </Col>

          {/* 系统健康度 */}
          <Col span={12}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }} title="系统健康度">
              <Row gutter={16}>
                <Col span={12}>
                  <div style={{ textAlign: 'center', padding: '16px 0' }}>
                    <div style={{ fontSize: 14, color: '#666', marginBottom: 8 }}>系统服务</div>
                    <Tag color={getHealthColor(healthStatus.system)} style={{ fontSize: 14, padding: '4px 12px' }}>
                      {getHealthText(healthStatus.system)}
                    </Tag>
                  </div>
                </Col>
                <Col span={12}>
                  <div style={{ textAlign: 'center', padding: '16px 0' }}>
                    <div style={{ fontSize: 14, color: '#666', marginBottom: 8 }}>数据库</div>
                    <Tag color={getHealthColor(healthStatus.database)} style={{ fontSize: 14, padding: '4px 12px' }}>
                      {getHealthText(healthStatus.database)}
                    </Tag>
                  </div>
                </Col>
                <Col span={12}>
                  <div style={{ textAlign: 'center', padding: '16px 0' }}>
                    <div style={{ fontSize: 14, color: '#666', marginBottom: 8 }}>缓存服务</div>
                    <Tag color={getHealthColor(healthStatus.cache)} style={{ fontSize: 14, padding: '4px 12px' }}>
                      {getHealthText(healthStatus.cache)}
                    </Tag>
                  </div>
                </Col>
                <Col span={12}>
                  <div style={{ textAlign: 'center', padding: '16px 0' }}>
                    <div style={{ fontSize: 14, color: '#666', marginBottom: 8 }}>API服务</div>
                    <Tag color={getHealthColor(healthStatus.api)} style={{ fontSize: 14, padding: '4px 12px' }}>
                      {getHealthText(healthStatus.api)}
                    </Tag>
                  </div>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>

        {/* 最近动态 */}
        <Row style={{ marginTop: 16 }}>
          <Col span={24}>
            <Card style={{ borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }} title="最近动态">
              {recentActivities?.map((activity: any, idx: number) => (
                <div key={idx} style={{ 
                  display: 'flex', 
                  justifyContent: 'space-between', 
                  alignItems: 'center', 
                  padding: '8px 0',
                  borderBottom: idx < recentActivities.length - 1 ? '1px solid #f0f0f0' : 'none'
                }}>
                  <div style={{ display: 'flex', alignItems: 'center' }}>
                    <Tag color={
                      activity.type === 'user' ? 'blue' : 
                      activity.type === 'trade' ? 'green' : 
                      activity.type === 'alert' ? 'orange' : 'default'
                    } style={{ marginRight: 8 }}>
                      {activity.type === 'user' ? '用户' : 
                       activity.type === 'trade' ? '交易' : 
                       activity.type === 'alert' ? '告警' : '其他'}
                    </Tag>
                    <span>{activity.message}</span>
                  </div>
                  <span style={{ fontSize: 12, color: '#999' }}>{activity.time}</span>
                </div>
              ))}
            </Card>
          </Col>
        </Row>
      </>}
    </div>
  );
};

export default Dashboard; 