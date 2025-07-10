import { get, post, put, del } from '../utils/request';
import type { 
  FofPortfolio, 
  IndexPortfolio, 
  TimingPortfolio, 
  Product, 
  ProductVO,
  ProductReview, 
  ProductPerformance,
  PageResult,
  QueryParams 
} from '../types';

// FOF组合API
export const fofPortfolioApi = {
  // 获取FOF组合列表
  getList: (params?: QueryParams) => 
    get<PageResult<FofPortfolio>>('/fof-portfolios', params),
  
  // 获取FOF组合详情
  getById: (id: number) => 
    get<FofPortfolio>(`/fof-portfolios/${id}`),
  
  // 创建FOF组合
  create: (data: Partial<FofPortfolio>) => 
    post<FofPortfolio>('/fof-portfolios', data),
  
  // 更新FOF组合
  update: (id: number, data: Partial<FofPortfolio>) => 
    put<FofPortfolio>(`/fof-portfolios/${id}`, data),
  
  // 删除FOF组合
  delete: (id: number) => 
    del(`/fof-portfolios/${id}`),
  
  // 根据类型获取FOF组合
  getByType: (type: string) => 
    get<FofPortfolio[]>(`/fof-portfolios/type/${type}`),
  
  // 获取所有FOF组合（不分页）
  getAll: () => 
    get<FofPortfolio[]>('/fof-portfolios/all'),
};

// 指数组合API
export const indexPortfolioApi = {
  // 获取指数组合列表
  getList: (params?: QueryParams) => 
    get<PageResult<IndexPortfolio>>('/index-portfolios', params),
  
  // 获取指数组合详情
  getById: (id: number) => 
    get<IndexPortfolio>(`/index-portfolios/${id}`),
  
  // 创建指数组合
  create: (data: Partial<IndexPortfolio>) => 
    post<IndexPortfolio>('/index-portfolios', data),
  
  // 更新指数组合
  update: (id: number, data: Partial<IndexPortfolio>) => 
    put<IndexPortfolio>(`/index-portfolios/${id}`, data),
  
  // 删除指数组合
  delete: (id: number) => 
    del(`/index-portfolios/${id}`),
  
  // 根据指数代码获取组合
  getByIndexCode: (indexCode: string) => 
    get<IndexPortfolio[]>(`/index-portfolios/index/${indexCode}`),
  
  // 获取所有指数组合（不分页）
  getAll: () => 
    get<IndexPortfolio[]>('/index-portfolios/all'),
};

// 择时组合API
export const timingPortfolioApi = {
  // 获取择时组合列表
  getList: (params?: QueryParams) => 
    get<PageResult<TimingPortfolio>>('/timing-portfolios', params),
  
  // 获取择时组合详情
  getById: (id: number) => 
    get<TimingPortfolio>(`/timing-portfolios/${id}`),
  
  // 创建择时组合
  create: (data: Partial<TimingPortfolio>) => 
    post<TimingPortfolio>('/timing-portfolios', data),
  
  // 更新择时组合
  update: (id: number, data: Partial<TimingPortfolio>) => 
    put<TimingPortfolio>(`/timing-portfolios/${id}`, data),
  
  // 删除择时组合
  delete: (id: number) => 
    del(`/timing-portfolios/${id}`),
  
  // 根据择时策略获取组合
  getByTimingStrategy: (timingStrategy: string) => 
    get<TimingPortfolio[]>(`/timing-portfolios/strategy/${timingStrategy}`),
  
  // 获取所有择时组合（不分页）
  getAll: () => 
    get<TimingPortfolio[]>('/timing-portfolios/all'),
};

