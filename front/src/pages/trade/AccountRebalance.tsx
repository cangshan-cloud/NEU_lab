import React, { useState, useEffect } from 'react';
import { Button, Form, InputNumber, Select, message, Card } from 'antd';
import { accountRebalance } from '../../api/trade';
import { getProducts } from '../../api/fund';
import { getUserList } from '../../api/user';

const AccountRebalance: React.FC = () => {
  // ... existing code ...

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