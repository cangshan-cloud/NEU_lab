import React from 'react';
import { ConfigProvider } from 'antd';
import zhCN from 'antd/locale/zh_CN';
import Router from './router';
import './App.css';
import { BrowserRouter, useLocation } from 'react-router-dom';

const AppContent: React.FC = () => {
  const location = useLocation();
  const isAuthPage = location.pathname === '/login' || location.pathname === '/register';
  return (
    <div className={isAuthPage ? 'auth-root' : 'main-root'}>
      <Router />
    </div>
  );
};

const App: React.FC = () => {
  return (
    <ConfigProvider locale={zhCN}>
      <BrowserRouter>
        <AppContent />
      </BrowserRouter>
    </ConfigProvider>
  );
};

export default App;
