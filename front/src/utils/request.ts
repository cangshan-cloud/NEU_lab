import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import { message } from 'antd';
import type { ApiResponse } from '../types';
import qs from 'qs';
import { useCallback } from 'react';

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
  paramsSerializer: params => qs.stringify(params, { arrayFormat: 'repeat' })
});

// 请求拦截器：自动携带token
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers = config.headers || {};
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    const { data } = response;
    
    // 如果响应成功但业务状态码不是0（后端成功状态码）
    if (data.code !== 0) {
      message.error(data.message || '请求失败');
      return Promise.reject(new Error(data.message || '请求失败'));
    }
    
    return response;
  },
  (error) => {
    // 处理网络错误
    if (error.response) {
      const { status, data } = error.response;
      switch (status) {
        case 401:
          message.error('未授权，请重新登录');
          // 可以在这里处理登录过期
          localStorage.removeItem('token');
          window.location.href = '/login';
          break;
        case 403:
          message.error('拒绝访问');
          break;
        case 404:
          message.error('请求的资源不存在');
          break;
        case 500:
          message.error('服务器内部错误');
          break;
        default:
          message.error(data?.message || '请求失败');
      }
    } else if (error.request) {
      message.error('网络错误，请检查网络连接');
    } else {
      message.error('请求配置错误');
    }
    
    return Promise.reject(error);
  }
);

// 封装GET请求
export const get = <T = any>(url: string, params?: any, config?: AxiosRequestConfig) => {
  return request.get<ApiResponse<T>>(url, { params, ...config });
};

// 封装POST请求
export const post = <T = any>(url: string, data?: any, config?: AxiosRequestConfig) => {
  return request.post<ApiResponse<T>>(url, data, config);
};

// 封装PUT请求
export const put = <T = any>(url: string, data?: any, config?: AxiosRequestConfig) => {
  return request.put<ApiResponse<T>>(url, data, config);
};

// 封装DELETE请求
export const del = <T = any>(url: string, config?: AxiosRequestConfig) => {
  return request.delete<ApiResponse<T>>(url, config);
};

/**
 * 埋点事件上报
 * @param event 事件类型（如view、click、search、form_submit等）
 * @param page 当前页面路径
 * @param properties 其他属性（如按钮ID、表单类型、搜索词等）
 */
export function trackEvent(event: string, page: string, properties: Record<string, any> = {}) {
  const user = localStorage.getItem('user');
  let userId = undefined;
  try {
    userId = user ? JSON.parse(user).id : undefined;
  } catch {}
  const payload = {
    userId,
    eventType: event, // 修正字段名，确保与后端一致
    page,
    timestamp: Date.now(),
    properties,
    platform: 'web',
  };
  // 忽略本地开发时的无效上报
  if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
    // 可选：开发环境不实际上报
    // return;
  }
  return request.post('/track-events', payload).catch(() => {});
}

/**
 * React Hook: useTrackEvent
 * 用于在组件中便捷调用trackEvent进行埋点
 * @returns (event, page, properties) => void
 */
export function useTrackEvent() {
  return useCallback((event: string, page: string, properties: Record<string, any> = {}) => {
    trackEvent(event, page, properties);
  }, []);
}

export default request; 