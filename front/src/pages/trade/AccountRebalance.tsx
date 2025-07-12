import React, { useState, useEffect } from 'react';
import { Button, Form, InputNumber, Select, message, Card } from 'antd';
import { accountRebalance } from '../../api/trade';
import { getProducts } from '../../api/fund';
import { getUserList } from '../../api/user';
import { useTrackEvent } from '../../utils/request';

const AccountRebalance: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/account-rebalance');
  }, [track]);
  // 调仓、导出、批量操作等操作可用track('click', '/account-rebalance', { buttonId: 'rebalance' })等

  const [users, setUsers] = useState<{ label: string; value: number }[]>([]);
  const [products, setProducts] = useState<{ label: string; value: number }[]>([]);

  useEffect(() => {
    getUserList().then(res => {
      const raw: any = res.data;
      const list = Array.isArray(raw) ? raw : (raw && Array.isArray(raw.records) ? raw.records : []);
      const total = (raw && typeof raw.total === 'number') ? raw.total : list.length;
      setUsers(list.map((item: any) => ({
        label: item.username || item.name,
        value: item.id
      })));
    });
    getProducts().then(res => setProducts((res.data || []).map((item: any) => ({
      label: item.productName,
      value: item.id
    }))));
  }, []);

  // ... existing code ...
}

export default AccountRebalance; 