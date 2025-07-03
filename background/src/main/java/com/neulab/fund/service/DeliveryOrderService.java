package com.neulab.fund.service;

import com.neulab.fund.entity.DeliveryOrder;
import java.util.List;

public interface DeliveryOrderService {
    List<DeliveryOrder> findByConditions(Long userId, Long productId, String status);
} 