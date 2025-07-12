import React, { useState, useEffect } from 'react';
import { register, listRoles } from '../api/user';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Typography, message, Card } from 'antd';
import { useTrackEvent } from '../utils/request';

const { Title } = Typography;

const UserRegister: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/user-register');
  }, [track]);
  // 注册、提交等操作可用track('click', '/user-register', { buttonId: 'register' })等
  const [loading, setLoading] = useState(false);
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [roleId, setRoleId] = useState<number>(2);

  useEffect(() => {
    // 获取所有角色，查找普通用户id
    listRoles().then(res => {
      const roles = res.data.data || [];
      const normalRole = roles.find((r: any) => r.roleName === '普通用户');
      if (normalRole) setRoleId(normalRole.id);
    });
  }, []);

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      await register({ ...values, roleId });
      message.success('注册成功，请登录');
      setTimeout(() => navigate('/login'), 1200);
    } catch (err: any) {
      message.error(err.response?.data?.message || '注册失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-bg">
      <Card
        style={{
          width: 370,
          boxShadow: '0 2px 12px rgba(0,0,0,0.08)',
          borderRadius: 12,
        }}
        bodyStyle={{ padding: 32 }}
      >
        <Title level={2} style={{ textAlign: 'center', color: '#52c41a', marginBottom: 32 }}>用户注册</Title>
        <Form form={form} layout="vertical" onFinish={onFinish} autoComplete="off">
          <Form.Item name="username" label="用户名" rules={[{ required: true, message: '请输入用户名' }]}> 
            <Input size="large" placeholder="用户名" autoFocus />
          </Form.Item>
          <Form.Item name="password" label="密码" rules={[{ required: true, message: '请输入密码' }]}> 
            <Input.Password size="large" placeholder="密码" />
          </Form.Item>
          <Form.Item name="realName" label="真实姓名" rules={[{ required: true, message: '请输入真实姓名' }]}> 
            <Input size="large" placeholder="真实姓名" />
          </Form.Item>
          <Form.Item name="email" label="邮箱" rules={[{ required: true, type: 'email', message: '请输入有效邮箱' }]}> 
            <Input size="large" placeholder="邮箱" />
          </Form.Item>
          <Form.Item name="phone" label="手机号" rules={[{ required: true, message: '请输入手机号' }]}> 
            <Input size="large" placeholder="手机号" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" size="large" block loading={loading} style={{ borderRadius: 6 }}>注册</Button>
          </Form.Item>
        </Form>
        <div style={{ textAlign: 'right', marginTop: 8 }}>
          <Button type="link" style={{ padding: 0 }} onClick={() => navigate('/login')}>已有账号？去登录</Button>
        </div>
      </Card>
    </div>
  );
};

export default UserRegister; 