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
import { fundApi, fundCompanyApi, fundManagerApi } from '../../api';
import type { Fund, FundCompany, FundManager, QueryParams } from '../../types';

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

  // 加载基金列表
  const loadFunds = async () => {
    setLoading(true);
    try {
      const params: QueryParams = {
        page: current - 1,
        size: pageSize,
        keyword: searchKeyword,
        companyId: selectedCompany,
        managerId: selectedManager,
        fundType: selectedType,
        riskLevel: selectedRiskLevel,
      };
      
      const response = await fundApi.getList(params);
      if (response.data.code === 200) {
        setFunds(response.data.data.content);
        setTotal(response.data.data.totalElements);
      }
    } catch (error) {
      message.error('加载基金列表失败');
    } finally {
      setLoading(false);
    }
  };

  // 加载基金公司列表
  const loadCompanies = async () => {
    try {
      const response = await fundCompanyApi.getAll();
      if (response.data.code === 200) {
        setCompanies(response.data.data);
      }
    } catch (error) {
      message.error('加载基金公司列表失败');
    }
  };

  // 加载基金经理列表
  const loadManagers = async () => {
    try {
      const response = await fundManagerApi.getList();
      if (response.data.code === 200) {
        setManagers(response.data.data.content);
      }
    } catch (error) {
      message.error('加载基金经理列表失败');
    }
  };

  useEffect(() => {
    loadFunds();
    loadCompanies();
    loadManagers();
  }, [current, pageSize, searchKeyword, selectedCompany, selectedManager, selectedType, selectedRiskLevel]);

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
    setCurrent(1);
  };

  // 处理新增/编辑
  const handleEdit = (fund?: Fund) => {
    setEditingFund(fund || null);
    if (fund) {
      form.setFieldsValue(fund);
    } else {
      form.resetFields();
    }
    setModalVisible(true);
  };

  // 处理保存
  const handleSave = async () => {
    try {
      const values = await form.validateFields();
      if (editingFund) {
        await fundApi.update(editingFund.id, values);
        message.success('更新基金成功');
      } else {
        await fundApi.create(values);
        message.success('创建基金成功');
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

  // 表格列定义
  const columns = [
    {
      title: '基金代码',
      dataIndex: 'fundCode',
      key: 'fundCode',
      width: 100,
      fixed: 'left' as const,
    },
    {
      title: '基金名称',
      dataIndex: 'fundName',
      key: 'fundName',
      width: 250,
      ellipsis: true,
    },
    {
      title: '基金类型',
      dataIndex: 'fundType',
      key: 'fundType',
      width: 100,
      render: (type: string) => {
        const typeMap: Record<string, { text: string; color: string }> = {
          STOCK: { text: '股票型', color: 'red' },
          BOND: { text: '债券型', color: 'green' },
          HYBRID: { text: '混合型', color: 'blue' },
          MONEY: { text: '货币型', color: 'orange' },
        };
        const config = typeMap[type] || { text: type, color: 'default' };
        return <Tag color={config.color}>{config.text}</Tag>;
      },
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
      title: '净值日期',
      dataIndex: 'navDate',
      key: 'navDate',
      width: 100,
      render: (date: string) => date ? new Date(date).toLocaleDateString() : '-',
    },
    {
      title: '风险等级',
      dataIndex: 'riskLevel',
      key: 'riskLevel',
      width: 100,
      render: (level: string) => {
        const levelMap: Record<string, { text: string; color: string }> = {
          LOW: { text: '低风险', color: 'green' },
          MEDIUM: { text: '中风险', color: 'orange' },
          HIGH: { text: '高风险', color: 'red' },
        };
        const config = levelMap[level] || { text: level, color: 'default' };
        return <Tag color={config.color}>{config.text}</Tag>;
      },
    },
    {
      title: '成立日期',
      dataIndex: 'inceptionDate',
      key: 'inceptionDate',
      width: 100,
      render: (date: string) => date ? new Date(date).toLocaleDateString() : '-',
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
      width: 200,
      fixed: 'right' as const,
      render: (_: any, record: Fund) => (
        <Space size="middle">
          <Button 
            type="link" 
            size="small"
            onClick={() => navigate(`/fund/detail/${record.id}`)}
          >
            查看
          </Button>
          <Button 
            type="link" 
            size="small"
            onClick={() => handleEdit(record)}
          >
            编辑
          </Button>
          <Button 
            type="link" 
            size="small"
            danger
            onClick={() => handleDelete(record.id)}
          >
            删除
          </Button>
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
                  {company.companyName}
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
                  {manager.managerName}
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
            <Button icon={<ReloadOutlined />} onClick={handleReset}>
              重置
            </Button>
            <Button type="primary" icon={<PlusOutlined />} onClick={() => handleEdit()}>
              新增基金
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
                  {company.companyName}
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
                  {manager.managerName}
                </Option>
              ))}
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default FundList; 