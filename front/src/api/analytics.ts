import request from '../utils/request';

// 实时统计
export function getRealtimeAnalytics() {
  return request.get('/analytics/realtime');
}

// 历史统计
export function getHistoryAnalytics(params: { start: string; end: string }) {
  return request.get('/analytics/overview', { params });
}

// AI画像异步结果查询
export function getUserProfile(userId: number) {
  return request.get(`/ai/profile/result`, { params: { userId } });
}

// 用户分群
export function getUserSegments() {
  return request.get('/analytics/segments');
}

// 大模型行为洞察摘要
export function getLlmSummary() {
  return request.get('/analytics/llm-summary');
} 