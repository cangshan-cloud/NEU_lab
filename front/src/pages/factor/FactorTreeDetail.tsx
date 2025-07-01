import React, { useEffect, useState } from 'react';
import { Card, Table, Tag, Spin, Button } from 'antd';
import { useParams, useNavigate } from 'react-router-dom';
import { getFactorTreeWithNodesList } from '../../api/factor';

const FactorTreeDetail: React.FC = () => {
  const { id } = useParams();
  const [tree, setTree] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      const res = await getFactorTreeWithNodesList();
      const found = (res.data?.data || []).find((t: any) => String(t.tree.id) === String(id));
      setTree(found);
      setLoading(false);
    };
    fetch();
  }, [id]);

  if (loading) return <Spin style={{margin: 40}} />;
  if (!tree) return <div style={{margin: 40}}>未找到该因子树</div>;

  return (
    <Card title={tree.tree.treeName} extra={<Button onClick={() => navigate(-1)}>返回</Button>} style={{maxWidth: 900, margin: '32px auto'}}>
      <div style={{marginBottom: 16}}>描述：{tree.tree.treeDescription || '-'}</div>
      <Table
        rowKey={r => r.id}
        columns={[
          { title: '因子代码', dataIndex: 'factorCode' },
          { title: '因子名称', dataIndex: 'factorName' },
          { title: '类型', dataIndex: 'factorType' },
          { title: '分类', dataIndex: 'factorCategory' },
          { title: '状态', dataIndex: 'status', render: (v: string) => <Tag color={v === 'active' ? 'green' : 'red'}>{v}</Tag> }
        ]}
        dataSource={tree.factors}
        pagination={false}
        size="small"
      />
    </Card>
  );
};

export default FactorTreeDetail; 