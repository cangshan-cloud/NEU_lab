import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Tree, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, SearchOutlined } from '@ant-design/icons';
import { getFactorTreeList, deleteFactorTree } from '../../api/factor';
import type { FactorTree } from '../../types';

const { Search } = Input;

const FactorTreeList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<FactorTree[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getFactorTreeList();
      if (response.data.code === 200) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取因子树列表失败');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleDelete = (id: number) => {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个因子树吗？',
      onOk: async () => {
        try {
          const response = await deleteFactorTree(id);
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

  const columns = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      width: 80,
    },
    {
      title: '因子树名称',
      dataIndex: 'treeName',
      key: 'treeName',
    },
    {
      title: '描述',
      dataIndex: 'treeDescription',
      key: 'treeDescription',
      ellipsis: true,
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'ACTIVE' ? 'green' : 'red'}>
          {status === 'ACTIVE' ? '启用' : '禁用'}
        </Tag>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (date: string) => new Date(date).toLocaleString(),
    },
    {
      title: '操作',
      key: 'action',
      width: 200,
      render: (_: any, record: FactorTree) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/factor/tree/edit/${record.id}`}
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
    item.treeName.toLowerCase().includes(searchText.toLowerCase()) ||
    (item.treeDescription && item.treeDescription.toLowerCase().includes(searchText.toLowerCase()))
  );

  return (
    <Card title="因子树管理" extra={
      <Space>
        <Search
          placeholder="搜索因子树"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/factor/tree/add'}
        >
          新增因子树
        </Button>
      </Space>
    }>
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
      />
    </Card>
  );
};

export default FactorTreeList; 