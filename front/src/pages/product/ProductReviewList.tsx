import React, { useState, useEffect } from 'react';
import { Table, Card, Button, Space, Tag, Modal, message, Input } from 'antd';
import { PlusOutlined, EditOutlined, DeleteOutlined, EyeOutlined } from '@ant-design/icons';
import { getProductReviewList, deleteProductReview } from '../../api/product';
import type { ProductReview } from '../../types';

const { Search } = Input;

const ProductReviewList: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [dataSource, setDataSource] = useState<ProductReview[]>([]);
  const [searchText, setSearchText] = useState('');

  const fetchData = async () => {
    setLoading(true);
    try {
      const response = await getProductReviewList();
      if (response.data.code === 0) {
        setDataSource(response.data.data.content || response.data.data);
      }
    } catch (error) {
      message.error('获取产品审核列表失败');
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
      content: '确定要删除这个产品审核吗？',
      onOk: async () => {
        try {
          const response = await deleteProductReview(id);
          if (response.data.code === 0) {
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
      title: '产品ID',
      dataIndex: 'productId',
      key: 'productId',
      width: 100,
    },
    {
      title: '审核员ID',
      dataIndex: 'reviewerId',
      key: 'reviewerId',
      width: 100,
    },
    {
      title: '审核类型',
      dataIndex: 'reviewType',
      key: 'reviewType',
    },
    {
      title: '审核状态',
      dataIndex: 'reviewStatus',
      key: 'reviewStatus',
      render: (status: string) => {
        const colorMap: Record<string, string> = {
          'PENDING': 'orange',
          'APPROVED': 'green',
          'REJECTED': 'red',
        };
        const statusMap: Record<string, string> = {
          'PENDING': '待审核',
          'APPROVED': '已通过',
          'REJECTED': '已拒绝',
        };
        return <Tag color={colorMap[status] || 'default'}>{statusMap[status] || status}</Tag>;
      },
    },
    {
      title: '审核日期',
      dataIndex: 'reviewDate',
      key: 'reviewDate',
      render: (date: string) => date ? new Date(date).toLocaleString() : '-',
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
      render: (_: any, record: ProductReview) => (
        <Space size="middle">
          <Button 
            type="link" 
            icon={<EyeOutlined />}
            onClick={() => window.location.href = `/product/review/detail/${record.id}`}
          >
            查看
          </Button>
          <Button 
            type="link" 
            icon={<EditOutlined />}
            onClick={() => window.location.href = `/product/review/edit/${record.id}`}
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
    item.reviewType?.toLowerCase().includes(searchText.toLowerCase()) ||
    item.reviewStatus?.toLowerCase().includes(searchText.toLowerCase())
  );

  return (
    <Card title="产品审核管理" extra={
      <Space>
        <Search
          placeholder="搜索审核"
          allowClear
          onSearch={setSearchText}
          style={{ width: 200 }}
        />
        <Button 
          type="primary" 
          icon={<PlusOutlined />}
          onClick={() => window.location.href = '/product/review/add'}
        >
          新增审核
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

export default ProductReviewList; 