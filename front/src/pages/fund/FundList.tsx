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
  Tooltip
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

const { Search } = Input;
const { Option } = Select;

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
  const [portfolioName, setPortfolioName] = useState('');

  // 加载基金列表
  const loadFunds = async () => {
    setLoading(true);
    try {
      const params: QueryParams = {
        page: current - 1,
        size: pageSize,
        ...(searchKeyword && { keyword: searchKeyword }),
        ...(selectedCompany && { companyId: selectedCompany }),
        ...(selectedManager && { managerId: selectedManager }),
        ...(selectedType && { fundType: selectedType }),
        ...(selectedRiskLevel && { riskLevel: selectedRiskLevel }),
        ...(selectedTags.length > 0 && { tagIds: selectedTags }),
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
      fundList = fundList.map((fund: Fund) => {
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
      setFunds(fundList);
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
    } catch (e) {
      setTags([]);
    }
  };

  // 只在公司和经理都加载完后再加载基金
  useEffect(() => {
    loadCompanies();
    loadManagers();
    loadTags();
    // 临时测试：直接写死一条数据
    setFunds([
      { id: 1, code: '001', name: '测试基金', type: 'STOCK', companyName: '测试公司', managerName: '张三' }
    ]);
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
    setSearchKeyword(value);
    setCurrent(1);
  };

  // 处理筛选
  const handleFilter = (type: string, value: string) => {
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
    try {
      const values = await form.validateFields();
      // 字段映射，保证和后端实体类一致
      const payload = {
        name: values.fundName,
        code: values.fundCode,
        type: values.fundType,
        status: values.status, // 如果表单有 status 字段
        companyId: values.companyId,
        managerId: values.managerId,
        riskLevel: values.riskLevel,
        inceptionDate: values.inceptionDate, // 如有
        fundSize: values.fundSize, // 如有
        nav: values.nav, // 如有
        navDate: values.navDate, // 如有
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
    try {
      await fundApi.delete(id);
      message.success('删除基金成功');
      loadFunds();
    } catch (error) {
      message.error('删除基金失败');
    }
  };

  // 保存为组合
  const handleSavePortfolio = async () => {
    await fundPortfolioApi.create({
      portfolioName,
      funds: selectedRowKeys.map(id => ({ id: Number(id) })) as any,
    });
    setPortfolioModalVisible(false);
    setPortfolioName('');
    setSelectedRowKeys([]);
    message.success('保存组合成功');
  };

  // 表格列定义
  const columns = [
    {
      title: '基金代码',
      dataIndex: 'code',
      key: 'code',
    },
    {
      title: '基金名称',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '基金类型',
      dataIndex: 'type',
      key: 'type',
    },
    {
      title: '基金公司',
      dataIndex: 'companyName',
      key: 'companyName',
      width: 150,
      ellipsis: true,
    },
    {
      title: '基金经理',
      dataIndex: 'managerName',
      key: 'managerName',
      width: 120,
      ellipsis: true,
    },
    {
      title: '基金规模(亿元)',
      dataIndex: 'fundSize',
      key: 'fundSize',
      width: 120,
      render: (size: number) => size ? `${size.toFixed(2)}` : '-',
    },
    {
      title: '最新净值',
      dataIndex: 'nav',
      key: 'nav',
      width: 100,
      render: (nav: number) => nav ? nav.toFixed(4) : '-',
    },
    {
      title: '风险等级',
      dataIndex: 'riskLevel',
      key: 'riskLevel',
      width: 100,
      render: (level: string) => {
        if (!level) return '-';
        const upperLevel = level.toUpperCase();
        const levelMap: Record<string, { text: string; color: string }> = {
          LOW: { text: '低风险', color: 'green' },
          MEDIUM: { text: '中风险', color: 'orange' },
          HIGH: { text: '高风险', color: 'red' },
        };
        const config = levelMap[upperLevel] || { text: level, color: 'default' };
        return <Tag color={config.color}>{config.text}</Tag>;
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      width: 80,
      render: (status: string) => (
        <Tag color={status === 'ACTIVE' ? 'green' : 'red'}>
          {status === 'ACTIVE' ? '启用' : '禁用'}
        </Tag>
      ),
    },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: Fund) => (
        <Space size="middle">
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Button type="link" danger onClick={() => handleDelete(record.id)}>删除</Button>
        </Space>
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
            />
            <Select
              placeholder="选择基金公司"
              allowClear
              style={{ width: 150 }}
              onChange={(value) => handleFilter('company', value)}
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
            >
              {managers.map(manager => (
                <Option key={manager.id} value={manager.id.toString()}>
                  {manager.managerName || manager.name}
                </Option>
              ))}
            </Select>
            <Select
              placeholder="基金类型"
              allowClear
              style={{ width: 120 }}
              onChange={(value) => handleFilter('type', value)}
            >
              <Option value="STOCK">股票型</Option>
              <Option value="BOND">债券型</Option>
              <Option value="HYBRID">混合型</Option>
              <Option value="MONEY">货币型</Option>
            </Select>
            <Select
              placeholder="风险等级"
              allowClear
              style={{ width: 120 }}
              onChange={(value) => handleFilter('riskLevel', value)}
            >
              <Option value="LOW">低风险</Option>
              <Option value="MEDIUM">中风险</Option>
              <Option value="HIGH">高风险</Option>
            </Select>
            <Select
              mode="multiple"
              placeholder="选择标签"
              style={{ width: 200 }}
              value={selectedTags}
              onChange={setSelectedTags}
              allowClear
            >
              {tags.map(tag => (
                <Select.Option key={tag.id} value={tag.id}>{tag.tagName}</Select.Option>
              ))}
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
              onClick={() => setPortfolioModalVisible(true)}
            >
              保存为组合
            </Button>
          </Space>
        </div>

        {/* 表格 */}

        <Table
          columns={columns}
          dataSource={funds}
          rowKey="id"
          loading={loading}
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
          scroll={{ x: 1400, y: 600 }}
          size="middle"
          rowSelection={{
            selectedRowKeys,
            onChange: setSelectedRowKeys,
          }}
        />
        {console.log('渲染时 funds:', funds)}
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
            name="fundType"
            label="基金类型"
            rules={[{ required: true, message: '请选择基金类型' }]}
          >
            <Select placeholder="请选择基金类型">
              <Option value="STOCK">股票型</Option>
              <Option value="BOND">债券型</Option>
              <Option value="HYBRID">混合型</Option>
              <Option value="MONEY">货币型</Option>
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
        </Form>
      </Modal>

      <Modal
        title="保存为基金组合"
        open={portfolioModalVisible}
        onOk={handleSavePortfolio}
        onCancel={() => setPortfolioModalVisible(false)}
      >
        <Input
          placeholder="请输入组合名称"
          value={portfolioName}
          onChange={e => setPortfolioName(e.target.value)}
        />
      </Modal>
    </div>
  );
};

export default FundList; 