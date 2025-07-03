import request, { get, post, put } from '../utils/request';

// 发起角色变更申请
export function createRoleChangeRequest(data: { targetRoleId: number; reason: string }) {
  return post('/role-change-requests', data);
}

// 查询所有角色变更申请（管理员/高权限角色用）
export function getAllRoleChangeRequests(params: { status?: string; page?: number; size?: number }) {
  return get('/role-change-requests', params);
}

// 查询单个申请详情
export function getRoleChangeRequestDetail(id: number) {
  return get(`/role-change-requests/${id}`);
}

// 审核角色变更申请
export function reviewRoleChangeRequest(id: number, data: { status: string; reviewComment?: string }) {
  return put(`/role-change-requests/${id}/review`, data);
} 