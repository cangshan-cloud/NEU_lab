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
import ProductAdd from '../pages/product/ProductAdd';
import FundPortfolioList from '../pages/fund/FundPortfolioList';
import FactorCompositeCreate from '../pages/factor/FactorCompositeCreate';
import StyleFactorCreate from '../pages/factor/StyleFactorCreate';
import FactorTreeCreate from '../pages/factor/FactorTreeCreate';
import FactorTreeDetail from '../pages/factor/FactorTreeDetail';

// 路由配置
export const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <Dashboard />,
      },
      // 基金研究子系统
      {
        path: 'funds',
        children: [
          { index: true, element: <FundList /> },
          { path: ':id', element: <FundDetail /> },
          { path: 'companies', element: <FundCompanyList /> },
          { path: 'managers', element: <FundManagerList /> },
          { path: 'portfolios', element: <FundPortfolioList /> },
        ],
      },
      // 因子管理子系统
      {
        path: 'factors',
        children: [
          { index: true, element: <FactorList /> },
          { path: ':id', element: <FactorDetail /> },
          { path: 'trees', element: <FactorTreeList /> },
          { path: 'composite-create', element: <FactorCompositeCreate /> },
          { path: 'style-create', element: <StyleFactorCreate /> },
        ],
      },
      // 策略管理子系统
      {
        path: 'strategies',
        children: [
          { index: true, element: <StrategyList /> },
          { path: ':id', element: <StrategyDetail /> },
          { path: 'backtests', element: <StrategyBacktestList /> },
        ],
      },
      // 组合产品管理子系统
      {
        path: 'products',
        children: [
          { index: true, element: <ProductList /> },
          { path: ':id', element: <ProductDetail /> },
          { path: 'reviews', element: <ProductReviewList /> },
        ],
      },
      // 交易管理子系统
      {
        path: 'trades',
        children: [
          { path: 'orders', element: <TradeOrderList /> },
          { path: 'capital-flows', element: <CapitalFlowList /> },
          { path: 'records', element: <TradeRecordList /> },
          { path: 'positions', element: <UserPositionList /> },
        ],
      },
      {
        path: '/product/add',
        element: <ProductAdd />
      },
      {
        path: '/factor/tree/create',
        element: <FactorTreeCreate />
      },
      {
        path: '/factor/tree/:id',
        element: <FactorTreeDetail />
      },
    ],
  },
]);

export default router; 