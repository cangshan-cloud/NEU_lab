import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Table, Button, Card, message, Pagination, Modal, Descriptions, Tag, Alert } from 'antd';
import axios from 'axios';
import { useTrackEvent } from '../../utils/request';

const StrategyBacktestList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/strategy-backtests');
  }, [track]);
  // 回测、筛选、导出、查看详情等操作可用track('click', '/strategy-backtests', { buttonId: 'backtest' })等
  // 导出、批量操作等操作可用track('click', '/strategy-backtests', { buttonId: 'export' })等
  const { id } = useParams<{ id?: string }>();
  const navigate = useNavigate();
  const [data, setData] = useState<any[]>([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [detail, setDetail] = useState<any | null>(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [strategyName, setStrategyName] = useState<string | null>(null);
  const [isValidIdInUrl, setIsValidIdInUrl] = useState(false);

  useEffect(() => {
    setLoading(true);
    setError(null);
    // 只在id存在且合法时才带strategyId，否则请求所有回测
    const parsedId = Number(id);
    const currentValidId = id !== undefined && id !== null && id !== '' && !isNaN(parsedId) && Number.isFinite(parsedId);
    setIsValidIdInUrl(currentValidId);

    // 默认只请求所有回测历史，不带strategyId
    let url = `/api/strategy-backtests?page=${page - 1}&size=${size}`;
    if (currentValidId) {
      url = `/api/strategy-backtests?strategyId=${parsedId}&page=${page - 1}&size=${size}`;
    }

    axios.get(url)
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

    // 只有在id合法时才查策略名
    if (currentValidId) {
      axios.get(`/api/strategies/${parsedId}`)
        .then(res => setStrategyName(res.data.data?.strategyName || null))
        .catch(() => setStrategyName(null));
    } else {
      setStrategyName(null);
    }
  }, [id, page, size]);

  const formatPercent = (v?: number) => v !== undefined && v !== null ? `${(v * 100).toFixed(2)}%` : '-';
  const formatTime = (t?: string) => t ? new Date(t).toLocaleString() : '-';

  const columns = [
    { title: '策略ID', dataIndex: 'strategyId' },
    { title: '回测名称', dataIndex: 'backtestName' },
    { title: '回测类型', dataIndex: 'backtestType' },
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
    <Card title={isValidIdInUrl ? `策略【${strategyName || id}】回测历史` : '全部策略回测历史'} extra={<Button onClick={() => navigate(-1)}>返回</Button>}>
      {error && <Alert type="error" message={error} showIcon style={{ marginBottom: 16 }} />}
      <Table
        rowKey="id"
        columns={columns}
        dataSource={data}
        loading={loading}
        pagination={false}
      />
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
            <Descriptions.Item label="回测名称">{detail.backtestName}</Descriptions.Item>
            <Descriptions.Item label="回测类型">{detail.backtestType}</Descriptions.Item>
            <Descriptions.Item label="回测区间">{detail.startDate} ~ {detail.endDate}</Descriptions.Item>
            <Descriptions.Item label="初始资金">{detail.initialCapital}</Descriptions.Item>
            <Descriptions.Item label="最终价值">{detail.finalValue}</Descriptions.Item>
            <Descriptions.Item label="总收益率">{formatPercent(detail.totalReturn)}</Descriptions.Item>
            <Descriptions.Item label="年化收益率">{formatPercent(detail.annualReturn)}</Descriptions.Item>
            <Descriptions.Item label="最大回撤">{formatPercent(detail.maxDrawdown)}</Descriptions.Item>
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

export default StrategyBacktestList; 