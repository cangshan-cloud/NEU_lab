import React, { useState, useEffect } from 'react';
import { login } from '../api/user';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Typography, message, Card } from 'antd';
import { useTrackEvent } from '../utils/request';

const { Title } = Typography;

const UserLogin: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/user-login');
  }, [track]);
  // 登录、提交等操作可用track('click', '/user-login', { buttonId: 'login' })等
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: any) => {
    setLoading(true);
    try {
      const res = await login(values);
      const { token, user } = res.data.data;
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      message.success('登录成功');
      navigate('/');
    } catch (err: any) {
      message.error(err.response?.data?.message || '登录失败');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-page-bg">
      <Card
        style={{
          width: 350,
          boxShadow: '0 2px 12px rgba(0,0,0,0.08)',
          borderRadius: 12,
        }}
        bodyStyle={{ padding: 32 }}
      >
        <Title level={2} style={{ textAlign: 'center', color: '#1677ff', marginBottom: 32 }}>用户登录</Title>
        <Form layout="vertical" onFinish={onFinish} autoComplete="off">
          <Form.Item name="username" label="用户名" rules={[{ required: true, message: '请输入用户名' }]}> 
            <Input size="large" placeholder="用户名" autoFocus />
          </Form.Item>
          <Form.Item name="password" label="密码" rules={[{ required: true, message: '请输入密码' }]}> 
            <Input.Password size="large" placeholder="密码" />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit" size="large" block loading={loading} style={{ borderRadius: 6 }}>登录</Button>
          </Form.Item>
        </Form>
        <div style={{ textAlign: 'right', marginTop: 8 }}>
          <span style={{ color: '#888' }}>没有账号？</span>
          <Button type="link" style={{ padding: 0, marginLeft: 4 }} onClick={() => navigate('/register')}>去注册</Button>
        </div>
      </Card>
    </div>
  );
};

export default UserLogin; 