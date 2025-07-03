import { get, post, put, del } from '../utils/request';
import type { 
  FundCompany, 
  FundManager, 
  Fund, 
  FundTag, 
  FundPortfolio,
  PageResult,
  QueryParams 
} from '../types';

// 基金公司API
export const fundCompanyApi = {
  // 获取基金公司列表
  getList: (params?: QueryParams) => 
    get<PageResult<FundCompany>>('/fund-companies', params),
  
  // 获取基金公司详情
  getById: (id: number) => 
    get<FundCompany>(`/fund-companies/${id}`),
  
  // 创建基金公司
  create: (data: Partial<FundCompany>) => 
    post<FundCompany>('/fund-companies', data),
  
  // 更新基金公司
  update: (id: number, data: Partial<FundCompany>) => 
    put<FundCompany>(`/fund-companies/${id}`, data),
  
  // 删除基金公司
  delete: (id: number) => 
    del(`/fund-companies/${id}`),
  
  // 获取所有基金公司（不分页）
  getAll: () => get<FundCompany[]>('/fund-companies'),
};

// 基金经理API
export const fundManagerApi = {
  // 获取基金经理列表
  getList: (params?: QueryParams) => 
    get<PageResult<FundManager>>('/fund-managers', params),
  
  // 获取基金经理详情
  getById: (id: number) => 
    get<FundManager>(`/fund-managers/${id}`),
  
  // 创建基金经理
  create: (data: Partial<FundManager>) => 
    post<FundManager>('/fund-managers', data),
  
  // 更新基金经理
  update: (id: number, data: Partial<FundManager>) => 
    put<FundManager>(`/fund-managers/${id}`, data),
  
  // 删除基金经理
  delete: (id: number) => 
    del(`/fund-managers/${id}`),
  
  // 根据公司ID获取基金经理
  getByCompanyId: (companyId: number) => 
    get<FundManager[]>(`/fund-managers/company/${companyId}`),
};

// 基金API
export const fundApi = {
  // 获取基金列表
  getList: (params?: QueryParams) => 
    get<PageResult<Fund>>('/funds', params),
  
  // 获取基金详情
  getById: (id: number) => 
    get<Fund>(`/funds/${id}`),
  
  // 创建基金
  create: (data: Partial<Fund>) => 
    post<Fund>('/funds', data),
  
  // 更新基金
  update: (id: number, data: Partial<Fund>) => 
    put<Fund>(`/funds/${id}`, data),
  
  // 删除基金
  delete: (id: number) => 
    del(`/funds/${id}`),
  
  // 根据公司ID获取基金
  getByCompanyId: (companyId: number) => 
    get<Fund[]>(`/funds/company/${companyId}`),
  
  // 根据基金经理ID获取基金
  getByManagerId: (managerId: number) => 
    get<Fund[]>(`/funds/manager/${managerId}`),
  
  // 搜索基金
  search: (keyword: string) => 
    get<Fund[]>(`/funds/search?keyword=${encodeURIComponent(keyword)}`),
};

// 基金标签API
export const fundTagApi = {
  // 获取基金标签列表
  getList: (params?: QueryParams) => 
    get<PageResult<FundTag>>('/fund-tags', params),
  
  // 获取基金标签详情
  getById: (id: number) => 
    get<FundTag>(`/fund-tags/${id}`),
  
  // 创建基金标签
  create: (data: Partial<FundTag>) => 
    post<FundTag>('/fund-tags', data),
  
  // 更新基金标签
  update: (id: number, data: Partial<FundTag>) => 
    put<FundTag>(`/fund-tags/${id}`, data),
  
  // 删除基金标签
  delete: (id: number) => 
    del(`/fund-tags/${id}`),
  
  // 根据分类获取标签
  getByCategory: (category: string) => 
    get<FundTag[]>(`/fund-tags/category/${category}`),
  
  // 获取所有标签（不分页）
  getAll: () => 
    get<FundTag[]>('/fund-tags/all'),
};

// 基金组合API
export const fundPortfolioApi = {
  // 获取基金组合列表
  getList: (params?: QueryParams) => 
    get<PageResult<FundPortfolio>>('/fund-portfolios', params),
  
  // 获取基金组合详情
  getById: (id: number) => 
    get<FundPortfolio>(`/fund-portfolios/${id}`),
  
  // 创建基金组合
  create: (data: Partial<FundPortfolio>) => 
    post<FundPortfolio>('/fund-portfolios', data),
  
  // 更新基金组合
  update: (id: number, data: Partial<FundPortfolio>) => 
    put<FundPortfolio>(`/fund-portfolios/${id}`, data),
  
  // 删除基金组合
  delete: (id: number) => 
    del(`/fund-portfolios/${id}`),
  
  // 根据类型获取组合
  getByType: (type: string) => 
    get<FundPortfolio[]>(`/fund-portfolios/type/${type}`),
  
  // 获取所有组合（不分页）
  getAll: () => 
    get<FundPortfolio[]>('/fund-portfolios/all'),
};

// 获取所有基金组合（不分页）
export function getPortfolios() {
  return fundPortfolioApi.getAll();
}

// 获取所有产品（假设产品API为productApi，若无请后续补充）
import { productApi } from './product';
export function getProducts() {
  return productApi.getAll();
} 