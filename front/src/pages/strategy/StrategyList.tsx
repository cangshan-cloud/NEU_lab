import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchStrategyList, deleteStrategy } from '../../api/strategy';
import type { StrategyVO } from '../../api/strategy';
import { Card, Table, message, Tag, Button, Input, Select, Pagination, Space, Row, Col, Form } from 'antd';
import { useTrackEvent } from '../../utils/request';

const { Option } = Select;

const STRATEGY_TYPE_OPTIONS = [
  { label: '全部', value: '' },
  { label: '价值', value: 'VALUE' },
  { label: '成长', value: 'GROWTH' },
  { label: '风险', value: 'RISK' },
  { label: '均衡', value: 'BALANCED' },
  { label: '动量', value: 'MOMENTUM' },
];
const RISK_LEVEL_OPTIONS = [
  { label: '全部', value: '' },
  { label: '低', value: 'LOW' },
  { label: '中', value: 'MEDIUM' },
  { label: '高', value: 'HIGH' },
];
const STATUS_OPTIONS = [
  { label: '全部', value: '' },
  { label: '启用', value: 'ACTIVE' },
  { label: '禁用', value: 'INACTIVE' },
];

const StrategyList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/strategies');
  }, [track]);
  // 新增、编辑、筛选、导出、查看详情、回测等操作可用track('click', '/strategies', { buttonId: 'add' })等
  const [data, setData] = useState<StrategyVO[]>([]);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  // 移除筛选按钮和表单，改为受控组件+自动请求
  const [keyword, setKeyword] = useState('');
  const [type, setType] = useState('');
  const [riskLevel, setRiskLevel] = useState('');
  const [status, setStatus] = useState('');
  const navigate = useNavigate();
  const [filterForm] = Form.useForm();

  // 只在page/size变化时请求数据
  useEffect(() => {
    fetchData();
    // eslint-disable-next-line
  }, [page, size, keyword, type, riskLevel, status]);

  // 只要筛选项或搜索项变化，重置page并请求数据
  // useEffect(() => {
  //   setPage(1);
  //   fetchData(1);
  //   // eslint-disable-next-line
  // }, [keyword, type, riskLevel, status]);

  const handleKeywordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setKeyword(e.target.value);
    setPage(1);
  };
  const handleTypeChange = (v: string) => {
    setType(v);
    setPage(1);
  };
  const handleRiskLevelChange = (v: string) => {
    setRiskLevel(v);
    setPage(1);
  };
  const handleStatusChange = (v: string) => {
    setStatus(v);
    setPage(1);
  };

  // fetchData支持传入页码
  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await fetchStrategyList({
        type,
        riskLevel,
        status,
        keyword,
        page: page - 1,
        size
      });
      const pageData = res.data.data;
      setData(pageData.content);
      setTotal(pageData.totalElements);
    } catch (e) {
      message.error('获取策略列表失败');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    await deleteStrategy(id);
    message.success('删除成功');
    fetchData();
  };

  const riskLevelColorMap: Record<string, string> = {
    'LOW': 'green',
    'MEDIUM': 'orange',
    'HIGH': 'red',
  };
  const riskLevelTextMap: Record<string, string> = {
    'LOW': '低',
    'MEDIUM': '中',
    'HIGH': '高',
  };
  const strategyTypeTextMap: Record<string, string> = {
    'VALUE': '价值',
    'GROWTH': '成长',
    'RISK': '风险',
    'BALANCED': '均衡',
    'MOMENTUM': '动量',
  };

  const columns = [
    { title: '策略代码', dataIndex: 'strategyCode', key: 'strategyCode' },
    { title: '策略名称', dataIndex: 'strategyName', key: 'strategyName' },
    { title: '类型', dataIndex: 'strategyType', key: 'strategyType', render: (v: string) => strategyTypeTextMap[v] || v },
    { title: '风险等级', dataIndex: 'riskLevel', key: 'riskLevel', render: (v: string) => <Tag color={riskLevelColorMap[v] || 'default'}>{riskLevelTextMap[v] || v}</Tag> },
    { title: '状态', dataIndex: 'status', key: 'status', render: (v: string) => v === 'ACTIVE' ? <Tag color="green">启用</Tag> : <Tag color="red">禁用</Tag> },
    { title: '操作', dataIndex: 'action', key: 'action', render: (_: any, record: StrategyVO) => (
      <Space>
        <Button type="link" onClick={() => navigate(`/strategies/${record.id}`)}>详情</Button>
        <Button type="link" onClick={() => navigate(`/strategies/edit/${record.id}`)}>编辑</Button>
        <Button type="link" onClick={() => navigate(`/strategies/backtest/${record.id}`)}>回测</Button>
        <Button type="link" onClick={() => navigate(`/strategies/backtest-list/${record.id}`)}>回测历史</Button>
        <Button type="link" danger onClick={() => handleDelete(record.id)}>删除</Button>
      </Space>
    ) },
  ];

  // 美化筛选区，参考产品列表
  return (
    <Card title="策略管理" extra={<Button type="primary" onClick={() => navigate('/strategies/create')}>新建策略</Button>}>
      <Space style={{ marginBottom: 16 }}>
        <Input.Search
          placeholder="代码/名称/描述"
          value={keyword}
          onChange={handleKeywordChange}
          allowClear
          style={{ width: 200 }}
        />
        <Select
          placeholder="类型"
          value={type}
          onChange={handleTypeChange}
          style={{ width: 120 }}
          allowClear
        >
          {STRATEGY_TYPE_OPTIONS.map(opt => <Option key={opt.value} value={opt.value}>{opt.label}</Option>)}
        </Select>
        <Select
          placeholder="风险等级"
          value={riskLevel}
          onChange={handleRiskLevelChange}
          style={{ width: 120 }}
          allowClear
        >
          {RISK_LEVEL_OPTIONS.map(opt => <Option key={opt.value} value={opt.value}>{opt.label}</Option>)}
        </Select>
        <Select
          placeholder="状态"
          value={status}
          onChange={handleStatusChange}
          style={{ width: 120 }}
          allowClear
        >
          {STATUS_OPTIONS.map(opt => <Option key={opt.value} value={opt.value}>{opt.label}</Option>)}
        </Select>
      </Space>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={data}
        loading={loading}
        pagination={false}
        scroll={{ x: 'max-content' }}
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
    </Card>
  );
};

export default StrategyList; 