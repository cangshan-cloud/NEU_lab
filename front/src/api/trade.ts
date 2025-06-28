import { get, post, put, del } from '../utils/request';
import type { 
  TradeOrder, 
  TradeRecord, 
  UserPosition, 
  CapitalFlow,
  PageResult,
  QueryParams 
} from '../types';

// 交易订单API
export const tradeOrderApi = {
  // 获取交易订单列表
  getList: (params?: QueryParams) => 
    get<PageResult<TradeOrder>>('/trade-orders', params),
  
  // 获取交易订单详情
  getById: (id: number) => 
    get<TradeOrder>(`/trade-orders/${id}`),
  
  // 创建交易订单
  create: (data: Partial<TradeOrder>) => 
    post<TradeOrder>('/trade-orders', data),
  
  // 更新交易订单
  update: (id: number, data: Partial<TradeOrder>) => 
    put<TradeOrder>(`/trade-orders/${id}`, data),
  
  // 删除交易订单
  delete: (id: number) => 
    del(`/trade-orders/${id}`),
  
  // 根据用户ID获取订单
  getByUserId: (userId: number, params?: QueryParams) => 
    get<PageResult<TradeOrder>>(`/trade-orders/user/${userId}`, params),
  
  // 根据产品ID获取订单
  getByProductId: (productId: number, params?: QueryParams) => 
    get<PageResult<TradeOrder>>(`/trade-orders/product/${productId}`, params),
  
  // 根据状态获取订单
  getByStatus: (status: string, params?: QueryParams) => 
    get<PageResult<TradeOrder>>(`/trade-orders/status/${status}`, params),
  
  // 根据交易类型获取订单
  getByTradeType: (tradeType: string, params?: QueryParams) => 
    get<PageResult<TradeOrder>>(`/trade-orders/type/${tradeType}`, params),
  
  // 取消订单
  cancel: (id: number) => 
    put<TradeOrder>(`/trade-orders/${id}/cancel`),
  
  // 确认订单
  confirm: (id: number) => 
    put<TradeOrder>(`/trade-orders/${id}/confirm`),
};

// 交易记录API
export const tradeRecordApi = {
  // 获取交易记录列表
  getList: (params?: QueryParams) => 
    get<PageResult<TradeRecord>>('/trade-records', params),
  
  // 获取交易记录详情
  getById: (id: number) => 
    get<TradeRecord>(`/trade-records/${id}`),
  
  // 创建交易记录
  create: (data: Partial<TradeRecord>) => 
    post<TradeRecord>('/trade-records', data),
  
  // 更新交易记录
  update: (id: number, data: Partial<TradeRecord>) => 
    put<TradeRecord>(`/trade-records/${id}`, data),
  
  // 删除交易记录
  delete: (id: number) => 
    del(`/trade-records/${id}`),
  
  // 根据用户ID获取记录
  getByUserId: (userId: number, params?: QueryParams) => 
    get<PageResult<TradeRecord>>(`/trade-records/user/${userId}`, params),
  
  // 根据订单ID获取记录
  getByOrderId: (orderId: number) => 
    get<TradeRecord[]>(`/trade-records/order/${orderId}`),
  
  // 根据产品ID获取记录
  getByProductId: (productId: number, params?: QueryParams) => 
    get<PageResult<TradeRecord>>(`/trade-records/product/${productId}`, params),
  
  // 根据交易类型获取记录
  getByTradeType: (tradeType: string, params?: QueryParams) => 
    get<PageResult<TradeRecord>>(`/trade-records/type/${tradeType}`, params),
  
  // 根据时间范围获取记录
  getByTimeRange: (startTime: string, endTime: string, params?: QueryParams) => 
    get<PageResult<TradeRecord>>(`/trade-records/time-range`, { startTime, endTime, ...params }),
};

