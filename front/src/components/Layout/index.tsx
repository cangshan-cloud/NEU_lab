import React, { useState } from 'react';
import { Layout as AntLayout, Menu, theme, Avatar, Dropdown, Space } from 'antd';
import {
  DashboardOutlined,
  FundOutlined,
  ExperimentOutlined,
  SettingOutlined,
  ShoppingOutlined,
  TransactionOutlined,
  UserOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
} from '@ant-design/icons';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import type { MenuProps } from 'antd';

const { Header, Sider, Content } = AntLayout;

const Layout: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  // 菜单项配置
  const menuItems: MenuProps['items'] = [
    {
      key: '/dashboard',
      icon: <DashboardOutlined />,
      label: '仪表盘',
    },
    {
      key: '/fund',
      icon: <FundOutlined />,
      label: '基金研究',
      children: [
        {
          key: '/fund/list',
          label: '基金列表',
        },
        {
          key: '/fund/company',
          label: '基金公司',
        },
        {
          key: '/fund/manager',
          label: '基金经理',
        },
      ],
    },
    {
      key: '/factor',
      icon: <ExperimentOutlined />,
      label: '因子管理',
      children: [
        {
          key: '/factor/list',
          label: '因子列表',
        },
        {
          key: '/factor/tree',
          label: '因子树',
        },
      ],
    },
    {
      key: '/strategy',
      icon: <SettingOutlined />,
      label: '策略管理',
      children: [
        {
          key: '/strategy/list',
          label: '策略列表',
        },
        {
          key: '/strategy/backtest',
          label: '策略回测',
        },
      ],
    },
    {
      key: '/product',
      icon: <ShoppingOutlined />,
      label: '产品管理',
      children: [
        {
          key: '/product/list',
          label: '产品列表',
        },
        {
          key: '/product/review',
          label: '产品审核',
        },
      ],
    },
    {
      key: '/trade',
      icon: <TransactionOutlined />,
      label: '交易管理',
      children: [
        {
          key: '/trade/order',
          label: '交易订单',
        },
        {
          key: '/trade/record',
          label: '交易记录',
        },
        {
          key: '/trade/position',
          label: '用户持仓',
        },
        {
          key: '/trade/flow',
          label: '资金流水',
        },
      ],
    },
  ];

  // 用户下拉菜单
  const userMenuItems: MenuProps['items'] = [
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
      type: 'divider',
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  // 菜单点击处理
  const handleMenuClick: MenuProps['onClick'] = ({ key }) => {
    navigate(key);
  };

  // 用户菜单点击处理
  const handleUserMenuClick: MenuProps['onClick'] = ({ key }) => {
    switch (key) {
      case 'logout':
        // 处理退出登录
        localStorage.removeItem('token');
        navigate('/login');
        break;
      default:
        break;
    }
  };

  // 获取当前选中的菜单项
  const getSelectedKeys = () => {
    const pathname = location.pathname;
    const keys: string[] = [];
    
    // 找到匹配的菜单项
    const findMenuKey = (items: any[], path: string) => {
      for (const item of items) {
        if (item.key === path) {
          keys.push(item.key);
          return true;
        }
        if (item.children) {
          if (findMenuKey(item.children, path)) {
            keys.push(item.key);
            return true;
          }
        }
      }
      return false;
    };

    findMenuKey(menuItems || [], pathname);
    return keys.reverse();
  };

  return (
    <AntLayout style={{ minHeight: '100vh' }}>
      <Sider 
        trigger={null} 
        collapsible 
        collapsed={collapsed}
        width={240}
        style={{
          boxShadow: '2px 0 8px rgba(0,0,0,0.1)',
        }}
      >
        <div style={{ 
          height: 32, 
          margin: 16, 
          background: 'rgba(255, 255, 255, 0.2)',
          borderRadius: 6,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center',
          color: 'white',
          fontSize: collapsed ? 12 : 16,
          fontWeight: 'bold'
        }}>
          {collapsed ? 'NFA' : '智能投顾'}
        </div>
        <Menu
          theme="dark"
          mode="inline"
          selectedKeys={getSelectedKeys()}
          items={menuItems}
          onClick={handleMenuClick}
          style={{
            borderRight: 0,
          }}
        />
      </Sider>
      <AntLayout>
        <Header style={{ 
          padding: '0 24px', 
          background: colorBgContainer,
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
          zIndex: 1000,
        }}>
          <Space>
            {React.createElement(collapsed ? MenuUnfoldOutlined : MenuFoldOutlined, {
              className: 'trigger',
              onClick: () => setCollapsed(!collapsed),
              style: { fontSize: 18, cursor: 'pointer' }
            })}
            <span style={{ fontSize: 18, fontWeight: 'bold', color: '#1890ff' }}>
              智能投顾系统
            </span>
          </Space>
          <Dropdown menu={{ items: userMenuItems, onClick: handleUserMenuClick }}>
            <Space style={{ cursor: 'pointer' }}>
              <Avatar icon={<UserOutlined />} />
              <span>管理员</span>
            </Space>
          </Dropdown>
        </Header>
        <Content
          style={{
            margin: '24px',
            padding: '24px 32px',
            minHeight: 'calc(100vh - 112px)',
            background: colorBgContainer,
            borderRadius: borderRadiusLG,
            boxShadow: '0 2px 8px rgba(0,0,0,0.06)',
            overflow: 'auto',
          }}
        >
          <Outlet />
        </Content>
      </AntLayout>
    </AntLayout>
  );
};

export default Layout; 