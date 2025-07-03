package com.neulab.fund.service.impl;

import com.neulab.fund.entity.DeliveryOrder;
import com.neulab.fund.repository.DeliveryOrderRepository;
import com.neulab.fund.service.DeliveryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;

    @Override
    public List<DeliveryOrder> findByConditions(Long userId, Long productId, String status) {
        if (userId != null && productId != null && status != null) {
            return deliveryOrderRepository.findByUserIdAndProductIdAndStatus(userId, productId, status);
        }
        return deliveryOrderRepository.findAll();
    }
} 