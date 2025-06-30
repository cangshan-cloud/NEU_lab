import { get, post, put, del } from '../utils/request';
import type { FundTag } from '../types';

export const fundTagApi = {
  getAll: () => get<FundTag[]>('/fund-tags'),
  create: (data: Partial<FundTag>) => post<FundTag>('/fund-tags', data),
  update: (id: number, data: Partial<FundTag>) => put<FundTag>(`/fund-tags/${id}`, data),
  delete: (id: number) => del(`/fund-tags/${id}`),
}; 