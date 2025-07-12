import React, { useState, useEffect } from 'react';
import { 
  Table, 
  Card, 
  Input, 
  Select, 
  Button, 
  Space, 
  Tag, 
  Modal, 
  Form, 
  message,
  Popconfirm,
  Tooltip,
  InputNumber
} from 'antd';
import { 
  SearchOutlined, 
  PlusOutlined, 
  EditOutlined, 
  DeleteOutlined,
  EyeOutlined,
  ReloadOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import { fundApi, fundCompanyApi, fundManagerApi, fundTagApi, fundPortfolioApi } from '../../api';
import type { Fund, FundCompany, FundManager, QueryParams, FundTag } from '../../types';
import { useTrackEvent } from '../../utils/request';
import { post } from '../../utils/request';

const { Search } = Input;
const { Option, OptGroup } = Select;

type FundWithNames = Fund & { companyName?: string; managerName?: string };

const FundList: React.FC = () => {
  const [funds, setFunds] = useState<Fund[]>([]);
  const [companies, setCompanies] = useState<FundCompany[]>([]);
  const [managers, setManagers] = useState<FundManager[]>([]);
  const [loading, setLoading] = useState(false);
  const [total, setTotal] = useState(0);
  const [current, setCurrent] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [selectedCompany, setSelectedCompany] = useState<string>('');
  const [selectedManager, setSelectedManager] = useState<string>('');
  const [selectedType, setSelectedType] = useState<string>('');
  const [selectedRiskLevel, setSelectedRiskLevel] = useState<string>('');
  const [modalVisible, setModalVisible] = useState(false);
  const [editingFund, setEditingFund] = useState<Fund | null>(null);
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [companiesLoaded, setCompaniesLoaded] = useState(false);
  const [managersLoaded, setManagersLoaded] = useState(false);
  const [tags, setTags] = useState<FundTag[]>([]);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const [portfolioModalVisible, setPortfolioModalVisible] = useState(false);
  const [portfolioForm] = Form.useForm();
  const [styleTags, setStyleTags] = useState<FundTag[]>([]);
  const [themeTags, setThemeTags] = useState<FundTag[]>([]);
  const [sizeTags, setSizeTags] = useState<FundTag[]>([]);
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/funds');
  }, [track]);
  // 新增、编辑、筛选、查看详情等操作可用track('click', '/funds', { buttonId: 'add' })等
  // 导出、批量操作等操作可用track('click', '/funds', { buttonId: 'export' })等

  // 状态颜色映射
  const statusColorMap: Record<string, string> = {
    ACTIVE: 'green',
    INACTIVE: 'red',
    HIGH: 'orange',
    MEDIUM: 'blue',
    LOW: 'gray',
  };

  // 加载基金列表
  const loadFunds = async () => {
    setLoading(true);
    try {
      const params: any = {
        page: current - 1,
        size: pageSize,
        ...(searchKeyword && { keyword: searchKeyword }),
        ...(selectedCompany && { companyId: selectedCompany }),
        ...(selectedManager && { managerId: selectedManager }),
        ...(selectedTags.length > 0 && { tagIds: selectedTags }),
        ...(selectedRiskLevel && { riskLevel: selectedRiskLevel }),
      };
      const response = await fundApi.getList(params);
      console.log('基金接口返回', response);
      let fundList: any[] = [];
      if (response.data.data) {
        if (Array.isArray(response.data.data)) {
          fundList = response.data.data;
        } else if (response.data.data && Array.isArray((response.data.data as any).content)) {
          fundList = (response.data.data as any).content;
        }
      }
      fundList = fundList.map((fund: FundWithNames) => {
        const riskLevel = fund.riskLevel || fund.status || '';
        return {
          ...fund,
          companyName: companies.find(c => c.id === fund.companyId)?.companyName
            || companies.find(c => c.id === fund.companyId)?.name
            || '',
          managerName: managers.find(m => m.id === fund.managerId)?.managerName
            || managers.find(m => m.id === fund.managerId)?.name
            || '',
          riskLevel,
        };
      });
      console.log('最终传给表格的数据 funds:', fundList);
      setFunds(fundList as any[]);
      setTotal(response.data.data.totalElements || fundList.length || 0);
    } catch (error) {
      message.error('加载基金列表失败');
      setFunds([]);
    } finally {
      setLoading(false);
    }
  };

  // 加载基金公司列表
  const loadCompanies = async () => {
    try {
      const response = await fundCompanyApi.getAll();
      console.log('公司接口返回', response);
      if (Array.isArray(response.data.data)) {
        setCompanies(response.data.data);
      } else if (response.data.data && Array.isArray((response.data.data as any).content)) {
        setCompanies((response.data.data as any).content);
      } else {
        setCompanies([]);
      }
    } catch (error) {
      message.error('加载基金公司列表失败');
      setCompanies([]);
    } finally {
      setCompaniesLoaded(true);
    }
  };

  // 加载基金经理列表
  const loadManagers = async () => {
    try {
      const response = await fundManagerApi.getList();
      console.log('经理接口返回', response);
      if (Array.isArray(response.data.data)) {
        setManagers(response.data.data);
      } else if (response.data.data && Array.isArray(response.data.data.content)) {
        setManagers(response.data.data.content);
      } else {
        setManagers([]);
      }
    } catch (error) {
      message.error('加载基金经理列表失败');
      setManagers([]);
    } finally {
      setManagersLoaded(true);
    }
  };

  // 加载标签
  const loadTags = async () => {
    try {
      const res = await fundTagApi.getAll();
      setTags(res.data.data);
      setStyleTags(res.data.data.filter((tag: FundTag) => tag.tagCategory === 'STYLE'));
      setThemeTags(res.data.data.filter((tag: FundTag) => tag.tagCategory === 'THEME'));
      setSizeTags(res.data.data.filter((tag: FundTag) => tag.tagCategory === 'SIZE'));
    } catch (e) {
      setTags([]);
      setStyleTags([]);
      setThemeTags([]);
      setSizeTags([]);
    }
  };

  // 只在公司和经理都加载完后再加载基金
  useEffect(() => {
    loadCompanies();
    loadManagers();
    loadTags();
    // 临时测试：直接写死一条数据
    setFunds([
      { id: 1, code: '001', name: '测试基金', type: 'STOCK' } as Fund ]);
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if (companiesLoaded && managersLoaded) {
      loadFunds();
    }
    // eslint-disable-next-line
  }, [companiesLoaded, managersLoaded, current, pageSize, searchKeyword, selectedCompany, selectedManager, selectedType, selectedRiskLevel, selectedTags]);

  // 处理搜索
  const handleSearch = (value: string) => {
    track('click', '/funds', { buttonId: 'search', keyword: value });
    setSearchKeyword(value);
    setCurrent(1);
  };

  // 处理筛选
  const handleFilter = (type: string, value: string) => {
    track('click', '/funds', { buttonId: 'filter', filterType: type, filterValue: value });
    switch (type) {
      case 'company':
        setSelectedCompany(value);
        break;
      case 'manager':
        setSelectedManager(value);
        break;
      case 'type':
        setSelectedType(value);
        break;
      case 'riskLevel':
        setSelectedRiskLevel(value);
        break;
    }
    setCurrent(1);
  };

  // 重置筛选
  const handleReset = () => {
    track('click', '/funds', { buttonId: 'reset' });
    setSearchKeyword('');
    setSelectedCompany('');
    setSelectedManager('');
    setSelectedType('');
    setSelectedRiskLevel('');
    setSelectedTags([]);
    setCurrent(1);
  };

  // 处理新增/编辑
  const handleEdit = (fund?: Fund) => {
    track('click', '/funds', { buttonId: fund ? 'edit' : 'add', fundId: fund?.id });
    setEditingFund(fund || null);
    if (fund) {
      form.setFieldsValue({
        fundName: fund.name,
        fundCode: fund.code,
        fundType: fund.type,
        status: fund.status,
        companyId: fund.companyId,
        managerId: fund.managerId,
        riskLevel: fund.riskLevel,
        inceptionDate: fund.inceptionDate,
        fundSize: fund.fundSize,
        nav: fund.nav,
        navDate: fund.navDate,
        // 其它字段按需补充
      });
    } else {
      form.resetFields();
    }
    setModalVisible(true);
  };

  // 处理保存
  const handleSave = async () => {
    track('click', '/funds', { buttonId: 'save', fundId: editingFund?.id });
    try {
      const values = await form.validateFields();
      // 字段映射，保证和后端实体类一致
      const payload = {
        fundCode: values.fundCode,
        fundName: values.fundName,
        fundType: values.fundType, // 如有
        status: values.status, // 如有
        companyId: values.companyId,
        managerId: values.managerId,
        riskLevel: values.riskLevel,
        inceptionDate: values.inceptionDate, // 如有
        fundSize: values.fundSize, // 如有
        nav: values.nav, // 如有
        navDate: values.navDate, // 如有
        investmentStrategy: values.investmentStrategy, // 如有
        benchmark: values.benchmark, // 如有
        tags: (values.tags || []).map((id: number) => ({ id })), // 关键修正
        // 其它字段按需补充
      };
      if (editingFund) {
        await fundApi.update(editingFund.id, payload);
        message.success('更新基金成功');
      } else {
        const res = await fundApi.create(payload);
        console.log('新增返回', res);
        message.success('创建基金成功');
        setCurrent(1);
      }
      setModalVisible(false);
      loadFunds();
    } catch (error) {
      message.error('保存失败');
    }
  };

  // 处理删除
  const handleDelete = async (id: number) => {
    track('click', '/funds', { buttonId: 'delete', fundId: id });
    try {
      await fundApi.delete(id);
      message.success('删除基金成功');
      loadFunds();
    } catch (error) {
      message.error('删除基金失败');
    }
  };

  // 保存为组合
  const handleSavePortfolio = () => {
    if (selectedRowKeys.length === 0) {
      message.warning('请先选择要保存的基金');
      return;
    }
    setPortfolioModalVisible(true);
  };

  const handlePortfolioFormOk = async () => {
    try {
      const values = await portfolioForm.validateFields();
      // 1. 创建组合
      const res = await fundPortfolioApi.create({
        portfolioName: values.portfolioName,
        portfolioType: values.portfolioType,
        riskLevel: values.riskLevel,
        targetReturn: values.targetReturn,
        maxDrawdown: values.maxDrawdown,
        investmentHorizon: values.investmentHorizon,
        minInvestment: values.minInvestment,
        description: values.description,
      });
      // 兼容 axios 返回结构
      const resData = res.data || {};
      if (resData.code !== 0) {
        message.error(resData.message || '创建组合失败');
        return;
      }
      const portfolioId = resData.data?.id;
      if (!portfolioId) {
        message.error('未获取到新建组合ID');
        return;
      }
      // 2. 批量保存组合-基金关联
      const relRes = await post('/fund-portfolio-relations/batch', {
        portfolioId,
        fundIds: selectedRowKeys.map(fundId => Number(fundId)),
      });
      const relResData = relRes.data || {};
      if (relResData.code !== 0) {
        message.error(relResData.message || '保存组合基金关联失败');
        return;
      }
      message.success('保存成功');
      setPortfolioModalVisible(false);
      portfolioForm.resetFields();
    } catch (e: any) {
      message.error(e?.message || '保存失败，请重试');
    }
  };

  // 选类型时
  const handleTypeChange = (typeId: number | undefined) => {
    setSelectedType(typeId ? String(typeId) : '');
    setSelectedTags(prev => {
      // 移除已有风格类标签，只保留一个
      const nonStyle = prev.filter(id => !styleTags.some(tag => tag.id === id));
      return typeId ? [...nonStyle, typeId] : nonStyle;
    });
    setCurrent(1);
  };

  // 选标签时
  const handleTagsChange = (tagIds: number[]) => {
    // 只允许非风格类标签多选
    const styleSelected = selectedTags.find(id => styleTags.some(tag => tag.id === id));
    setSelectedTags([...(styleSelected ? [styleSelected] : []), ...tagIds.filter(id => !styleTags.some(tag => tag.id === id))]);
    setCurrent(1);
  };

  // 表格列定义
  const columns = [
    { title: '基金ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: '基金代码', dataIndex: 'fundCode', key: 'fundCode', width: 120 },
    { title: '基金名称', dataIndex: 'fundName', key: 'fundName', width: 180 },
    { title: '公司名称', dataIndex: 'companyName', key: 'companyName', width: 180 },
    { title: '经理名称', dataIndex: 'managerName', key: 'managerName', width: 120 },
    { title: '成立日期', dataIndex: 'inceptionDate', key: 'inceptionDate', width: 120 },
    { title: '基金规模(亿元)', dataIndex: 'fundSize', key: 'fundSize', width: 120 },
    { title: '最新净值', dataIndex: 'nav', key: 'nav', width: 100 },
    { title: '净值日期', dataIndex: 'navDate', key: 'navDate', width: 120 },
    {
      title: '风险等级',
      dataIndex: 'riskLevel',
      key: 'riskLevel',
      render: (riskLevel: string) => (
        <Tag color={statusColorMap[riskLevel] || 'default'}>
          {riskLevel === 'HIGH' ? '高' : riskLevel === 'MEDIUM' ? '中' : riskLevel === 'LOW' ? '低' : riskLevel}
        </Tag>
      ),
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={statusColorMap[status] || 'default'}>
          {status === 'ACTIVE' ? '正常' : status === 'INACTIVE' ? '停用' : status}
        </Tag>
      ),
    },
    { title: '投资策略', dataIndex: 'investmentStrategy', key: 'investmentStrategy', width: 180 },
    { title: '业绩基准', dataIndex: 'benchmark', key: 'benchmark', width: 120 },
    { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt', width: 180 },
    { title: '更新时间', dataIndex: 'updatedAt', key: 'updatedAt', width: 180 },
    {
      title: '标签',
      dataIndex: 'tags',
      key: 'tags',
      render: (tags: FundTag[]) => (
        <>
          {tags?.map(tag => (
            <Tag key={tag.id} color={tag.tagColor || 'blue'}>
              {tag.tagName}
            </Tag>
          ))}
        </>
      ),
    },
  ];

  return (
    <div>
      <Card title="基金列表">
        {/* 搜索和筛选区域 */}
        <div style={{ marginBottom: 16 }}>
          <Space wrap>
            <Search
              placeholder="搜索基金代码或名称"
              allowClear
              enterButton={<SearchOutlined />}
              onSearch={handleSearch}
              style={{ width: 300 }}
              value={searchKeyword}
              onChange={e => setSearchKeyword(e.target.value)}
            />
            <Select
              placeholder="选择基金公司"
              allowClear
              style={{ width: 150 }}
              onChange={(value) => handleFilter('company', value)}
              value={selectedCompany || undefined}
            >
              {companies.map(company => (
                <Option key={company.id} value={company.id.toString()}>
                  {company.companyName || company.name}
                </Option>
              ))}
            </Select>
            <Select
              placeholder="选择基金经理"
              allowClear
              style={{ width: 150 }}
              onChange={(value) => handleFilter('manager', value)}
              value={selectedManager || undefined}
            >
              {managers.map(manager => (
                <Option key={manager.id} value={manager.id.toString()}>
                  {manager.managerName || manager.name}
                </Option>
              ))}
            </Select>
            <Select
              placeholder="选择基金类型"
              allowClear
              style={{ width: 150 }}
              onChange={handleTypeChange}
              value={selectedTags.find(id => styleTags.some(tag => tag.id === id)) || undefined}
            >
              {styleTags.map(tag => (
                <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
              ))}
            </Select>
            <Select
              placeholder="风险等级"
              allowClear
              style={{ width: 120 }}
              onChange={(value) => handleFilter('riskLevel', value)}
              value={selectedRiskLevel || undefined}
            >
              <Option value="LOW">低风险</Option>
              <Option value="MEDIUM">中风险</Option>
              <Option value="HIGH">高风险</Option>
            </Select>
            <Select
              mode="multiple"
              placeholder="选择标签"
              style={{ width: 200 }}
              value={selectedTags.filter(id => !styleTags.some(tag => tag.id === id))}
              onChange={handleTagsChange}
              allowClear
            >
              <OptGroup label="主题">
                {themeTags.map(tag => (
                  <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
                ))}
              </OptGroup>
              <OptGroup label="规模">
                {sizeTags.map(tag => (
                  <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
                ))}
              </OptGroup>
            </Select>
            <Button icon={<ReloadOutlined />} onClick={handleReset}>
              重置
            </Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => handleEdit()}>
              新增基金
            </Button>
            <Button
              type="primary"
              disabled={selectedRowKeys.length === 0}
              onClick={handleSavePortfolio}
            >
              保存为组合
            </Button>
          </Space>
        </div>

        {/* 表格 */}

        <Table
          bordered
          columns={columns}
          dataSource={funds}
          rowKey="id"
          loading={loading}
          scroll={{ x: 'max-content' }}
          rowClassName={(_, idx) => (idx % 2 === 0 ? 'table-row-light' : 'table-row-dark')}
          pagination={{
            current,
            pageSize,
            total,
            showSizeChanger: true,
            showQuickJumper: true,
            showTotal: (total, range) => `第 ${range[0]}-${range[1]} 条/共 ${total} 条`,
            onChange: (page, size) => {
              setCurrent(page);
              setPageSize(size || 10);
            },
          }}
          size="middle"
          rowSelection={{
            selectedRowKeys,
            onChange: setSelectedRowKeys,
          }}
        />
      </Card>

      {/* 新增/编辑模态框 */}
      <Modal
        title={editingFund ? '编辑基金' : '新增基金'}
        open={modalVisible}
        onOk={handleSave}
        onCancel={() => setModalVisible(false)}
        width={600}
      >
        <Form
          form={form}
          layout="vertical"
        >
          <Form.Item
            name="fundCode"
            label="基金代码"
            rules={[{ required: true, message: '请输入基金代码' }]}
          >
            <Input placeholder="请输入基金代码" />
          </Form.Item>
          <Form.Item
            name="fundName"
            label="基金名称"
            rules={[{ required: true, message: '请输入基金名称' }]}
          >
            <Input placeholder="请输入基金名称" />
          </Form.Item>
          <Form.Item
            name="typeTag"
            label="基金类型(风格)"
          >
            <Select placeholder="请选择基金类型">
              {styleTags.map(tag => (
                <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="riskLevel"
            label="风险等级"
            rules={[{ required: true, message: '请选择风险等级' }]}
          >
            <Select placeholder="请选择风险等级">
              <Option value="LOW">低风险</Option>
              <Option value="MEDIUM">中风险</Option>
              <Option value="HIGH">高风险</Option>
            </Select>
          </Form.Item>
          <Form.Item
            name="companyId"
            label="基金公司"
            rules={[{ required: true, message: '请选择基金公司' }]}
          >
            <Select placeholder="请选择基金公司">
              {companies.map(company => (
                <Option key={company.id} value={company.id}>
                  {company.companyName || company.name}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="managerId"
            label="基金经理"
          >
            <Select placeholder="请选择基金经理" allowClear>
              {managers.map(manager => (
                <Option key={manager.id} value={manager.id}>
                  {manager.managerName || manager.name}
                </Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item
            name="tags"
            label="基金标签"
          >
            <Select
              mode="multiple"
              placeholder="请选择标签"
              allowClear
            >
              <OptGroup label="风格">
                {styleTags.map(tag => (
                  <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
                ))}
              </OptGroup>
              <OptGroup label="主题">
                {themeTags.map(tag => (
                  <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
                ))}
              </OptGroup>
              <OptGroup label="规模">
                {sizeTags.map(tag => (
                  <Option key={tag.id} value={tag.id}>{tag.tagName}</Option>
                ))}
              </OptGroup>
            </Select>
          </Form.Item>
        </Form>
      </Modal>

      {/* 新建组合表单Modal */}
      <Modal
        title="新建基金组合"
        open={portfolioModalVisible}
        onOk={handlePortfolioFormOk}
        onCancel={() => setPortfolioModalVisible(false)}
        destroyOnClose
      >
        <Form form={portfolioForm} layout="vertical">
          <Form.Item name="portfolioName" label="组合名称" rules={[{ required: true, message: '请输入组合名称' }]}>
            <Input placeholder="请输入组合名称" />
          </Form.Item>
          <Form.Item name="portfolioType" label="组合类型">
            <Input placeholder="如：自定义/指数/平衡/激进等" />
          </Form.Item>
          <Form.Item name="riskLevel" label="风险等级">
            <Select options={[{ value: '高风险', label: '高风险' }, { value: '中风险', label: '中风险' }, { value: '低风险', label: '低风险' }]} allowClear />
          </Form.Item>
          <Form.Item name="targetReturn" label="目标收益率">
            <InputNumber min={0} max={100} addonAfter="%" style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="maxDrawdown" label="最大回撤">
            <InputNumber min={0} max={100} addonAfter="%" style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="investmentHorizon" label="投资期限">
            <Input placeholder="如：1年/2年/3年" />
          </Form.Item>
          <Form.Item name="minInvestment" label="最小投资金额">
            <InputNumber min={0} style={{ width: '100%' }} addonAfter="元" />
          </Form.Item>
          <Form.Item name="description" label="描述">
            <Input.TextArea rows={2} placeholder="可选，补充说明" />
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default FundList; 