// 产品API
export const productApi = {
  // 获取产品列表
  getList: (params?: QueryParams) => 
    get<PageResult<Product>>('/products', params),
  
  // 获取产品详情
  getById: (id: number) => 
    get<Product>(`/products/${id}`),
  
  // 创建产品
  create: (data: Partial<Product>) => 
    post<Product>('/products', data),
  
  // 更新产品
  update: (id: number, data: Partial<Product>) => 
    put<Product>(`/products/${id}`, data),
  
  // 删除产品
  delete: (id: number) => 
    del(`/products/${id}`),
  
  // 根据类型获取产品
  getByType: (type: string) => 
    get<Product[]>(`/products/type/${type}`),
  
  // 根据风险等级获取产品
  getByRiskLevel: (riskLevel: string) => 
    get<Product[]>(`/products/risk/${riskLevel}`),
  
  // 获取所有产品（不分页）
  getAll: () => 
    get<Product[]>('/products'),
  
  // 获取所有产品VO（不分页）
  getAllVOs: () => 
    get<ProductVO[]>('/products/vo'),
  
  // 提交产品审核申请
  submitReview: (id: number) => post(`/products/${id}/submit-review`),
};

// 产品审核API
export const productReviewApi = {
  // 获取产品审核列表
  getList: (params?: QueryParams) => 
    get<PageResult<ProductReview>>('/product-reviews', params),
  
  // 获取产品审核详情
  getById: (id: number) => 
    get<ProductReview>(`/product-reviews/${id}`),
  
  // 创建产品审核
  create: (data: Partial<ProductReview>) => 
    post<ProductReview>('/product-reviews', data),
  
  // 更新产品审核
  update: (id: number, data: Partial<ProductReview>) => 
    put<ProductReview>(`/product-reviews/${id}`, data),
  
  // 删除产品审核
  delete: (id: number) => 
    del(`/product-reviews/${id}`),
  
  // 根据产品ID获取审核
  getByProductId: (productId: number) => 
    get<ProductReview[]>(`/product-reviews/product/${productId}`),
  
  // 根据审核状态获取
  getByStatus: (status: string) => 
    get<ProductReview[]>(`/product-reviews/status/${status}`),
  
  // 获取所有审核记录
  getAll: () => 
    get<ProductReview[]>('/product-reviews'),
  
  // 获取待审核列表
  getPending: () => 
    get<ProductReview[]>('/product-reviews/pending'),
  
  // 审核通过
  approve: (id: number, data: Partial<ProductReview>) => 
    put<ProductReview>(`/product-reviews/${id}/approve`, data),
  
  // 审核拒绝
  reject: (id: number, data: Partial<ProductReview>) => 
    put<ProductReview>(`/product-reviews/${id}/reject`, data),
};

// 产品收益API
export const productPerformanceApi = {
  // 获取产品收益列表
  getList: (params?: QueryParams) => 
    get<PageResult<ProductPerformance>>('/product-performances', params),
  
  // 获取产品收益详情
  getById: (id: number) => 
    get<ProductPerformance>(`/product-performances/${id}`),
  
  // 创建产品收益
  create: (data: Partial<ProductPerformance>) => 
    post<ProductPerformance>('/product-performances', data),
  
  // 更新产品收益
  update: (id: number, data: Partial<ProductPerformance>) => 
    put<ProductPerformance>(`/product-performances/${id}`, data),
  
  // 删除产品收益
  delete: (id: number) => 
    del(`/product-performances/${id}`),
  
  // 根据产品ID获取收益
  getByProductId: (productId: number, params?: QueryParams) => 
    get<PageResult<ProductPerformance>>(`/product-performances/product/${productId}`, params),
  
  // 根据日期获取收益
  getByDate: (date: string, params?: QueryParams) => 
    get<PageResult<ProductPerformance>>(`/product-performances/date/${date}`, params),
  
  // 批量创建产品收益
  batchCreate: (data: Partial<ProductPerformance>[]) => 
    post<ProductPerformance[]>('/product-performances/batch', data),
};

// 兼容性导出函数
export const getProductList = (params?: QueryParams) => 
  productApi.getList(params);

export const getProductById = (id: number) => 
  productApi.getById(id);

export const deleteProduct = (id: number) => 
  productApi.delete(id);

export const getProductReviewList = (params?: QueryParams) => 
  productReviewApi.getList(params);

export const deleteProductReview = (id: number) => 
  productReviewApi.delete(id); 