import React from 'react';
import { createBrowserRouter, Navigate } from 'react-router-dom';
import Layout from '../components/Layout';
import Dashboard from '../pages/Dashboard';
import FundList from '../pages/fund/FundList';
import FundDetail from '../pages/fund/FundDetail';
import FundCompanyList from '../pages/fund/FundCompanyList';
import FundManagerList from '../pages/fund/FundManagerList';
import FactorList from '../pages/factor/FactorList';
import FactorDetail from '../pages/factor/FactorDetail';
import FactorTreeList from '../pages/factor/FactorTreeList';
import StrategyList from '../pages/strategy/StrategyList';
import StrategyDetail from '../pages/strategy/StrategyDetail';
import StrategyBacktestList from '../pages/strategy/StrategyBacktestList';
import ProductList from '../pages/product/ProductList';
import ProductDetail from '../pages/product/ProductDetail';
import ProductReviewList from '../pages/product/ProductReviewList';
import TradeOrderList from '../pages/trade/TradeOrderList';
import TradeRecordList from '../pages/trade/TradeRecordList';
import UserPositionList from '../pages/trade/UserPositionList';
import CapitalFlowList from '../pages/trade/CapitalFlowList';

// 路由配置
export const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <Navigate to="/dashboard" replace />,
      },
      {
        path: 'dashboard',
        element: <Dashboard />,
      },
      // 基金研究子系统
      {
        path: 'fund',
        children: [
          {
            path: 'list',
            element: <FundList />,
          },
          {
            path: 'detail/:id',
            element: <FundDetail />,
          },
          {
            path: 'company',
            element: <FundCompanyList />,
          },
          {
            path: 'manager',
            element: <FundManagerList />,
          },
        ],
      },
      // 因子管理子系统
      {
        path: 'factor',
        children: [
          {
            path: 'list',
            element: <FactorList />,
          },
          {
            path: 'detail/:id',
            element: <FactorDetail />,
          },
          {
            path: 'tree',
            element: <FactorTreeList />,
          },
        ],
      },
      // 策略管理子系统
      {
        path: 'strategy',
        children: [
          {
            path: 'list',
            element: <StrategyList />,
          },
          {
            path: 'detail/:id',
            element: <StrategyDetail />,
          },
          {
            path: 'backtest',
            element: <StrategyBacktestList />,
          },
        ],
      },
      // 组合产品管理子系统
      {
        path: 'product',
        children: [
          {
            path: 'list',
            element: <ProductList />,
          },
          {
            path: 'detail/:id',
            element: <ProductDetail />,
          },
          {
            path: 'review',
            element: <ProductReviewList />,
          },
        ],
      },
      // 交易管理子系统
      {
        path: 'trade',
        children: [
          {
            path: 'order',
            element: <TradeOrderList />,
          },
          {
            path: 'record',
            element: <TradeRecordList />,
          },
          {
            path: 'position',
            element: <UserPositionList />,
          },
          {
            path: 'flow',
            element: <CapitalFlowList />,
          },
        ],
      },
    ],
  },
]);

export default router; 