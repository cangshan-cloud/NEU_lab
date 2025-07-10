import request from '../utils/request';
import type { ApiResponse, PageResult } from '../types';

// 策略VO/DTO类型
export interface StrategyVO {
  id: number;
  strategyCode: string;
  strategyName: string;
  strategyType: string;
  riskLevel: string;
  targetReturn: number;
  maxDrawdown: number;
  investmentHorizon: string;
  description: string;
  factorTreeId: number;
  parameters: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}
export interface StrategyDTO {
  strategyCode: string;
  strategyName: string;
  strategyType: string;
  riskLevel: string;
  targetReturn: number;
  maxDrawdown: number;
  investmentHorizon: string;
  description: string;
  factorTreeId: number;
  parameters: string;
  status: string;
}
export interface StrategyBacktestDTO {
  strategyId: number;
  backtestName: string;
  backtestType: string;
  startDate: string;
  endDate: string;
  initialCapital: number;
  parameters: string;
}
export interface StrategyBacktestVO {
  id: number;
  strategyId: number;
  backtestName: string;
  backtestType: string;
  startDate: string;
  endDate: string;
  initialCapital: number;
  finalValue: number;
  totalReturn: number;
  annualReturn: number;
  maxDrawdown: number;
  sharpeRatio: number;
  volatility: number;
  winRate: number;
  backtestResult: string;
  status: string;
  createdAt: string;
  updatedAt: string;
  results: string;
}

// 分页查询策略列表
export const fetchStrategyList = (params: {
  type?: string; // 修改为type以匹配后端
  riskLevel?: string;
  status?: string;
  keyword?: string;
  page?: number;
  size?: number;
}) => request.get<ApiResponse<PageResult<StrategyVO>>>(`/strategies`, { params });

// 查询策略详情
export function fetchStrategyDetail(id: number) {
  return request.get<ApiResponse<StrategyVO>>(`/strategies/${id}`);
}

// 新建策略
export function createStrategy(data: StrategyDTO) {
  return request.post<ApiResponse<StrategyVO>>('/strategies', data);
}

// 编辑策略
export function updateStrategy(id: number, data: StrategyDTO) {
  return request.put<ApiResponse<StrategyVO>>(`/strategies/${id}`, data);
}

// 删除策略
export function deleteStrategy(id: number) {
  return request.delete<ApiResponse<void>>(`/strategies/${id}`);
}

// 策略回测
export function backtestStrategy(id: number, data: StrategyBacktestDTO) {
  return request.post<ApiResponse<StrategyBacktestVO>>(`/strategies/${id}/backtest`, data);
}

// 预留：策略模拟、再平衡、监控
export function simulateStrategy(id: number) {
  return request.post<ApiResponse<void>>(`/strategies/${id}/simulate`);
}
export function rebalanceStrategy(id: number) {
  return request.post<ApiResponse<void>>(`/strategies/${id}/rebalance`);
}
export function monitorStrategy(id: number) {
  return request.get<ApiResponse<void>>(`/strategies/${id}/monitor`);
} 