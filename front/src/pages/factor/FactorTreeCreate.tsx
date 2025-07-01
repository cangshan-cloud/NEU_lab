import React, { useEffect, useState } from 'react';
import { Card, Input, Button, Table, message } from 'antd';
import { getFactorList, createFactorTreeWithNodes } from '../../api/factor';
import { useNavigate } from 'react-router-dom';
import type { Factor } from '../../types';

const FactorTreeCreate: React.FC = () => {
  const [factors, setFactors] = useState<Factor[]>([]);
  const [selectedRowKeys, setSelectedRowKeys] = useState<React.Key[]>([]);
  const [treeName, setTreeName] = useState('');
  const [treeDesc, setTreeDesc] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    getFactorList().then(res => setFactors(res.data?.data?.content || res.data?.data || []));
  }, []);

  const handleSave = async () => {
    if (!treeName) {
      message.error('请输入因子树名称');
      return;
    }
    if (selectedRowKeys.length === 0) {
      message.error('请至少选择一个因子');
      return;
    }
    setLoading(true);
    try {
      const nodes = selectedRowKeys.map(fid => ({ factorId: fid, nodeName: factors.find(f => f.id === fid)?.factorName || '', nodeType: '因子', status: 'active' }));
      const vo = {
        tree: {
          treeName,
          treeDescription: treeDesc,
          status: 'active'
        },
        nodes
      };
      await createFactorTreeWithNodes(vo);
      message.success('创建成功');
      navigate(-1);
    } catch (e: any) {
      message.error('保存失败: ' + (e?.message || '未知错误'));
    }
    setLoading(false);
  };

  return (
    <Card title="新增因子树" style={{maxWidth: 900, margin: '32px auto'}}>
      <div style={{display: 'flex', gap: 32}}>
        <div style={{flex: 1}}>
          <div style={{marginBottom: 8}}>可选因子：</div>
          <Table
            rowKey="id"
            columns={[
              { title: '因子代码', dataIndex: 'factorCode' },
              { title: '因子名称', dataIndex: 'factorName' },
              { title: '类型', dataIndex: 'factorType' },
              { title: '分类', dataIndex: 'factorCategory' }
            ]}
            dataSource={factors}
            rowSelection={{
              type: 'checkbox',
              selectedRowKeys,
              onChange: setSelectedRowKeys
            }}
            pagination={false}
            size="small"
            scroll={{y: 400}}
          />
        </div>
        <div style={{flex: 1, display: 'flex', flexDirection: 'column', gap: 16}}>
          <Input placeholder="因子树名称" value={treeName} onChange={e => setTreeName(e.target.value)} />
          <Input placeholder="描述" value={treeDesc} onChange={e => setTreeDesc(e.target.value)} />
          <Button type="primary" onClick={handleSave} loading={loading} style={{marginTop: 24}}>保存</Button>
          <Button onClick={() => navigate(-1)} style={{marginTop: 8}}>取消</Button>
        </div>
      </div>
    </Card>
  );
};

export default FactorTreeCreate; 