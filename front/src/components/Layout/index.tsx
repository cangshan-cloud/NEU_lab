import React, { useState } from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import {
  Layout as AntLayout,
  Menu,
  Button,
  Avatar,
  Dropdown,
  Space,
  Typography,
  theme,
} from 'antd';
import {
  DashboardOutlined,
  FundOutlined,
  ExperimentOutlined,
  SettingOutlined,
  ShoppingOutlined,
  TransactionOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
  LogoutOutlined,
  BellOutlined,
} from '@ant-design/icons';
import './index.css';

const { Header, Sider, Content } = AntLayout;
const { Title } = Typography;

const Layout: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const location = useLocation();
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  // 菜单配置
  const menuItems = [
    {
      key: '/',
      icon: <DashboardOutlined />,
      label: '仪表盘',
    },
    {
      key: 'funds',
      icon: <FundOutlined />,
      label: '基金研究',
      children: [
        {
          key: '/funds',
          label: '基金列表',
        },
        {
          key: '/funds/companies',
          label: '基金公司',
        },
        {
          key: '/funds/managers',
          label: '基金经理',
        },
        {
          key: '/funds/portfolios',
          label: '基金组合管理',
        },
      ],
    },
    {
      key: 'factors',
      icon: <ExperimentOutlined />,
      label: '因子管理',
      children: [
        {
          key: '/factors',
          label: '因子列表',
        },
        {
          key: '/factors/trees',
          label: '因子树',
        },
        {
          key: '/factors/composite-create',
          label: '创建衍生因子',
        },
        {
          key: '/factors/style-create',
          label: '创建风格因子',
        },
      ],
    },
    {
      key: 'strategies',
      icon: <SettingOutlined />,
      label: '策略管理',
      children: [
        {
          key: '/strategies',
          label: '策略列表',
        },
        {
          key: '/strategies/backtests',
          label: '策略回测',
        },
      ],
    },
    {
      key: 'products',
      icon: <ShoppingOutlined />,
      label: '组合产品',
      children: [
        {
          key: '/products',
          label: '产品列表',
        },
        {
          key: '/products/reviews',
          label: '产品审核',
        },
      ],
    },
    {
      key: 'trades',
      icon: <TransactionOutlined />,
      label: '交易管理',
      children: [
        {
          key: '/trades/orders',
          label: '交易单',
        },
        {
          key: '/trades/capital-flows',
          label: '资金流水',
        },
        {
          key: '/trades/records',
          label: '交易记录',
        },
        {
          key: '/trades/positions',
          label: '用户持仓',
        },
      ],
    },
  ];

  // 用户菜单
  const userMenuItems = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: '个人中心',
    },
    {
      key: 'settings',
      icon: <SettingOutlined />,
      label: '系统设置',
    },
    {
      type: 'divider' as const,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  // 获取当前选中的菜单项
  const getSelectedKeys = () => {
    const pathname = location.pathname;
    const keys: string[] = [];
    
    // 根据路径匹配菜单项
    if (pathname === '/') {
      keys.push('/');
    } else if (pathname.startsWith('/funds')) {
      keys.push('funds');
      keys.push(pathname);
    } else if (pathname.startsWith('/factors')) {
      keys.push('factors');
      keys.push(pathname);
    } else if (pathname.startsWith('/strategies')) {
      keys.push('strategies');
      keys.push(pathname);
    } else if (pathname.startsWith('/products')) {
      keys.push('products');
      keys.push(pathname);
    } else if (pathname.startsWith('/trades')) {
      keys.push('trades');
      keys.push(pathname);
    }
    
    return keys;
  };

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Sider trigger={null} collapsible collapsed={collapsed} theme="dark">
        <div className="logo">
          <Title level={4} style={{ color: 'white', margin: 0, textAlign: 'center' }}>
            {collapsed ? 'NEU' : 'NEU智能投顾'}
          </Title>
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={getSelectedKeys()}
          items={menuItems}
          onClick={({ key }) => {
            if (key !== 'funds' && key !== 'factors' && key !== 'strategies' && key !== 'products' && key !== 'trades') {
              window.location.href = key;
            }
          }}
        />
      </Sider>
      <AntLayout>
        <Header style={{ padding: 0, background: colorBgContainer }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', height: '100%', padding: '0 24px' }}>
            <Button
              type="text"
              icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
              onClick={() => setCollapsed(!collapsed)}
              style={{
                fontSize: '16px',
                width: 64,
                height: 64,
              }}
            />
            <Space size="large">
              <Button type="text" icon={<BellOutlined />} />
              <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
                <Space style={{ cursor: 'pointer' }}>
                  <Avatar icon={<UserOutlined />} />
                  <span>管理员</span>
                </Space>
              </Dropdown>
            </Space>
          </div>
        </Header>
        <Content
          style={{
            margin: '24px 16px',
            padding: 24,
            minHeight: 280,
            background: colorBgContainer,
            borderRadius: borderRadiusLG,
          }}
        >
          <Outlet />
        </Content>
      </AntLayout>
    </AntLayout>
  );
};

export default Layout; 