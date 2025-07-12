import React, { useEffect, useState } from 'react';
import { listUsers, disableUser, deleteUser, updateUserRole, listRoles } from '../api/user';
import { useTrackEvent } from '../utils/request';

const UserList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/users');
  }, [track]);
  // 导出、批量操作等操作可用track('click', '/users', { buttonId: 'export' })等
  // 未来如有搜索、筛选、编辑、禁用等操作，可直接调用track('click', '/users', { buttonId: 'xxx', ... })
  const [users, setUsers] = useState<any[]>([]);
  const [roles, setRoles] = useState<any[]>([]);
  const [params, setParams] = useState({ page: 0, size: 10, role: '', status: '', keyword: '' });
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState('');

  const fetchUsers = async () => {
    setLoading(true);
    try {
      const res = await listUsers(params);
      setUsers(res.data.data.content);
      setTotal(res.data.data.totalElements);
    } finally {
      setLoading(false);
    }
  };
  const fetchRoles = async () => {
    const res = await listRoles();
    setRoles(res.data.data);
  };
  useEffect(() => { fetchUsers(); }, [params]);
  useEffect(() => { fetchRoles(); }, []);

  const handleDisable = async (id: number) => {
    await disableUser(id);
    setMsg('已禁用');
    fetchUsers();
  };
  const handleDelete = async (id: number) => {
    await deleteUser(id);
    setMsg('已删除');
    fetchUsers();
  };
  const handleRoleChange = async (id: number, roleId: number) => {
    await updateUserRole(id, roleId);
    setMsg('角色已修改');
    fetchUsers();
  };
  const handlePageChange = (delta: number) => {
    setParams({ ...params, page: params.page + delta });
  };
  const handleFilter = (e: React.FormEvent) => {
    e.preventDefault();
    fetchUsers();
  };

  return (
    <div style={{ maxWidth: 900, margin: '40px auto', padding: 24, boxShadow: '0 2px 8px #eee', borderRadius: 8 }}>
      <h2>用户管理</h2>
      <form onSubmit={handleFilter} style={{ display: 'flex', gap: 8, marginBottom: 16 }}>
        <input placeholder="关键词" value={params.keyword} onChange={e => setParams({ ...params, keyword: e.target.value })} style={{ padding: 4 }} />
        <select value={params.role} onChange={e => setParams({ ...params, role: e.target.value })} style={{ padding: 4 }}>
          <option value="">全部角色</option>
          {roles.map((r: any) => <option key={r.id} value={String(r.id)}>{r.roleName}</option>)}
        </select>
        <select value={params.status} onChange={e => setParams({ ...params, status: e.target.value })} style={{ padding: 4 }}>
          <option value="">全部状态</option>
          <option value="ACTIVE">正常</option>
          <option value="INACTIVE">禁用</option>
        </select>
        <button type="submit" style={{ padding: '4px 16px' }}>筛选</button>
      </form>
      {msg && <div style={{ color: 'green', marginBottom: 8 }}>{msg}</div>}
      <table style={{ width: '100%', borderCollapse: 'collapse', marginBottom: 16 }}>
        <thead>
          <tr style={{ background: '#fafafa' }}>
            <th style={{ border: '1px solid #eee', padding: 8 }}>ID</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>用户名</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>角色</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>真实姓名</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>邮箱</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>手机号</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>状态</th>
            <th style={{ border: '1px solid #eee', padding: 8 }}>操作</th>
          </tr>
        </thead>
        <tbody>
          {users.map(u => (
            <tr key={u.id}>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.id}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.username}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>
                <select value={String(u.roleId)} onChange={e => handleRoleChange(u.id, Number(e.target.value))} style={{ padding: 4 }}>
                  {roles.map((r: any) => <option key={r.id} value={String(r.id)}>{r.roleName}</option>)}
                </select>
              </td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.realName}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.email}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.phone}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>{u.status}</td>
              <td style={{ border: '1px solid #eee', padding: 8 }}>
                {u.status === 'ACTIVE' && <button onClick={() => handleDisable(u.id)} style={{ marginRight: 8, padding: '2px 8px' }}>禁用</button>}
                <button onClick={() => handleDelete(u.id)} style={{ padding: '2px 8px', color: 'red' }}>删除</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <button disabled={params.page === 0} onClick={() => handlePageChange(-1)}>上一页</button>
        <span>第 {params.page + 1} 页 / 共 {Math.ceil(total / params.size)} 页</span>
        <button disabled={(params.page + 1) * params.size >= total} onClick={() => handlePageChange(1)}>下一页</button>
      </div>
      {loading && <div style={{ marginTop: 16 }}>加载中...</div>}
    </div>
  );
};

export default UserList; 