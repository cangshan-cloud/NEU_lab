import React, { useEffect, useState, useRef } from 'react';
import { Table, Button, Card, message, Pagination, Modal, Descriptions, Tag, Alert } from 'antd';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useTrackEvent } from '../../utils/request';

const AllStrategyBacktestList: React.FC = () => {
  const navigate = useNavigate();
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/all-strategy-backtests');
  }, [track]);
  // 回测、筛选、导出、查看详情等操作可用track('click', '/all-strategy-backtests', { buttonId: 'backtest' })等
  const [data, setData] = useState<any[]>([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [detail, setDetail] = useState<any | null>(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [strategyNameMap, setStrategyNameMap] = useState<{ [key: number]: string }>({});
  const fetchingRef = useRef<{ [key: number]: boolean }>({});

  useEffect(() => {
    setLoading(true);
    setError(null);
    axios.get(`/api/strategy-backtests?page=${page - 1}&size=${size}`)
      .then(res => {
        setData(res.data.data?.content || []);
        setTotal(res.data.data?.totalElements || 0);
        if (!res.data.data || !Array.isArray(res.data.data.content)) {
          setError('后端接口未返回分页数据结构');
        }
      })
      .catch((e) => {
        setError(`获取回测历史失败: ${e.response?.data?.message || e.message || '接口无响应'}`);
        message.error('获取回测历史失败');
      })
      .finally(() => setLoading(false));
  }, [page, size]);

  const formatPercent = (v?: number) => v !== undefined && v !== null ? `${(v * 100).toFixed(2)}%` : '-';
  const formatTime = (t?: string) => t ? new Date(t).toLocaleString() : '-';

  // 获取策略名称（带缓存）
  const fetchStrategyName = (strategyId: number) => {
    if (!strategyId || isNaN(Number(strategyId)) || strategyNameMap[strategyId] || fetchingRef.current[strategyId]) return;
    fetchingRef.current[strategyId] = true;
    axios.get(`/api/strategies/${strategyId}`)
      .then(res => {
        setStrategyNameMap(prev => ({ ...prev, [strategyId]: res.data.data?.strategyName || String(strategyId) }));
      })
      .catch(() => {
        setStrategyNameMap(prev => ({ ...prev, [strategyId]: String(strategyId) }));
      })
      .finally(() => {
        fetchingRef.current[strategyId] = false;
      });
  };

  const columns = [
    { title: '策略ID', dataIndex: 'strategyId' },
    {
      title: '策略名称',
      dataIndex: 'strategyId',
      render: (strategyId: number) => {
        if (strategyId && !isNaN(Number(strategyId))) {
          if (!strategyNameMap[strategyId]) fetchStrategyName(strategyId);
          return strategyNameMap[strategyId] || '加载中...';
        }
        return '-';
      }
    },
    { title: '回测名称', dataIndex: 'backtestName' },
    // 移除回测类型
    { title: '回测区间', render: (_: any, r: any) => `${r.startDate} ~ ${r.endDate}` },
    { title: '初始资金', dataIndex: 'initialCapital' },
    { title: '最终价值', dataIndex: 'finalValue' },
    { title: '总收益率', render: (_: any, r: any) => formatPercent(r.totalReturn) },
    { title: '最大回撤', render: (_: any, r: any) => formatPercent(r.maxDrawdown) },
    { title: '状态', render: (_: any, r: any) => <Tag color={r.status === 'SUCCESS' ? 'green' : 'orange'}>{r.status}</Tag> },
    { title: '创建时间', render: (_: any, r: any) => formatTime(r.createdAt) },
    { title: '操作', render: (_: any, r: any) => (
      <Button type="link" onClick={() => { setDetail(r); setModalOpen(true); }}>详情</Button>
    ) },
  ];

  return (
    <Card title={'全部策略回测历史'} bodyStyle={{padding: 0}}>
      {error && <Alert type="error" message={error} showIcon style={{ marginBottom: 16 }} />}
      <div style={{ width: '100%', overflowX: 'auto' }}>
        <Table
          rowKey="id"
          columns={columns}
          dataSource={data}
          loading={loading}
          pagination={false}
          bordered
          size="middle"
          style={{ minWidth: 1200 }}
          scroll={{ x: 1200 }}
        />
      </div>
      <Pagination
        current={page}
        pageSize={size}
        total={total}
        showSizeChanger
        onChange={p => setPage(p)}
        onShowSizeChange={(_, s) => { setSize(s); setPage(1); }}
        style={{ marginTop: 16, textAlign: 'right' }}
      />
      <Modal open={modalOpen} onCancel={() => setModalOpen(false)} footer={null} title="回测详情" width={700}>
        {detail && (
          <Descriptions bordered column={2}>
            <Descriptions.Item label="策略ID">{detail.strategyId}</Descriptions.Item>
            <Descriptions.Item label="策略名称"><b>{strategyNameMap[detail.strategyId] || '-'}</b></Descriptions.Item>
            <Descriptions.Item label="回测名称"><b>{detail.backtestName}</b></Descriptions.Item>
            {/* 移除回测类型 */}
            <Descriptions.Item label="回测区间">{detail.startDate} ~ {detail.endDate}</Descriptions.Item>
            <Descriptions.Item label="初始资金">{detail.initialCapital}</Descriptions.Item>
            <Descriptions.Item label="最终价值">{detail.finalValue}</Descriptions.Item>
            <Descriptions.Item label="总收益率"><b style={{color:'#1677ff'}}>{formatPercent(detail.totalReturn)}</b></Descriptions.Item>
            <Descriptions.Item label="年化收益率">{formatPercent(detail.annualReturn)}</Descriptions.Item>
            <Descriptions.Item label="最大回撤"><b style={{color:'#faad14'}}>{formatPercent(detail.maxDrawdown)}</b></Descriptions.Item>
            <Descriptions.Item label="夏普比率">{detail.sharpeRatio}</Descriptions.Item>
            <Descriptions.Item label="波动率">{detail.volatility}</Descriptions.Item>
            <Descriptions.Item label="胜率">{formatPercent(detail.winRate)}</Descriptions.Item>
            <Descriptions.Item label="回测结果">{detail.backtestResult}</Descriptions.Item>
            <Descriptions.Item label="状态">{detail.status}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{formatTime(detail.createdAt)}</Descriptions.Item>
            <Descriptions.Item label="更新时间">{formatTime(detail.updatedAt)}</Descriptions.Item>
            <Descriptions.Item label="明细">{detail.results}</Descriptions.Item>
          </Descriptions>
        )}
      </Modal>
    </Card>
  );
};

export default AllStrategyBacktestList; 