import React, { useState, useEffect } from 'react';
import { Card, Table, Tag, Button, Modal, Input, message } from 'antd';
import { batchImportFactors, getFactorList } from '../../api/factor';

const FactorList: React.FC = () => {
  const [importVisible, setImportVisible] = useState(false);
  const [importJson, setImportJson] = useState('');
  const [importLoading, setImportLoading] = useState(false);
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchData = async () => {
    setLoading(true);
    const res = await getFactorList();
    setData(res.data?.data?.content || res.data?.data || []);
    setLoading(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const columns = [
    {
      title: '因子代码',
      dataIndex: 'factorCode',
      key: 'factorCode',
    },
    {
      title: '因子名称',
      dataIndex: 'factorName',
      key: 'factorName',
    },
    {
      title: '因子分类',
      dataIndex: 'factorCategory',
      key: 'factorCategory',
    },
    {
      title: '更新频率',
      dataIndex: 'updateFrequency',
      key: 'updateFrequency',
    },
    {
      title: '状态',
      dataIndex: 'status',
      key: 'status',
      render: (status: string) => (
        <Tag color={status === 'ACTIVE' ? 'green' : 'red'}>
          {status === 'ACTIVE' ? '正常' : '停用'}
        </Tag>
      ),
    },
  ];

  const handleImport = async () => {
    try {
      setImportLoading(true);
      const data = JSON.parse(importJson);
      await batchImportFactors(data);
      message.success('批量导入成功');
      setImportVisible(false);
      setImportJson('');
      fetchData();
    } catch (e) {
      message.error('JSON格式错误或导入失败');
    } finally {
      setImportLoading(false);
    }
  };

  return (
    <Card title="因子列表" extra={<Button onClick={() => setImportVisible(true)}>批量导入因子</Button>}>
      <Table columns={columns} dataSource={data} rowKey="id" loading={loading} />
      <Modal
        title="批量导入因子"
        open={importVisible}
        onOk={handleImport}
        onCancel={() => setImportVisible(false)}
        confirmLoading={importLoading}
      >
        <Input.TextArea
          rows={8}
          value={importJson}
          onChange={e => setImportJson(e.target.value)}
          placeholder='Paste factor JSON array, e.g. [{"factorCode":"F001",...}]'
        />
      </Modal>
    </Card>
  );
};

export default FactorList; 