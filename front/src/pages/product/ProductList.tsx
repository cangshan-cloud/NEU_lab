import React, { useEffect, useState } from 'react';
import { Card, Table, message, Tag, Button, Modal, Form, Input, InputNumber, Select, Popconfirm, Row, Col, Space, Divider } from 'antd';
import { productApi } from '../../api/product';
import type { Product, ProductVO } from '../../types';

const { Option } = Select;
const { TextArea } = Input;

const ProductList: React.FC = () => {
  const [data, setData] = useState<Product[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [form] = Form.useForm();
  const [filterForm] = Form.useForm();
  const [showAdvancedFilter, setShowAdvancedFilter] = useState(false);
  const localUser = localStorage.getItem('user');
  const user = localUser ? JSON.parse(localUser) : null;
  const isAdmin = !!user && Number(user.roleId || user.role_id) === 1;
  const isUser = !!user && Number(user.roleId || user.role_id) === 2;

  const statusColorMap: Record<string, string> = {
    ACTIVE: 'green',
    INACTIVE: 'red',
  };

  const productTypeOptions = [
    { label: '股票型', value: 'STOCK' },
    { label: '债券型', value: 'BOND' },
    { label: '混合型', value: 'HYBRID' },
    { label: '货币型', value: 'MONEY' },
    { label: '指数型', value: 'INDEX' },
    { label: 'FOF型', value: 'FOF' },
    { label: 'QDII型', value: 'QDII' },
  ];

  const riskLevelOptions = [
    { label: '低风险', value: 'LOW' },
    { label: '中低风险', value: 'MEDIUM_LOW' },
    { label: '中风险', value: 'MEDIUM' },
    { label: '中高风险', value: 'MEDIUM_HIGH' },
    { label: '高风险', value: 'HIGH' },
  ];

  const investmentHorizonOptions = [
    { label: '短期(1年以内)', value: 'SHORT' },
    { label: '中期(1-3年)', value: 'MEDIUM' },
    { label: '长期(3年以上)', value: 'LONG' },
  ];

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async (filters?: any) => {
    try {
      setLoading(true);
      // 优先使用VO接口
      const response = await productApi.getAllVOs();
      let arr: Product[] = [];
      if (Array.isArray(response.data.data)) {
        // 将VO数据转换为Product格式
        arr = response.data.data.map((vo: ProductVO) => ({
          id: vo.id,
          productCode: vo.productCode,
          productName: vo.productName,
          productType: vo.productType,
          strategyId: vo.strategyId,
          portfolioId: vo.portfolioId,
          riskLevel: vo.riskLevel,
          targetReturn: vo.targetReturn,
          maxDrawdown: vo.maxDrawdown,
          investmentHorizon: vo.investmentHorizon,
          minInvestment: vo.minInvestment,
          maxInvestment: vo.maxInvestment,
          managementFee: vo.managementFee,
          performanceFee: vo.performanceFee,
          subscriptionFee: vo.subscriptionFee,
          redemptionFee: vo.redemptionFee,
          description: vo.description,
          prospectus: vo.prospectus,
          status: vo.status,
          createdAt: vo.createdAt,
          updatedAt: vo.updatedAt,
        }));
      } else if (
        response.data.data &&
        typeof response.data.data === 'object' &&
        'content' in response.data.data &&
        Array.isArray((response.data.data as any).content)
      ) {
        arr = (response.data.data as any).content;
      } else arr = [];
      
      // 应用筛选条件
      if (filters) {
        arr = applyFilters(arr, filters);
      }
      
      setData(arr);
    } catch (error) {
      // 如果VO接口失败，回退到普通接口
      try {
        const response = await productApi.getAll();
        let arr: Product[] = [];
        if (Array.isArray(response.data.data)) arr = response.data.data;
        else if (
          response.data.data &&
          typeof response.data.data === 'object' &&
          'content' in response.data.data &&
          Array.isArray((response.data.data as any).content)
        ) {
          arr = (response.data.data as any).content;
        } else arr = [];
        
        // 应用筛选条件
        if (filters) {
          arr = applyFilters(arr, filters);
        }
        
        setData(arr);
      } catch (fallbackError) {
        message.error('获取产品列表失败');
        setData([]);
      }
    } finally {
      setLoading(false);
    }
  };

  // 应用筛选条件
  const applyFilters = (products: Product[], filters: any) => {
    return products.filter(product => {
      // 产品类型筛选
      if (filters.productType && product.productType !== filters.productType) {
        return false;
      }
      
      // 风险等级筛选
      if (filters.riskLevel && product.riskLevel !== filters.riskLevel) {
        return false;
      }
      
      // 投资期限筛选
      if (filters.investmentHorizon && product.investmentHorizon !== filters.investmentHorizon) {
        return false;
      }
      
      // 目标收益率范围筛选
      if (filters.targetReturnRange && product.targetReturn) {
        const [min, max] = filters.targetReturnRange;
        if (product.targetReturn < min || product.targetReturn > max) {
          return false;
        }
      }
      
      // 最大回撤范围筛选
      if (filters.maxDrawdownRange && product.maxDrawdown) {
        const [min, max] = filters.maxDrawdownRange;
        if (product.maxDrawdown < min || product.maxDrawdown > max) {
          return false;
        }
      }
      
      // 最小投资金额范围筛选
      if (filters.minInvestmentRange && product.minInvestment) {
        const [min, max] = filters.minInvestmentRange;
        if (product.minInvestment < min || product.minInvestment > max) {
          return false;
        }
      }
      
      // 管理费率范围筛选
      if (filters.managementFeeRange && product.managementFee) {
        const [min, max] = filters.managementFeeRange;
        if (product.managementFee < min || product.managementFee > max) {
          return false;
        }
      }
      
      // 状态筛选
      if (filters.status && product.status !== filters.status) {
        return false;
      }
      
      // 关键词搜索
      if (filters.keyword) {
        const keyword = filters.keyword.toLowerCase();
        const searchFields = [
          product.productCode,
          product.productName,
          product.description
        ].filter(Boolean);
        
        const hasMatch = searchFields.some(field => 
          field && field.toLowerCase().includes(keyword)
        );
        
        if (!hasMatch) {
          return false;
        }
      }
      
      return true;
    });
  };

  const handleAdd = () => {
    setEditingProduct(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record: Product) => {
    setEditingProduct(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await productApi.delete(id);
      message.success('删除成功');
      fetchData();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      if (editingProduct) {
        await productApi.update(editingProduct.id, values);
        message.success('编辑成功');
      } else {
        await productApi.create(values);
        message.success('新增成功');
      }
      setModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  // 筛选处理
  const handleFilter = async () => {
    try {
      const values = await filterForm.validateFields();
      await fetchData(values);
      message.success('筛选完成');
    } catch (e) {
      // 校验失败
    }
  };

  // 重置筛选
  const handleResetFilter = () => {
    filterForm.resetFields();
    fetchData();
    message.success('筛选条件已重置');
  };

  const handleSubmitReview = async (productId: number) => {
    try {
      await productApi.submitReview(productId);
      message.success('提交审核申请成功');
    } catch (e) {
      message.error('提交审核失败');
    }
  };

  const columns = [
    { title: '产品ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: '产品代码', dataIndex: 'productCode', key: 'productCode' },
    { title: '产品名称', dataIndex: 'productName', key: 'productName' },
    { 
      title: '产品类型', 
      dataIndex: 'productType', 
      key: 'productType',
      render: (type: string) => {
        const option = productTypeOptions.find(opt => opt.value === type);
        return option ? option.label : type;
      }
    },
    { 
      title: '风险等级', 
      dataIndex: 'riskLevel', 
      key: 'riskLevel',
      render: (level: string) => {
        const option = riskLevelOptions.find(opt => opt.value === level);
        return option ? option.label : level;
      }
    },
    { 
      title: '目标收益率', 
      dataIndex: 'targetReturn', 
      key: 'targetReturn',
      render: (value: number) => value ? `${value}%` : '-'
    },
    { 
      title: '最大回撤', 
      dataIndex: 'maxDrawdown', 
      key: 'maxDrawdown',
      render: (value: number) => value ? `${value}%` : '-'
    },
    { 
      title: '投资期限', 
      dataIndex: 'investmentHorizon', 
      key: 'investmentHorizon',
      render: (horizon: string) => {
        const option = investmentHorizonOptions.find(opt => opt.value === horizon);
        return option ? option.label : horizon;
      }
    },
    { 
      title: '最小投资金额', 
      dataIndex: 'minInvestment', 
      key: 'minInvestment',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-'
    },
    { 
      title: '最大投资金额', 
      dataIndex: 'maxInvestment', 
      key: 'maxInvestment',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-'
    },
    { 
      title: '管理费率', 
      dataIndex: 'managementFee', 
      key: 'managementFee',
      render: (value: number) => value ? `${value}%` : '-'
    },
    { 
      title: '业绩费率', 
      dataIndex: 'performanceFee', 
      key: 'performanceFee',
      render: (value: number) => value ? `${value}%` : '-'
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
    { title: '创建时间', dataIndex: 'createdAt', key: 'createdAt' },
    { title: '更新时间', dataIndex: 'updatedAt', key: 'updatedAt' },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: Product) => (
        <>
          {isUser && (
            <Button type="link" onClick={() => handleSubmitReview(record.id)}>提交审核</Button>
          )}
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Popconfirm title="确定要删除该产品吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <Card title="产品管理" extra={<Button type="primary" onClick={handleAdd}>新增产品</Button>}>
      {/* 高级筛选区域 */}
      <Card size="small" style={{ marginBottom: 16 }}>
        <Form form={filterForm} layout="horizontal">
          <Row gutter={16}>
            <Col span={6}>
              <Form.Item label="关键词搜索" name="keyword">
                <Input placeholder="产品代码/名称/描述" allowClear />
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item label="产品类型" name="productType">
                <Select placeholder="请选择产品类型" allowClear>
                  {productTypeOptions.map(option => (
                    <Option key={option.value} value={option.value}>{option.label}</Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item label="风险等级" name="riskLevel">
                <Select placeholder="请选择风险等级" allowClear>
                  {riskLevelOptions.map(option => (
                    <Option key={option.value} value={option.value}>{option.label}</Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
            <Col span={6}>
              <Form.Item label="投资期限" name="investmentHorizon">
                <Select placeholder="请选择投资期限" allowClear>
                  {investmentHorizonOptions.map(option => (
                    <Option key={option.value} value={option.value}>{option.label}</Option>
                  ))}
                </Select>
              </Form.Item>
            </Col>
          </Row>
          
          {showAdvancedFilter && (
            <>
              <Divider />
              <Row gutter={16}>
                <Col span={6}>
                  <Form.Item label="目标收益率范围(%)" name="targetReturnRange">
                    <Input.Group compact>
                      <Form.Item name={['targetReturnRange', 0]} noStyle>
                        <InputNumber placeholder="最小值" style={{ width: '50%' }} />
                      </Form.Item>
                      <Form.Item name={['targetReturnRange', 1]} noStyle>
                        <InputNumber placeholder="最大值" style={{ width: '50%' }} />
                      </Form.Item>
                    </Input.Group>
                  </Form.Item>
                </Col>
                <Col span={6}>
                  <Form.Item label="最大回撤范围(%)" name="maxDrawdownRange">
                    <Input.Group compact>
                      <Form.Item name={['maxDrawdownRange', 0]} noStyle>
                        <InputNumber placeholder="最小值" style={{ width: '50%' }} />
                      </Form.Item>
                      <Form.Item name={['maxDrawdownRange', 1]} noStyle>
                        <InputNumber placeholder="最大值" style={{ width: '50%' }} />
                      </Form.Item>
                    </Input.Group>
                  </Form.Item>
                </Col>
                <Col span={6}>
                  <Form.Item label="最小投资金额范围" name="minInvestmentRange">
                    <Input.Group compact>
                      <Form.Item name={['minInvestmentRange', 0]} noStyle>
                        <InputNumber placeholder="最小值" style={{ width: '50%' }} />
                      </Form.Item>
                      <Form.Item name={['minInvestmentRange', 1]} noStyle>
                        <InputNumber placeholder="最大值" style={{ width: '50%' }} />
                      </Form.Item>
                    </Input.Group>
                  </Form.Item>
                </Col>
                <Col span={6}>
                  <Form.Item label="管理费率范围(%)" name="managementFeeRange">
                    <Input.Group compact>
                      <Form.Item name={['managementFeeRange', 0]} noStyle>
                        <InputNumber placeholder="最小值" style={{ width: '50%' }} />
                      </Form.Item>
                      <Form.Item name={['managementFeeRange', 1]} noStyle>
                        <InputNumber placeholder="最大值" style={{ width: '50%' }} />
                      </Form.Item>
                    </Input.Group>
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={6}>
                  <Form.Item label="状态" name="status">
                    <Select placeholder="请选择状态" allowClear>
                      <Option value="ACTIVE">正常</Option>
                      <Option value="INACTIVE">停用</Option>
                    </Select>
                  </Form.Item>
                </Col>
              </Row>
            </>
          )}
          
          <Row>
            <Col span={24}>
              <Space>
                <Button type="primary" onClick={handleFilter}>筛选</Button>
                <Button onClick={handleResetFilter}>重置</Button>
                <Button 
                  type="link" 
                  onClick={() => setShowAdvancedFilter(!showAdvancedFilter)}
                >
                  {showAdvancedFilter ? '收起高级筛选' : '展开高级筛选'}
                </Button>
              </Space>
            </Col>
          </Row>
        </Form>
      </Card>

      <Table
        bordered
        columns={columns}
        dataSource={data}
        rowKey="id"
        loading={loading}
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
        }}
        scroll={{ x: 'max-content' }}
        rowClassName={(_, idx) => (idx % 2 === 0 ? 'table-row-light' : 'table-row-dark')}
      />
      
      <Modal
        title={editingProduct ? '编辑产品' : '新增产品'}
        open={modalVisible}
        onOk={handleOk}
        onCancel={() => setModalVisible(false)}
        destroyOnClose
        width={800}
      >
        <Form form={form} layout="vertical">
          <Form.Item name="productCode" label="产品代码" rules={[{ required: true, message: '请输入产品代码' }]}>
            <Input placeholder="请输入产品代码" />
          </Form.Item>
          <Form.Item name="productName" label="产品名称" rules={[{ required: true, message: '请输入产品名称' }]}>
            <Input placeholder="请输入产品名称" />
          </Form.Item>
          <Form.Item name="productType" label="产品类型">
            <Select placeholder="请选择产品类型" allowClear>
              {productTypeOptions.map(option => (
                <Option key={option.value} value={option.value}>{option.label}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item name="strategyId" label="策略ID">
            <InputNumber placeholder="请输入策略ID" style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="portfolioId" label="组合ID">
            <InputNumber placeholder="请输入组合ID" style={{ width: '100%' }} />
          </Form.Item>
          <Form.Item name="riskLevel" label="风险等级">
            <Select placeholder="请选择风险等级" allowClear>
              {riskLevelOptions.map(option => (
                <Option key={option.value} value={option.value}>{option.label}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item name="targetReturn" label="目标收益率(%)">
            <InputNumber 
              placeholder="请输入目标收益率" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="maxDrawdown" label="最大回撤(%)">
            <InputNumber 
              placeholder="请输入最大回撤" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="investmentHorizon" label="投资期限">
            <Select placeholder="请选择投资期限" allowClear>
              {investmentHorizonOptions.map(option => (
                <Option key={option.value} value={option.value}>{option.label}</Option>
              ))}
            </Select>
          </Form.Item>
          <Form.Item name="minInvestment" label="最小投资金额">
            <InputNumber 
              placeholder="请输入最小投资金额" 
              min={0} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="maxInvestment" label="最大投资金额">
            <InputNumber 
              placeholder="请输入最大投资金额" 
              min={0} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="managementFee" label="管理费率(%)">
            <InputNumber 
              placeholder="请输入管理费率" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="performanceFee" label="业绩费率(%)">
            <InputNumber 
              placeholder="请输入业绩费率" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="subscriptionFee" label="认购费率(%)">
            <InputNumber 
              placeholder="请输入认购费率" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="redemptionFee" label="赎回费率(%)">
            <InputNumber 
              placeholder="请输入赎回费率" 
              min={0} 
              max={100} 
              precision={2}
              style={{ width: '100%' }}
            />
          </Form.Item>
          <Form.Item name="description" label="产品描述">
            <TextArea rows={3} placeholder="请输入产品描述" />
          </Form.Item>
          <Form.Item name="prospectus" label="产品说明书">
            <TextArea rows={3} placeholder="请输入产品说明书" />
          </Form.Item>
          <Form.Item name="status" label="状态">
            <Select placeholder="请选择状态" allowClear>
              <Option value="ACTIVE">正常</Option>
              <Option value="INACTIVE">停用</Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </Card>
  );
};

export default ProductList; 