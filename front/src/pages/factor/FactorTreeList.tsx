import React, { useEffect, useState } from 'react';
import { Table, Button, Modal, Input, Select, Tag, message, Popconfirm } from 'antd';
import { getFactorTreeWithNodesList, createFactorTreeWithNodes, updateFactorTreeWithNodes, deleteFactorTree } from '../../api/factor';
import { getFactorList } from '../../api/factor';
import type { Factor } from '../../types';
import { useNavigate } from 'react-router-dom';
import { useTrackEvent } from '../../utils/request';

const FactorTreeList: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/factor-trees');
  }, [track]);
  // 新增、编辑、筛选、查看详情等操作可用track('click', '/factor-trees', { buttonId: 'add' })等
  const [trees, setTrees] = useState<any[]>([]);
  const [factors, setFactors] = useState<Factor[]>([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [editTree, setEditTree] = useState<any>(null);
  const [treeName, setTreeName] = useState('');
  const [treeDesc, setTreeDesc] = useState('');
  const [selectedFactors, setSelectedFactors] = useState<number[]>([]);
  const [loading, setLoading] = useState(false);
  const [selectedRowKey, setSelectedRowKey] = useState<number | undefined>();

  const navigate = useNavigate();

  const fetchAll = async () => {
    setLoading(true);
    const [treeRes, factorRes] = await Promise.all([
      getFactorTreeWithNodesList(),
      getFactorList()
    ]);
    setTrees(treeRes.data?.data || []);
    setFactors(factorRes.data?.data?.content || factorRes.data?.data || []);
    setLoading(false);
  };

  useEffect(() => {
    fetchAll();
  }, []);

  const openModal = (tree?: any) => {
    if (tree) {
      setEditTree(tree);
      setTreeName(tree.tree.treeName);
      setTreeDesc(tree.tree.treeDescription || '');
      setSelectedFactors(tree.nodes.map((n: any) => n.factorId).filter(Boolean));
    } else {
      setEditTree(null);
      setTreeName('');
      setTreeDesc('');
      setSelectedFactors([]);
    }
    setModalOpen(true);
  };

  const handleOk = async () => {
    if (!treeName) {
      message.error('请输入因子树名称');
      return;
    }
    const nodes = selectedFactors.map(fid => ({ factorId: fid, nodeName: factors.find(f => f.id === fid)?.factorName || '', nodeType: '因子', status: 'active' }));
    const vo = {
      tree: {
        id: editTree?.tree?.id,
        treeName,
        treeDescription: treeDesc,
        status: 'active'
      },
      nodes
    };
    if (editTree) {
      await updateFactorTreeWithNodes(vo);
      message.success('编辑成功');
    } else {
      await createFactorTreeWithNodes(vo);
      message.success('创建成功');
    }
    setModalOpen(false);
    fetchAll();
  };

  const handleDelete = async (id: number) => {
    await deleteFactorTree(id);
    message.success('删除成功');
    fetchAll();
  };

  return (
    <div>
      <div style={{marginBottom: 16, display: 'flex', alignItems: 'center', gap: 16}}>
        <Select
          style={{width: 300}}
          placeholder="选择因子树"
          value={selectedRowKey}
          onChange={id => setSelectedRowKey(Number(id))}
          allowClear
        >
          {trees.map(tree => (
            <Select.Option key={tree.tree.id} value={tree.tree.id}>
              {tree.tree.treeName}
            </Select.Option>
          ))}
        </Select>
        <Button type="primary" onClick={() => navigate('/factor/tree/create')}>新增因子树</Button>
      </div>
      <Table
        rowKey={r => r.tree.id}
        rowSelection={{
          type: 'radio',
          selectedRowKeys: selectedRowKey ? [selectedRowKey] : [],
          onChange: (keys) => setSelectedRowKey(Number(keys[0]))
        }}
        columns={[
          { title: '因子树名称', dataIndex: ['tree', 'treeName'] },
          { title: '描述', dataIndex: ['tree', 'treeDescription'] },
          { title: '包含因子', dataIndex: 'factors', render: (factors: any[], record: any) => {
            const unique = new Map();
            (factors || []).forEach(f => unique.set(f.id, f));
            return Array.from(unique.values()).map(f => (
              <Tag key={`${record.tree.id}-${f.id}`}>{f.factorName}</Tag>
            ));
          } },
          { title: '操作', render: (_: any, row: any) => <>
            <Button size="small" onClick={() => openModal(row)}>编辑</Button>
            <Popconfirm title="确定删除该因子树吗？" onConfirm={() => handleDelete(row.tree.id)}>
              <Button size="small" danger style={{marginLeft: 8}}>删除</Button>
            </Popconfirm>
          </> }
        ]}
        dataSource={trees}
        loading={loading}
        pagination={false}
        size="small"
      />
      <Button
        type="primary"
        disabled={!selectedRowKey}
        style={{margin: '16px 0'}}
        onClick={() => selectedRowKey && navigate(`/factor/tree/${selectedRowKey}`)}
      >
        查看详情
      </Button>
      <Modal
        open={modalOpen}
        title={editTree ? '编辑因子树' : '新增因子树'}
        onOk={handleOk}
        onCancel={() => setModalOpen(false)}
        destroyOnClose
      >
        <Input placeholder="因子树名称" value={treeName} onChange={e => setTreeName(e.target.value)} style={{marginBottom: 12}} />
        <Input placeholder="描述" value={treeDesc} onChange={e => setTreeDesc(e.target.value)} style={{marginBottom: 12}} />
        <Select
          mode="multiple"
          style={{width: '100%'}}
          placeholder="选择因子"
          value={selectedFactors}
          onChange={setSelectedFactors}
          optionFilterProp="children"
        >
          {factors.map(f => <Select.Option key={f.id} value={f.id}>{f.factorName}</Select.Option>)}
        </Select>
      </Modal>
    </div>
  );
};

export default FactorTreeList; 