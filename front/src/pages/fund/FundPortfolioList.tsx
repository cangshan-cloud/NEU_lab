import React, { useEffect, useState } from 'react';
import { Card, Table, message, Tag, Button, Modal, Form, Input, InputNumber, Select, Popconfirm } from 'antd';
import { fundPortfolioApi } from '../../api';
import type { FundPortfolio, FundPortfolioVO } from '../../types';

const { Option } = Select;

const FundPortfolioList: React.FC = () => {
  const [data, setData] = useState<FundPortfolio[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [editingPortfolio, setEditingPortfolio] = useState<FundPortfolio | null>(null);
  const [form] = Form.useForm();

  const statusColorMap: Record<string, string> = {
    ACTIVE: 'green',
    INACTIVE: 'red',
  };

  const portfolioTypeOptions = [
    { label: '股票型', value: 'STOCK' },
    { label: '债券型', value: 'BOND' },
    { label: '混合型', value: 'HYBRID' },
    { label: '货币型', value: 'MONEY' },
    { label: '指数型', value: 'INDEX' },
    { label: 'FOF型', value: 'FOF' },
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

  const fetchData = async () => {
    try {
      setLoading(true);
      // 优先使用VO接口
      const response = await fundPortfolioApi.getAllVOs();
      let arr: FundPortfolio[] = [];
      if (Array.isArray(response.data.data)) {
        // 将VO数据转换为FundPortfolio格式
        arr = response.data.data.map((vo: FundPortfolioVO) => ({
          id: vo.id,
          portfolioName: vo.portfolioName,
          portfolioCode: vo.portfolioCode,
          portfolioType: vo.portfolioType,
          riskLevel: vo.riskLevel,
          targetReturn: vo.targetReturn,
          maxDrawdown: vo.maxDrawdown,
          investmentHorizon: vo.investmentHorizon,
          minInvestment: vo.minInvestment,
          description: vo.description,
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
      setData(arr);
    } catch (error) {
      // 如果VO接口失败，回退到普通接口
      try {
        const response = await fundPortfolioApi.getAll();
        let arr: FundPortfolio[] = [];
        if (Array.isArray(response.data.data)) arr = response.data.data;
        else if (
          response.data.data &&
          typeof response.data.data === 'object' &&
          'content' in response.data.data &&
          Array.isArray((response.data.data as any).content)
        ) {
          arr = (response.data.data as any).content;
        } else arr = [];
        setData(arr);
      } catch (fallbackError) {
        message.error('获取基金组合列表失败');
        setData([]);
      }
    } finally {
      setLoading(false);
    }
  };

  const handleAdd = () => {
    setEditingPortfolio(null);
    form.resetFields();
    setModalVisible(true);
  };

  const handleEdit = (record: FundPortfolio) => {
    setEditingPortfolio(record);
    form.setFieldsValue(record);
    setModalVisible(true);
  };

  const handleDelete = async (id: number) => {
    try {
      await fundPortfolioApi.delete(id);
      message.success('删除成功');
      fetchData();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const handleOk = async () => {
    try {
      const values = await form.validateFields();
      if (editingPortfolio) {
        await fundPortfolioApi.update(editingPortfolio.id, values);
        message.success('编辑成功');
      } else {
        await fundPortfolioApi.create(values);
        message.success('新增成功');
      }
      setModalVisible(false);
      fetchData();
    } catch (e) {
      // 校验失败或接口异常
    }
  };

  const columns = [
    { title: '组合ID', dataIndex: 'id', key: 'id', width: 80 },
    { title: '组合代码', dataIndex: 'portfolioCode', key: 'portfolioCode' },
    { title: '组合名称', dataIndex: 'portfolioName', key: 'portfolioName' },
    { 
      title: '组合类型', 
      dataIndex: 'portfolioType', 
      key: 'portfolioType',
      render: (type: string) => {
        const option = portfolioTypeOptions.find(opt => opt.value === type);
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
      render: (_: any, record: FundPortfolio) => (
        <>
          <Button type="link" onClick={() => handleEdit(record)}>编辑</Button>
          <Popconfirm title="确定要删除该组合吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <Card title="基金组合管理" extra={<Button type="primary" onClick={handleAdd}>新增组合</Button>}>
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
        title={editingPortfolio ? '编辑组合' : '新增组合'}
        open={modalVisible}
        onOk={handleOk}
        onCancel={() => setModalVisible(false)}
        destroyOnClose
        width={600}
      >
        <Form form={form} layout="vertical">
          <Form.Item name="portfolioCode" label="组合代码" rules={[{ required: true, message: '请输入组合代码' }]}>
            <Input placeholder="请输入组合代码" />
          </Form.Item>
          <Form.Item name="portfolioName" label="组合名称" rules={[{ required: true, message: '请输入组合名称' }]}>
            <Input placeholder="请输入组合名称" />
          </Form.Item>
          <Form.Item name="portfolioType" label="组合类型">
            <Select placeholder="请选择组合类型" allowClear>
              {portfolioTypeOptions.map(option => (
                <Option key={option.value} value={option.value}>{option.label}</Option>
              ))}
            </Select>
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
          <Form.Item name="description" label="组合描述">
            <Input.TextArea rows={3} placeholder="请输入组合描述" />
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

export default FundPortfolioList; 