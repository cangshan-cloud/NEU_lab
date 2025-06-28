import { get, post, put, del } from '../utils/request';
import type { 
  Factor, 
  FactorTree, 
  FactorTreeNode, 
  FactorData,
  PageResult,
  QueryParams 
} from '../types';

// 因子API
export const factorApi = {
  // 获取因子列表
  getList: (params?: QueryParams) => 
    get<PageResult<Factor>>('/factors', params),
  
  // 获取因子详情
  getById: (id: number) => 
    get<Factor>(`/factors/${id}`),
  
  // 创建因子
  create: (data: Partial<Factor>) => 
    post<Factor>('/factors', data),
  
  // 更新因子
  update: (id: number, data: Partial<Factor>) => 
    put<Factor>(`/factors/${id}`, data),
  
  // 删除因子
  delete: (id: number) => 
    del(`/factors/${id}`),
  
  // 根据分类获取因子
  getByCategory: (category: string) => 
    get<Factor[]>(`/factors/category/${category}`),
  
  // 根据类型获取因子
  getByType: (type: string) => 
    get<Factor[]>(`/factors/type/${type}`),
  
  // 获取所有因子（不分页）
  getAll: () => 
    get<Factor[]>('/factors/all'),
};

// 因子树API
export const factorTreeApi = {
  // 获取因子树列表
  getList: (params?: QueryParams) => 
    get<PageResult<FactorTree>>('/factor-trees', params),
  
  // 获取因子树详情
  getById: (id: number) => 
    get<FactorTree>(`/factor-trees/${id}`),
  
  // 创建因子树
  create: (data: Partial<FactorTree>) => 
    post<FactorTree>('/factor-trees', data),
  
  // 更新因子树
  update: (id: number, data: Partial<FactorTree>) => 
    put<FactorTree>(`/factor-trees/${id}`, data),
  
  // 删除因子树
  delete: (id: number) => 
    del(`/factor-trees/${id}`),
  
  // 获取所有因子树（不分页）
  getAll: () => 
    get<FactorTree[]>('/factor-trees/all'),
};

// 因子树节点API
export const factorTreeNodeApi = {
  // 获取因子树节点列表
  getList: (params?: QueryParams) => 
    get<PageResult<FactorTreeNode>>('/factor-tree-nodes', params),
  
  // 获取因子树节点详情
  getById: (id: number) => 
    get<FactorTreeNode>(`/factor-tree-nodes/${id}`),
  
  // 创建因子树节点
  create: (data: Partial<FactorTreeNode>) => 
    post<FactorTreeNode>('/factor-tree-nodes', data),
  
  // 更新因子树节点
  update: (id: number, data: Partial<FactorTreeNode>) => 
    put<FactorTreeNode>(`/factor-tree-nodes/${id}`, data),
  
  // 删除因子树节点
  delete: (id: number) => 
    del(`/factor-tree-nodes/${id}`),
  
  // 根据因子树ID获取节点
  getByTreeId: (treeId: number) => 
    get<FactorTreeNode[]>(`/factor-tree-nodes/tree/${treeId}`),
  
  // 根据父节点ID获取子节点
  getByParentId: (parentId: number) => 
    get<FactorTreeNode[]>(`/factor-tree-nodes/parent/${parentId}`),
};

// 因子数据API
export const factorDataApi = {
  // 获取因子数据列表
  getList: (params?: QueryParams) => 
    get<PageResult<FactorData>>('/factor-data', params),
  
  // 获取因子数据详情
  getById: (id: number) => 
    get<FactorData>(`/factor-data/${id}`),
  
  // 创建因子数据
  create: (data: Partial<FactorData>) => 
    post<FactorData>('/factor-data', data),
  
  // 更新因子数据
  update: (id: number, data: Partial<FactorData>) => 
    put<FactorData>(`/factor-data/${id}`, data),
  
  // 删除因子数据
  delete: (id: number) => 
    del(`/factor-data/${id}`),
  
  // 根据因子ID获取数据
  getByFactorId: (factorId: number, params?: QueryParams) => 
    get<PageResult<FactorData>>(`/factor-data/factor/${factorId}`, params),
  
  // 根据基金ID获取数据
  getByFundId: (fundId: number, params?: QueryParams) => 
    get<PageResult<FactorData>>(`/factor-data/fund/${fundId}`, params),
  
  // 根据日期获取数据
  getByDate: (date: string, params?: QueryParams) => 
    get<PageResult<FactorData>>(`/factor-data/date/${date}`, params),
  
  // 批量创建因子数据
  batchCreate: (data: Partial<FactorData>[]) => 
    post<FactorData[]>('/factor-data/batch', data),
};

// 兼容性导出函数
export const getFactorTreeList = (params?: QueryParams) => 
  factorTreeApi.getList(params);

export const deleteFactorTree = (id: number) => 
  factorTreeApi.delete(id);

export const getFactorList = (params?: QueryParams) => 
  factorApi.getList(params);

export const deleteFactor = (id: number) => 
  factorApi.delete(id); 