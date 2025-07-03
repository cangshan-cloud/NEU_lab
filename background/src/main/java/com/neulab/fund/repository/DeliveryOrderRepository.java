package com.neulab.fund.repository;

import com.neulab.fund.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 交割单Repository
 */
@Repository
public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
    // 可根据需要添加自定义查询方法
    List<DeliveryOrder> findByUserIdAndProductIdAndStatus(Long userId, Long productId, String status);
} 