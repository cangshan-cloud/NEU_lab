import React from 'react';
import { BrowserRouter, Routes, Route, Navigate, Outlet } from 'react-router-dom';
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
import UserLogin from '../pages/UserLogin';
import UserRegister from '../pages/UserRegister';
import UserProfile from '../pages/UserProfile';
import UserList from '../pages/UserList';
import AccountRebalance from '../pages/trade/AccountRebalance';
import TradeErrorList from '../pages/trade/TradeErrorList';
import DeliveryOrderList from '../pages/trade/DeliveryOrderList';

const RequireAuth: React.FC = () => {
  const token = localStorage.getItem('token');
  return token ? <Outlet /> : <Navigate to="/login" replace />;
};

const Router = () => (
  <Routes>
    <Route path="/login" element={<UserLogin />} />
    <Route path="/register" element={<UserRegister />} />
    <Route element={<RequireAuth />}>
      <Route element={<Layout />}>
        <Route path="/" element={<Dashboard />} />
        <Route path="profile" element={<UserProfile />} />
        <Route path="users" element={<UserList />} />
        <Route path="funds" element={<FundList />} />
        <Route path="funds/:id" element={<FundDetail />} />
        <Route path="funds/companies" element={<FundCompanyList />} />
        <Route path="funds/managers" element={<FundManagerList />} />
        <Route path="funds/portfolios" element={<FundPortfolioList />} />
        <Route path="factors" element={<FactorList />} />
        <Route path="factors/:id" element={<FactorDetail />} />
        <Route path="factors/trees" element={<FactorTreeList />} />
        <Route path="factors/composite-create" element={<FactorCompositeCreate />} />
        <Route path="factors/style-create" element={<StyleFactorCreate />} />
        <Route path="strategies" element={<StrategyList />} />
        <Route path="strategies/:id" element={<StrategyDetail />} />
        <Route path="strategies/backtests" element={<StrategyBacktestList />} />
        <Route path="products" element={<ProductList />} />
        <Route path="products/:id" element={<ProductDetail />} />
        <Route path="products/reviews" element={<ProductReviewList />} />
        <Route path="trades" element={<TradeOrderList />} />
        <Route path="trades/capital-flows" element={<CapitalFlowList />} />
        <Route path="trades/records" element={<TradeRecordList />} />
        <Route path="trades/positions" element={<UserPositionList />} />
        <Route path="trades/account-rebalance" element={<AccountRebalance />} />
        <Route path="trades/errors" element={<TradeErrorList />} />
        <Route path="trades/delivery-orders" element={<DeliveryOrderList />} />
        <Route path="product/add" element={<ProductAdd />} />
        <Route path="factor/tree/create" element={<FactorTreeCreate />} />
        <Route path="factor/tree/:id" element={<FactorTreeDetail />} />
      </Route>
    </Route>
    <Route path="*" element={<Navigate to="/login" replace />} />
  </Routes>
);

export default Router; 