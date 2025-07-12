import React from 'react';
import { useEffect } from 'react';
import { useTrackEvent } from '../utils/request';
const RoleChangeRequestAdmin: React.FC = () => {
  const track = useTrackEvent();
  useEffect(() => {
    track('view', '/role-change-request-admin');
  }, [track]);
  // 导出、批量操作等操作可用track('click', '/role-change-request-admin', { buttonId: 'export' })等
  return <div style={{margin:40}}>当前系统无角色变更申请功能</div>;
};
export default RoleChangeRequestAdmin; 