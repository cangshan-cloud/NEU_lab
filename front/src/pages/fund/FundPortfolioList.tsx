import React, { useEffect, useState } from 'react';
import { Table, Card, Tag, Button, Modal, Popconfirm, message } from 'antd';
import { fundPortfolioApi } from '../../api';
import type { FundPortfolio } from '../../types';

const FundPortfolioList: React.FC = () => {
  const [portfolios, setPortfolios] = useState<FundPortfolio[]>([]);
  const [loading, setLoading] = useState(false);
  const [selectedPortfolio, setSelectedPortfolio] = useState<FundPortfolio | null>(null);
  const [modalVisible, setModalVisible] = useState(false);

  const loadPortfolios = async () => {
    setLoading(true);
    try {
      const res = await fundPortfolioApi.getList();
      setPortfolios(res.data.data.content || res.data.data);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPortfolios();
  }, []);

  // 删除组合
  const handleDelete = async (id: number) => {
    try {
      await fundPortfolioApi.delete(id);
      message.success('删除成功');
      loadPortfolios();
    } catch (e) {
      message.error('删除失败');
    }
  };

  const columns = [
    {
      title: '组合名称',
      dataIndex: 'portfolioName',
      key: 'portfolioName',
    },
    {
      title: '创建时间',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (val: string) => val ? val.replace('T', ' ').slice(0, 19) : '-',
    },
    {
      title: '包含基金数',
      dataIndex: 'funds',
      key: 'funds',
      render: (funds: any[] = []) => <Tag color="blue">{Array.isArray(funds) ? funds.length : 0}</Tag>,
    },
    {
      title: '操作',
      key: 'action',
      render: (_: any, record: FundPortfolio) => (
        <>
          <Button type="link" onClick={() => { setSelectedPortfolio(record); setModalVisible(true); }}>查看详情</Button>
          <Popconfirm title="确定要删除该组合吗？" onConfirm={() => handleDelete(record.id)} okText="确定" cancelText="取消">
            <Button type="link" danger>删除</Button>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <Card title="基金组合管理">
      <Table
        dataSource={portfolios}
        rowKey="id"
        columns={columns}
        loading={loading}
      />
      <Modal
        title="组合详情"
        open={modalVisible}
        onCancel={() => setModalVisible(false)}
        footer={null}
      >
        {selectedPortfolio && (
          <div>
            <p><b>组合名称：</b>{selectedPortfolio.portfolioName}</p>
            <p><b>创建时间：</b>{selectedPortfolio.createdAt?.replace('T', ' ').slice(0, 19)}</p>
            <p><b>包含基金：</b>
              {Array.isArray(selectedPortfolio.funds) && selectedPortfolio.funds.length > 0
                ? selectedPortfolio.funds.map(f => `${f.fundName || f.name || ''}(${f.fundCode || f.code || f.id})`).join('，')
                : '-'}
            </p>
          </div>
        )}
      </Modal>
    </Card>
  );
};

export default FundPortfolioList; 