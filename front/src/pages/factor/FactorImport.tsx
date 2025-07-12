import React, { useEffect, useState } from 'react';
import { Table, Button, Upload, message } from 'antd';
import { getFactorList, batchImportFactors } from '../../api/factor';
import type { Factor } from '../../types';
import { useTrackEvent } from '../../utils/request';

const FactorImport: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/factors/import');
  }, [track]);
  // 导入、模板下载、批量操作等操作可用track('click', '/factors/import', { buttonId: 'import' })等
  const [data, setData] = useState<Factor[]>([]);
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

  const handleImport = async (file: any) => {
    try {
      const text = await file.text();
      const json = JSON.parse(text);
      if (!Array.isArray(json)) throw new Error('请上传JSON数组文件');
      await batchImportFactors(json);
      message.success('批量导入成功');
      fetchData();
    } catch (e) {
      message.error('请上传格式正确的JSON数组文件');
    }
    return false;
  };

  return (
    <div>
      <Upload beforeUpload={handleImport} showUploadList={false} accept=".json">
        <Button type="primary" style={{marginBottom: 16}}>批量导入</Button>
      </Upload>
      <Table
        rowKey="id"
        columns={[
          { title: '因子ID', dataIndex: 'id' },
          { title: '因子代码', dataIndex: 'factorCode' },
          { title: '因子名称', dataIndex: 'factorName' },
          { title: '类型', dataIndex: 'factorType' },
          { title: '分类', dataIndex: 'factorCategory' },
          { title: '状态', dataIndex: 'status' }
        ]}
        dataSource={data}
        loading={loading}
        pagination={false}
        size="small"
      />
    </div>
  );
};

export default FactorImport; 