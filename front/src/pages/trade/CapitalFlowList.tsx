import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input, Form, Select } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getCapitalFlowList, deleteCapitalFlow, capitalFlowApi } from '../../api/trade';
import type { CapitalFlow } from '../../types';
import { useTrackEvent } from '../../utils/request';

const { Search } = Input;

const statusMap: Record<string, string> = {
  SUCCESS: '成功',
  FAILED: '失败',
  PENDING: '处理中',
  REJECTED: '已驳回',
};

const CapitalFlowModal: React.FC<{
  open: boolean;
  mode: 'view' | 'edit';
  data?: any;
  onOk?: (values: any) => void;
  onCancel: () => void;
}> = ({ open, mode, data, onOk, onCancel }) => {
  const [form] = Form.useForm();
  React.useEffect(() => {
    if (open) {
      form.setFieldsValue(data);
    }
  }, [open, data, form]);
  const isView = mode === 'view';
  return (
    <Modal
      open={open}
      title={isView ? '查看资金流水' : '编辑资金流水'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnHidden
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="流水号" name="flowNo"><Input disabled /></Form.Item>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="流水类型" name="flowType"><Select disabled={isView} options={[
          { value: 'DEPOSIT', label: '充值' },
          { value: 'WITHDRAW', label: '提现' },
          { value: 'BUY', label: '买入' },
          { value: 'SELL', label: '卖出' },
          { value: 'FEE', label: '手续费' },
          { value: 'DIVIDEND', label: '分红' },
        ]} /></Form.Item>
        <Form.Item label="金额" name="amount"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="变动前余额" name="balanceBefore"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="变动后余额" name="balanceAfter"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="订单ID" name="orderId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="流水状态" name="status"><Select disabled={isView} options={Object.entries(statusMap).map(([value, label]) => ({ value, label }))} /></Form.Item>
        <Form.Item label="备注" name="remark"><Input.TextArea disabled={isView} /></Form.Item>
        <Form.Item label="流水时间" name="flowTime"><Input disabled /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const CapitalFlowList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<CapitalFlow[]>([]);
  const [searchText, setSearchText] = useState('');
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/capital-flows');
  }, [track]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await getCapitalFlowList();
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setDataSource(list);
      setTotal(list.length);
    } catch (error) {
      message.error('获取资金流水列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = (id: number) => {
    track('click', '/capital-flows', { buttonId: 'delete', flowId: id });
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个资金流水吗？',
      onOk: async () => {
        try {
          const response = await deleteCapitalFlow(id);
          if (response.data.code === 200) {
            message.success('删除成功');
            fetchData();
          }
        } catch (error) {
          message.error('删除失败');
        }
      },
    });
  };

  const handleView = (record: any) => {
    track('click', '/capital-flows', { buttonId: 'view', flowId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/capital-flows', { buttonId: 'edit', flowId: record.id });
    setModalData(record);
    setModalMode('edit');
    setModalOpen(true);
  };

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: '流水号',
      dataIndex: 'flowNo',
      key: 'flowNo',
    },
    {
      title: '用户ID',
      dataIndex: 'userId',
      key: 'userId',
      width: 100,
    },
    {
      title: '流水类型',
      dataIndex: 'flowType',
      key: 'flowType',
      render: (flowType: string) => {
        const colorMap: Record<string, string> = {
          'DEPOSIT': 'green',
          'WITHDRAW': 'red',
          'BUY': 'blue',
          'SELL': 'orange',
          'FEE': 'purple',
        };
        const typeMap: Record<string, string> = {
          'DEPOSIT': '充值',
          'WITHDRAW': '提现',
          'BUY': '买入',
          'SELL': '卖出',
          'FEE': '手续费',
        };
        return <Tag color={colorMap[flowType] || 'default'}>{typeMap[flowType] || flowType}</Tag>;
      },
    },
    {
      title: '金额',
      dataIndex: 'amount',
      key: 'amount',
      render: (value: number) => {
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>¥{value.toLocaleString()}</span>;
      },
    },
    {
      title: '变动前余额',
      dataIndex: 'balanceBefore',
      key: 'balanceBefore',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '变动后余额',
      dataIndex: 'balanceAfter',
      key: 'balanceAfter',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '订单ID',
      dataIndex: 'orderId',
      key: 'orderId',
      width: 100,
      render: (value: number) => value || '-',
    },
    {
      title: '产品ID',
      dataIndex: 'productId',
      key: 'productId',
      width: 100,
      render: (value: number) => value || '-',
    },
    {
      title: '流水状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => {
        const colorMap: Record<string, string> = {
          SUCCESS: 'green',
          FAILED: 'red',
          PENDING: 'orange',
        };
        const statusMap: Record<string, string> = {
          SUCCESS: '成功',
          FAILED: '失败',
          PENDING: '处理中',
          REJECTED: '已驳回',
        };
        return <Tag color={colorMap[status] || 'default'}>{statusMap[status] || status}</Tag>;
      },
    },
    {
      title: '流水时间',
      dataIndex: 'flowTime',
      key: 'flowTime',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: any) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => handleView(record)}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          >
            编辑
          </Button>
          <Button 
            type="link" 
            danger 
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record.id)}
          >
            删除
          </Button>
        </Space>
      ),
    },
  ];

  const filteredData = dataSource.filter(item =>
    item.flowNo.toLowerCase().includes(searchText.toLowerCase()) ||
    item.flowType.toLowerCase().includes(searchText.toLowerCase()) ||
    item.status.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="资金流水管理" extra={
      <Space>
        <Search
          placeholder="搜索流水号、类型或状态"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => { setModalData(undefined); setModalMode('edit'); setModalOpen(true); }}
        >
          新增流水
        </Button>
      </Space>
    } style={{ maxWidth: 1200, margin: '0 auto' }}>
      <Table
        columns={columns}
        dataSource={filteredData}
        loading={loading}
        rowKey="id"
        pagination={{
          showSizeChanger: true,
          showQuickJumper: true,
          showTotal: (total) => `共 ${total} 条记录`,
        }}
        scroll={{ x: 1400 }}
      />
      <CapitalFlowModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
            await capitalFlowApi.update(modalData.id, values);
            message.success('保存成功');
            setModalOpen(false);
            fetchData();
          } catch (e) {
            message.error('保存失败');
          }
        }}
        onCancel={() => setModalOpen(false)}
      />
    </Card>
  );
};

export default CapitalFlowList; 