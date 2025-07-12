import React, { useEffect, useState } from 'react';
import { Table, Button, Input, InputNumber, message, Steps } from 'antd';
import { batchImportFactors, getFactorList } from '../../api/factor';
import type { Factor } from '../../types';
import { useTrackEvent } from '../../utils/request';

const StyleFactorCreate: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/style-factors/create');
  }, [track]);
  const [factors, setFactors] = useState<Factor[]>([]);
  const [selectedKeys, setSelectedKeys] = useState<React.Key[]>([]);
  const [selected, setSelected] = useState<Factor[]>([]);
  const [weights, setWeights] = useState<{[id: number]: number}>({});
  const [name, setName] = useState('');
  const [step, setStep] = useState(0);

  useEffect(() => {
    getFactorList().then(res => setFactors(res.data?.data?.content || res.data?.data || []));
  }, []);

  const handleAdd = () => {
    const newSelected = factors.filter(f => selectedKeys.includes(f.id) && !selected.some(s => s.id === f.id));
    setSelected([...selected, ...newSelected]);
    setSelectedKeys([]);
  };

  const handleRemove = (id: number) => {
    setSelected(selected.filter(f => f.id !== id));
    const newWeights = { ...weights };
    delete newWeights[id];
    setWeights(newWeights);
  };

  const handleSelectAll = () => {
    setSelected(factors);
  };

  const handleNext = () => {
    if (selected.length === 0) {
      message.error('请先选择因子');
      return;
    }
    setStep(1);
  };

  const handleSave = async () => {
    if (!name) {
      message.error('请填写风格因子名称');
      return;
    }
    if (selected.length === 0) {
      message.error('请先选择因子');
      return;
    }
    if (selected.some(f => !weights[f.id] || weights[f.id] <= 0)) {
      message.error('请为每个因子设置大于0的权重');
      return;
    }
    const newFactor = {
      factorCode: 'STYLE_' + Date.now(),
      factorName: name,
      factorCategory: '风格因子',
      factorType: '加权',
      description: '由多个基础因子加权生成风格因子',
      status: 'active',
      weights: selected.map(f => ({ id: f.id, weight: weights[f.id] }))
    };
    await batchImportFactors([newFactor]);
    message.success('新因子创建成功');
    setName('');
    setSelected([]);
    setWeights({});
    setStep(2);
  };

  return (
    <div>
      <Steps current={step} style={{marginBottom: 24}}>
        <Steps.Step title="选择因子" />
        <Steps.Step title="设置权重" />
        <Steps.Step title="完成" />
      </Steps>
      {step === 0 && (
        <div style={{display: 'flex', gap: 24}}>
          <div style={{flex: 1}}>
            <Table
              rowKey="id"
              columns={[
                { title: '因子代码', dataIndex: 'factorCode' },
                { title: '因子名称', dataIndex: 'factorName' }
              ]}
              dataSource={factors}
              rowSelection={{
                type: 'checkbox',
                selectedRowKeys: selectedKeys,
                onChange: setSelectedKeys
              }}
              pagination={false}
              size="small"
            />
            <Button onClick={handleAdd} style={{marginTop: 8}}>添加</Button>
            <Button onClick={handleSelectAll} style={{marginLeft: 8}}>全选</Button>
          </div>
          <div style={{flex: 1}}>
            <div>已选因子：</div>
            <Table
              rowKey="id"
              columns={[
                { title: '因子代码', dataIndex: 'factorCode' },
                { title: '因子名称', dataIndex: 'factorName' },
                { title: '操作', dataIndex: 'id', render: (id: number) => (
                  <Button size="small" danger onClick={() => handleRemove(id)}>移除</Button>
                )}
              ]}
              dataSource={selected}
              pagination={false}
              size="small"
            />
            <Button type="primary" onClick={handleNext} style={{marginTop: 8}}>下一步</Button>
          </div>
        </div>
      )}
      {step === 1 && (
        <div>
          <Input placeholder="风格因子名称" value={name} onChange={e => setName(e.target.value)} style={{width: 200, marginBottom: 16}} />
          <Table
            rowKey="id"
            columns={[
              { title: '因子代码', dataIndex: 'factorCode' },
              { title: '因子名称', dataIndex: 'factorName' },
              { title: '权重', dataIndex: 'id', render: (id: number) => (
                <InputNumber min={0} max={100} value={weights[id] || 0} onChange={v => setWeights(w => ({ ...w, [id]: v || 0 }))} />
              )}
            ]}
            dataSource={selected}
            pagination={false}
            size="small"
          />
          <Button onClick={() => setStep(0)} style={{marginRight: 8}}>上一步</Button>
          <Button type="primary" onClick={handleSave}>创建</Button>
        </div>
      )}
      {step === 2 && (
        <div style={{textAlign: 'center', marginTop: 40}}>
          <h3>新因子已创建！</h3>
          <Button type="primary" onClick={() => setStep(0)}>再建一个</Button>
        </div>
      )}
    </div>
  );
};

export default StyleFactorCreate; 