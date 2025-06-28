import { get, post, put, del } from '../utils/request';
import type { 
  Strategy, 
  StrategyBacktest, 
  StrategyPortfolio,
  PageResult,
  QueryParams 
} from '../types';

// 策略API
export const strategyApi = {
  // 获取策略列表
  getList: (params?: QueryParams) => 
    get<PageResult<Strategy>>('/strategies', params),
  
  // 获取策略详情
  getById: (id: number) => 
    get<Strategy>(`/strategies/${id}`),
  
  // 创建策略
  create: (data: Partial<Strategy>) => 
    post<Strategy>('/strategies', data),
  
  // 更新策略
  update: (id: number, data: Partial<Strategy>) => 
    put<Strategy>(`/strategies/${id}`, data),
  
  // 删除策略
  delete: (id: number) => 
    del(`/strategies/${id}`),
  
  // 根据类型获取策略
  getByType: (type: string) => 
    get<Strategy[]>(`/strategies/type/${type}`),
  
  // 根据风险等级获取策略
  getByRiskLevel: (riskLevel: string) => 
    get<Strategy[]>(`/strategies/risk/${riskLevel}`),
  
  // 获取所有策略（不分页）
  getAll: () => 
    get<Strategy[]>('/strategies/all'),
};

// 策略回测API
export const strategyBacktestApi = {
  // 获取策略回测列表
  getList: (params?: QueryParams) => 
    get<PageResult<StrategyBacktest>>('/strategy-backtests', params),
  
  // 获取策略回测详情
  getById: (id: number) => 
    get<StrategyBacktest>(`/strategy-backtests/${id}`),
  
  // 创建策略回测
  create: (data: Partial<StrategyBacktest>) => 
    post<StrategyBacktest>('/strategy-backtests', data),
  
  // 更新策略回测
  update: (id: number, data: Partial<StrategyBacktest>) => 
    put<StrategyBacktest>(`/strategy-backtests/${id}`, data),
  
  // 删除策略回测
  delete: (id: number) => 
    del(`/strategy-backtests/${id}`),
  
  // 根据策略ID获取回测
  getByStrategyId: (strategyId: number) => 
    get<StrategyBacktest[]>(`/strategy-backtests/strategy/${strategyId}`),
  
  // 获取所有策略回测（不分页）
  getAll: () => 
    get<StrategyBacktest[]>('/strategy-backtests/all'),
};

// 策略组合关联API
export const strategyPortfolioApi = {
  // 获取策略组合关联列表
  getList: (params?: QueryParams) => 
    get<PageResult<StrategyPortfolio>>('/strategy-portfolios', params),
  
  // 获取策略组合关联详情
  getById: (id: number) => 
    get<StrategyPortfolio>(`/strategy-portfolios/${id}`),
  
  // 创建策略组合关联
  create: (data: Partial<StrategyPortfolio>) => 
    post<StrategyPortfolio>('/strategy-portfolios', data),
  
  // 更新策略组合关联
  update: (id: number, data: Partial<StrategyPortfolio>) => 
    put<StrategyPortfolio>(`/strategy-portfolios/${id}`, data),
  
  // 删除策略组合关联
  delete: (id: number) => 
    del(`/strategy-portfolios/${id}`),
  
  // 根据策略ID获取组合关联
  getByStrategyId: (strategyId: number) => 
    get<StrategyPortfolio[]>(`/strategy-portfolios/strategy/${strategyId}`),
  
  // 根据组合ID获取策略关联
  getByPortfolioId: (portfolioId: number) => 
    get<StrategyPortfolio[]>(`/strategy-portfolios/portfolio/${portfolioId}`),
};

// 兼容性导出函数
export const getStrategyList = (params?: QueryParams) => 
  strategyApi.getList(params);

export const getStrategyById = (id: number) => 
  strategyApi.getById(id);

export const deleteStrategy = (id: number) => 
  strategyApi.delete(id);

export const getStrategyBacktestList = (params?: QueryParams) => 
  strategyBacktestApi.getList(params);

export const deleteStrategyBacktest = (id: number) => 
  strategyBacktestApi.delete(id); 