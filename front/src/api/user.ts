import request from '../utils/request';

// 用户注册
export function register(data: any) {
  return request.post('/users/register', data);
}

// 用户登录
export function login(data: any) {
  return request.post('/users/login', data);
}

// 获取用户详情
export function getUser(id: number) {
  return request.get(`/users/${id}`);
}

// 修改用户信息
export function updateUser(id: number, data: any) {
  return request.put(`/users/${id}`, data);
}

// 重置用户密码
export function resetPassword(id: number, data: any) {
  return request.put(`/users/${id}/password`, data);
}

// 用户分页与筛选
export function listUsers(params: any) {
  return request.get('/users', { params });
}

// 禁用用户
export function disableUser(id: number) {
  return request.put(`/users/${id}/disable`);
}

// 删除用户
export function deleteUser(id: number) {
  return request.delete(`/users/${id}`);
}

// 绑定基金经理
export function bindManager(id: number, managerId: number) {
  return request.post(`/users/${id}/bind-manager`, null, { params: { managerId } });
}

// 绑定基金公司
export function bindCompany(id: number, companyId: number) {
  return request.post(`/users/${id}/bind-company`, null, { params: { companyId } });
}

// 修改用户角色
export function updateUserRole(id: number, roleName: string) {
  return request.put(`/users/${id}/role`, null, { params: { roleName } });
}

// 获取所有角色
export function listRoles() {
  return request.get('/roles');
}

// 1. registerUser: 新增参数 companyId, managerId, roleId
// 2. getCompanyList, getManagerList, getRoleList: 新增API

export function getUserList() {
  return request.get('/api/users');
} 