// 用户持仓API
export const userPositionApi = {
  // 获取用户持仓列表
  getList: (params?: QueryParams) => 
    get<PageResult<UserPosition>>('/user-positions', params),
  
  // 获取用户持仓详情
  getById: (id: number) => 
    get<UserPosition>(`/user-positions/${id}`),
  
  // 创建用户持仓
  create: (data: Partial<UserPosition>) => 
    post<UserPosition>('/user-positions', data),
  
  // 更新用户持仓
  update: (id: number, data: Partial<UserPosition>) => 
    put<UserPosition>(`/user-positions/${id}`, data),
  
  // 删除用户持仓
  delete: (id: number) => 
    del(`/user-positions/${id}`),
  
  // 根据用户ID获取持仓
  getByUserId: (userId: number, params?: QueryParams) => 
    get<PageResult<UserPosition>>(`/user-positions/user/${userId}`, params),
  
  // 根据产品ID获取持仓
  getByProductId: (productId: number, params?: QueryParams) => 
    get<PageResult<UserPosition>>(`/user-positions/product/${productId}`, params),
  
  // 获取用户总资产
  getUserTotalAssets: (userId: number) => 
    get<{ totalAssets: number; totalProfitLoss: number }>(`/user-positions/user/${userId}/total-assets`),
  
  // 更新持仓市值
  updateMarketValue: (id: number, marketValue: number) => 
    put<UserPosition>(`/user-positions/${id}/market-value`, { marketValue }),
};

// 资金流水API
export const capitalFlowApi = {
  // 获取资金流水列表
  getList: (params?: QueryParams) => 
    get<PageResult<CapitalFlow>>('/capital-flows', params),
  
  // 获取资金流水详情
  getById: (id: number) => 
    get<CapitalFlow>(`/capital-flows/${id}`),
  
  // 创建资金流水
  create: (data: Partial<CapitalFlow>) => 
    post<CapitalFlow>('/capital-flows', data),
  
  // 更新资金流水
  update: (id: number, data: Partial<CapitalFlow>) => 
    put<CapitalFlow>(`/capital-flows/${id}`, data),
  
  // 删除资金流水
  delete: (id: number) => 
    del(`/capital-flows/${id}`),
  
  // 根据用户ID获取流水
  getByUserId: (userId: number, params?: QueryParams) => 
    get<PageResult<CapitalFlow>>(`/capital-flows/user/${userId}`, params),
  
  // 根据流水类型获取流水
  getByFlowType: (flowType: string, params?: QueryParams) => 
    get<PageResult<CapitalFlow>>(`/capital-flows/type/${flowType}`, params),
  
  // 根据订单ID获取流水
  getByOrderId: (orderId: number) => 
    get<CapitalFlow[]>(`/capital-flows/order/${orderId}`),
  
  // 根据产品ID获取流水
  getByProductId: (productId: number, params?: QueryParams) => 
    get<PageResult<CapitalFlow>>(`/capital-flows/product/${productId}`, params),
  
  // 根据时间范围获取流水
  getByTimeRange: (startTime: string, endTime: string, params?: QueryParams) => 
    get<PageResult<CapitalFlow>>(`/capital-flows/time-range`, { startTime, endTime, ...params }),
  
  // 获取用户账户余额
  getUserBalance: (userId: number) => 
    get<{ balance: number; frozenAmount: number }>(`/capital-flows/user/${userId}/balance`),
  
  // 充值
  deposit: (userId: number, amount: number, remark?: string) => 
    post<CapitalFlow>('/capital-flows/deposit', { userId, amount, remark }),
  
  // 提现
  withdraw: (userId: number, amount: number, remark?: string) => 
    post<CapitalFlow>('/capital-flows/withdraw', { userId, amount, remark }),
};

// 兼容性导出函数
export const getTradeOrderList = (params?: QueryParams) => 
  tradeOrderApi.getList(params);

export const deleteTradeOrder = (id: number) => 
  tradeOrderApi.delete(id);

export const getTradeRecordList = (params?: QueryParams) => 
  tradeRecordApi.getList(params);

export const deleteTradeRecord = (id: number) => 
  tradeRecordApi.delete(id);

export const getUserPositionList = (params?: QueryParams) => 
  userPositionApi.getList(params);

export const deleteUserPosition = (id: number) => 
  userPositionApi.delete(id);

export const getCapitalFlowList = (params?: QueryParams) => 
  capitalFlowApi.getList(params);

export const deleteCapitalFlow = (id: number) => 
  capitalFlowApi.delete(id); 