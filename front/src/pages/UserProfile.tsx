import React, { useEffect, useState } from 'react';
import { getUser, updateUser, resetPassword } from '../api/user';
import { createRoleChangeRequest } from '../api/roleChangeRequest';
import { get as getRoles } from '../utils/request';
import { Select, message as antdMsg } from 'antd';
import { fundCompanyApi, fundManagerApi } from '../api/fund';

const UserProfile: React.FC = () => {
  const [user, setUser] = useState<any>(null);
  const [edit, setEdit] = useState(false);
  const [form, setForm] = useState({ realName: '', email: '', phone: '' });
  const [pwd, setPwd] = useState('');
  const [msg, setMsg] = useState('');

  useEffect(() => {
    const localUser = localStorage.getItem('user');
    if (localUser) {
      const u = JSON.parse(localUser);
      setUser(u);
      setForm({ realName: u.realName || '', email: u.email || '', phone: u.phone || '' });
    }
  }, []);

  const handleEdit = () => setEdit(true);
  const handleCancel = () => {
    setEdit(false);
    setMsg('');
    if (user) setForm({ realName: user.realName || '', email: user.email || '', phone: user.phone || '' });
  };
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };
  const handleSave = async () => {
    if (!user) return;
    try {
      const res = await updateUser(user.id, form);
      setUser({ ...user, ...form });
      localStorage.setItem('user', JSON.stringify({ ...user, ...form }));
      setMsg('信息已更新');
      setEdit(false);
    } catch (err: any) {
      setMsg(err.response?.data?.message || '更新失败');
    }
  };
  const handlePwdReset = async () => {
    if (!user || !pwd) return;
    try {
      await resetPassword(user.id, { newPassword: pwd });
      setMsg('密码已重置');
      setPwd('');
    } catch (err: any) {
      setMsg(err.response?.data?.message || '重置失败');
    }
  };

  if (!user) return <div style={{ margin: 40 }}>未登录</div>;

  return (
    <div style={{ maxWidth: 500, margin: '40px auto', padding: 24, boxShadow: '0 2px 8px #eee', borderRadius: 8 }}>
      <h2>个人中心</h2>
      <div style={{ marginBottom: 16 }}>用户名：{user.username}</div>
      <div style={{ marginBottom: 16 }}>角色：{user.roleName}</div>
      <div style={{ marginBottom: 16 }}>
        真实姓名：{edit ? <input name="realName" value={form.realName} onChange={handleChange} style={{ padding: 4 }} /> : user.realName || '-'}
      </div>
      <div style={{ marginBottom: 16 }}>
        邮箱：{edit ? <input name="email" value={form.email} onChange={handleChange} style={{ padding: 4 }} /> : user.email || '-'}
      </div>
      <div style={{ marginBottom: 16 }}>
        手机号：{edit ? <input name="phone" value={form.phone} onChange={handleChange} style={{ padding: 4 }} /> : user.phone || '-'}
      </div>
      {edit ? (
        <div style={{ marginBottom: 16 }}>
          <button onClick={handleSave} style={{ marginRight: 8, padding: '6px 16px', background: '#1677ff', color: '#fff', border: 'none', borderRadius: 4 }}>保存</button>
          <button onClick={handleCancel} style={{ padding: '6px 16px', background: '#eee', border: 'none', borderRadius: 4 }}>取消</button>
        </div>
      ) : (
        <button onClick={handleEdit} style={{ marginBottom: 16, padding: '6px 16px', background: '#1677ff', color: '#fff', border: 'none', borderRadius: 4 }}>编辑信息</button>
      )}
      <div style={{ margin: '24px 0 8px 0', fontWeight: 500 }}>重置密码</div>
      <div style={{ display: 'flex', gap: 8, marginBottom: 16 }}>
        <input type="password" placeholder="新密码" value={pwd} onChange={e => setPwd(e.target.value)} style={{ padding: 4, flex: 1 }} />
        <button onClick={handlePwdReset} style={{ padding: '6px 16px', background: '#52c41a', color: '#fff', border: 'none', borderRadius: 4 }}>重置</button>
      </div>
      {msg && <div style={{ color: msg.includes('失败') ? 'red' : 'green', marginTop: 8 }}>{msg}</div>}
    </div>
  );
};

export default UserProfile; 