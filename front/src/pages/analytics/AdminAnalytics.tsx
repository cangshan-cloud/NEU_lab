import React, { useEffect, useState, useRef } from 'react';
import { Card, Row, Col, Table, Tag, Descriptions, Alert, Spin, message, Typography } from 'antd';
import { getRealtimeAnalytics, getUserSegments, getUserProfile, getLlmSummary } from '../../api/analytics';
import axios from 'axios';
const { Title, Text } = Typography;

// 标签颜色映射
const tagColorMap: Record<string, string> = {
  策略: 'blue',
  基金: 'green',
  数据分析: 'geekblue',
  产品: 'purple',
  交易: 'orange',
  风控: 'volcano',
  科技: 'cyan',
};

const AdminAnalytics: React.FC = () => {
  const [realtime, setRealtime] = useState<any>(null);
  const [segments, setSegments] = useState<any[]>([]);
  const [profiles, setProfiles] = useState<any[]>([]);
  const [llmSummary, setLlmSummary] = useState<{
    summary: string;
    advice: string;
    profiles?: any[];
    segments?: any[];
  } | null>(null);
  const [llmTaskId, setLlmTaskId] = useState<string | null>(null);
  const [llmPolling, setLlmPolling] = useState(false);
  const [llmPollError, setLlmPollError] = useState<string | null>(null);
  const llmMaxRetry = 20;
  const [loading, setLoading] = useState(true);
  const timerRef = useRef<number | null>(null);
  const [polling, setPolling] = useState(false);
  const [pollError, setPollError] = useState<string | null>(null);
  const maxRetry = 20; // 最多轮询20次，约1分钟

  // 拉取所有分群用户画像
  const fetchProfiles = async (userIds: number[]) => {
    try {
      const profileResArr = await Promise.all(userIds.map((id: number) => getUserProfile(id)));
      setProfiles(profileResArr.map(res => res.data.data));
      return profileResArr.map(res => res.data.data);
    } catch (e) {
      setPollError('用户画像加载失败');
      return [];
    }
  };

  const pollProfiles = async (userIds: number[]) => {
    setPolling(true);
    setPollError(null);
    let retry = 0;
    let finished = false;
    while (retry < maxRetry && !finished) {
      const profileList = await fetchProfiles(userIds);
      if (userIds.length && profileList.some(p => !p || !p.profileText)) {
        retry++;
        await new Promise(res => setTimeout(res, 5000));
      } else {
        finished = true;
      }
    }
    setPolling(false);
    if (!finished) setPollError('AI分析超时，请稍后刷新页面');
  };

  const fetchLlmSummaryAsync = async () => {
    setLlmSummary(null);
    setLlmTaskId(null);
    setLlmPolling(true);
    setLlmPollError(null);
    try {
      const res = await axios.post('/api/analytics/llm-summary/async');
      const taskId = res.data.data;
      setLlmTaskId(taskId);
      let retry = 0;
      let finished = false;
      while (retry < llmMaxRetry && !finished) {
        const pollRes = await axios.get('/api/analytics/llm-summary/result', { params: { taskId } });
        const { status, result } = pollRes.data.data;
        if (status === 'processing') {
          retry++;
          await new Promise(res => setTimeout(res, 5000));
        } else if (status === 'done' && result) {
          setLlmSummary({
            summary: result.summary,
            advice: result.advice,
            profiles: Array.isArray(result.profiles) ? result.profiles : [],
            segments: Array.isArray(result.segments) ? result.segments : [],
          });
          finished = true;
        } else if (status === 'error') {
          setLlmPollError(result?.summary || 'AI分析失败');
          finished = true;
        } else {
          setLlmPollError('AI分析未知状态');
          finished = true;
        }
      }
      if (!finished) setLlmPollError('AI分析超时，请稍后刷新页面');
    } catch (e: any) {
      setLlmPollError(e?.message || 'AI分析请求失败');
    } finally {
      setLlmPolling(false);
    }
  };

  useEffect(() => {
    setLoading(true);
    Promise.all([
      getRealtimeAnalytics(),
      getUserSegments()
    ]).then(async ([rtRes, segRes]) => {
      setRealtime(rtRes.data.data);
      setSegments(segRes.data.data || []);
      fetchLlmSummaryAsync();
      const userIds: number[] = Array.from(new Set((segRes.data.data || []).flatMap((s: any) => s.userIds as number[])));
      const profileList = await fetchProfiles(userIds);
      if (userIds.length && profileList.some(p => !p || !p.profileText)) {
        pollProfiles(userIds);
      }
    }).catch(err => {
      message.error('数据加载失败');
    }).finally(() => setLoading(false));
    return () => { setPolling(false); };
  }, []);

  return (
    <div style={{ padding: 24, background: '#f7f8fa', minHeight: '100vh' }}>
      <Title level={3} style={{ marginBottom: 24 }}>数据分析与洞察</Title>
      {loading ? <Spin size="large" style={{ margin: '80px auto', display: 'block' }} /> : <>
        {llmPolling && <Alert message="AI行为洞察分析中，请稍候..." type="info" showIcon style={{ marginBottom: 16 }} />}
        {llmPollError && <Alert message={llmPollError} type="error" showIcon style={{ marginBottom: 16 }} />}
        
        {/* 大模型行为洞察摘要 */}
        {llmSummary && (
          <Card style={{ marginBottom: 32, borderRadius: 16, boxShadow: '0 2px 12px #f0f1f2' }}>
            <Title level={4} style={{ marginBottom: 16 }}>大模型行为洞察摘要</Title>
            <Alert message={<span style={{ fontSize: 16 }}>{llmSummary.summary}</span>} type="info" showIcon style={{ marginBottom: 16, borderRadius: 8 }} />
            <Descriptions bordered column={1} style={{ marginBottom: 24 }}>
              <Descriptions.Item label={<b style={{ fontSize: 16 }}>智能运营建议</b>}>
                <div style={{ background: '#fafbfc', borderRadius: 8, padding: 16 }}>
                  <ul style={{ listStyle: 'none', margin: 0, padding: 0 }}>
                    {Array.isArray(llmSummary.advice) ? llmSummary.advice.map((item: string, idx: number) => (
                      <li key={idx} style={{ display: 'flex', alignItems: 'center', marginBottom: 8 }}>
                        <span style={{
                          minWidth: 22, height: 22, borderRadius: 6,
                          background: '#e6e8eb', color: '#666', fontWeight: 500,
                          display: 'flex', alignItems: 'center', justifyContent: 'center', marginRight: 12, fontSize: 15
                        }}>{idx + 1}</span>
                        <span style={{ fontSize: 15, color: '#333' }}>{item}</span>
                      </li>
                    )) : (
                      <span style={{ fontSize: 15 }}>{llmSummary.advice || ''}</span>
                    )}
                  </ul>
                </div>
              </Descriptions.Item>
            </Descriptions>
            
            {/* 结构化用户画像展示 */}
            {llmSummary.profiles && llmSummary.profiles.length > 0 && (
              <Card style={{ marginTop: 24, borderRadius: 12, background: '#fafcff' }} type="inner" title={<b style={{ fontSize: 16 }}>结构化用户画像列表</b>}>
                <Table
                  rowKey="userId"
                  dataSource={llmSummary.profiles}
                  columns={[
                    { title: '用户ID', dataIndex: 'userId', align: 'center' },
                    { title: '兴趣标签', dataIndex: 'tags', align: 'center', render: (tags: string) => tags?.split(',').map((t: string) => <Tag color={tagColorMap[t] || 'default'} key={t}>{t}</Tag>) },
                    { title: '画像描述', dataIndex: 'profileText', align: 'center', render: (text: string) => <Text type="secondary" style={{ fontSize: 15 }}>{text}</Text> },
                  ]}
                  pagination={false}
                  style={{ minWidth: 320 }}
                />
              </Card>
            )}
            
            {/* 结构化分群分析展示 */}
            {llmSummary.segments && llmSummary.segments.length > 0 && (
              <Card style={{ marginTop: 24, borderRadius: 12, background: '#f9f6ff' }} type="inner" title={<b style={{ fontSize: 16 }}>结构化用户分群分析</b>}>
                <Table
                  rowKey="segmentName"
                  dataSource={llmSummary.segments}
                  columns={[
                    { title: '分群名称', dataIndex: 'segmentName', align: 'center', render: (name: string) => <Tag color="geekblue">{name}</Tag> },
                    { title: '规则描述', dataIndex: 'rule', align: 'center' },
                    { title: '用户ID列表', dataIndex: 'userIds', align: 'center', render: (ids: number[]) => Array.isArray(ids) ? ids.map(id => <Tag color="blue" key={id}>{id}</Tag>) : ids },
                  ]}
                  pagination={false}
                  style={{ minWidth: 320 }}
                />
              </Card>
            )}
          </Card>
        )}
        
        {polling && <Alert message="AI分析中，请稍候..." type="info" showIcon style={{ marginBottom: 16 }} />}
        {pollError && <Alert message={pollError} type="error" showIcon style={{ marginBottom: 16 }} />}
        
        {/* 非结构化/原有用户画像列表（无结构化时兜底） */}
        {(llmSummary === null || !Array.isArray(llmSummary.profiles) || llmSummary.profiles.length === 0) && (
          <Card style={{ marginTop: 24, borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }} title="用户画像列表">
            <Table
              rowKey="userId"
              dataSource={profiles}
              columns={[
                { title: '用户ID', dataIndex: 'userId' },
                { title: '兴趣标签', dataIndex: 'tags', render: (tags: string) => tags?.split(',').map((t: string) => <Tag key={t}>{t}</Tag>) },
                { title: '画像描述', dataIndex: 'profileText' },
              ]}
              pagination={false}
            />
          </Card>
        )}
        
        {/* 非结构化/原有分群分析（无结构化时兜底） */}
        {(llmSummary === null || !Array.isArray(llmSummary.segments) || llmSummary.segments.length === 0) && (
          <Card style={{ marginTop: 24, borderRadius: 12, boxShadow: '0 2px 8px rgba(0,0,0,0.1)' }} title="用户分群分析">
            <Table
              rowKey="segmentName"
              dataSource={segments}
              columns={[
                { title: '分群名称', dataIndex: 'segmentName' },
                { title: '规则描述', dataIndex: 'rule' },
                { title: '用户ID列表', dataIndex: 'userIds', render: (ids: number[]) => ids.join(', ') },
              ]}
              pagination={false}
            />
          </Card>
        )}
      </>}
    </div>
  );
};

export default AdminAnalytics; 