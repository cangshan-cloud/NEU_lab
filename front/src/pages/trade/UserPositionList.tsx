import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input, Form, Select } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getUserPositionList, deleteUserPosition, userPositionApi } from '../../api/trade';
import type { UserPosition } from '../../types';
import { useTrackEvent } from '../../utils/request';

const { Search } = Input;

const UserPositionModal: React.FC<{
  open: boolean;
  mode: 'view' | 'edit';
  data?: any;
  onOk?: (values: any) => void;
  onCancel: () => void;
}> = ({ open, mode, data, onOk, onCancel }) => {
  const [form] = Form.useForm();
  useEffect(() => {
    if (open) {
      form.setFieldsValue(data);
    }
  }, [open, data, form]);
  const isView = mode === 'view';
  return (
    <Modal
      open={open}
      title={isView ? '查看用户持仓' : '编辑用户持仓'}
      onCancel={onCancel}
      onOk={isView ? onCancel : () => form.validateFields().then(onOk)}
      okText={isView ? '关闭' : '保存'}
      cancelButtonProps={{ style: { display: isView ? 'none' : undefined } }}
      destroyOnHidden
    >
      <Form form={form} layout="vertical" initialValues={data} disabled={isView}>
        <Form.Item label="用户ID" name="userId"><Input disabled /></Form.Item>
        <Form.Item label="产品ID" name="productId"><Input disabled /></Form.Item>
        <Form.Item label="持仓份额" name="shares"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="持仓成本" name="cost"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="平均成本价" name="avgCostPrice"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="当前市值" name="marketValue"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="浮动盈亏" name="profitLoss"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="浮动盈亏率" name="profitLossRate"><Input type="number" disabled={isView} /></Form.Item>
        <Form.Item label="最后更新时间" name="updatedAt"><Input disabled /></Form.Item>
        <Form.Item label="创建时间" name="createdAt"><Input disabled /></Form.Item>
      </Form>
    </Modal>
  );
};

const UserPositionList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<UserPosition[]>([]);
  const [searchText, setSearchText] = useState('');
  const [total, setTotal] = useState(0);
  const [modalOpen, setModalOpen] = useState(false);
  const [modalMode, setModalMode] = useState<'view' | 'edit'>('view');
  const [modalData, setModalData] = useState<any>(undefined);
  const track = useTrackEvent();

  useEffect(() => {
    track('view', '/user-positions');
  }, [track]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const res = await getUserPositionList();
      const list = Array.isArray(res.data.data) ? res.data.data : [];
      setDataSource(list);
      setTotal(list.length);
    } catch (error) {
      message.error('获取用户持仓列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = (id: number) => {
    track('click', '/user-positions', { buttonId: 'delete', positionId: id });
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个用户持仓吗？',
      onOk: async () => {
        try {
          const response = await deleteUserPosition(id);
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
    track('click', '/user-positions', { buttonId: 'view', positionId: record.id });
    setModalData(record);
    setModalMode('view');
    setModalOpen(true);
  };
  const handleEdit = (record: any) => {
    track('click', '/user-positions', { buttonId: 'edit', positionId: record.id });
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
      title: '用户ID',
      dataIndex: 'userId',
      key: 'userId',
      width: 100,
    },
    {
      title: '产品ID',
      dataIndex: 'productId',
      key: 'productId',
      width: 100,
    },
    {
      title: '持仓份额',
      dataIndex: 'shares',
      key: 'shares',
      render: (value: number) => value.toFixed(2),
    },
    {
      title: '持仓成本',
      dataIndex: 'cost',
      key: 'cost',
      render: (value: number) => `¥${value.toLocaleString()}`,
    },
    {
      title: '平均成本价',
      dataIndex: 'avgCostPrice',
      key: 'avgCostPrice',
      render: (value: number) => value ? `¥${value.toFixed(4)}` : '-',
    },
    {
      title: '市值',
      dataIndex: 'marketValue',
      key: 'marketValue',
      render: (value: number) => value ? `¥${value.toLocaleString()}` : '-',
    },
    {
      title: '盈亏',
      dataIndex: 'profitLoss',
      key: 'profitLoss',
      render: (value: number) => {
        if (value === null || value === undefined) return '-';
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>¥{value.toLocaleString()}</span>;
      },
    },
    {
      title: '盈亏率',
      dataIndex: 'profitLossRate',
      key: 'profitLossRate',
      render: (value: number) => {
        if (value === null || value === undefined) return '-';
        const color = value >= 0 ? 'green' : 'red';
        return <span style={{ color }}>{(value * 100).toFixed(2)}%</span>;
      },
    },
    {
      title: '更新时间',
      dataIndex: 'updatedAt',
      key: 'updatedAt',
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
    item.userId.toString().includes(searchText) ||
    item.productId.toString().includes(searchText)
  );

  return (
    <Card title="用户持仓管理" extra={
      <Space>
        <Search
          placeholder="搜索用户ID或产品ID"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => { setModalData(undefined); setModalMode('edit'); setModalOpen(true); }}
        >
          新增持仓
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
      <UserPositionModal
        open={modalOpen}
        mode={modalMode}
        data={modalData}
        onOk={async (values) => {
          try {
            await userPositionApi.update(modalData.id, values);
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

export default UserPositionList; 