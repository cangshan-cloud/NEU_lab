package com.neulab.fund.repository;

import com.neulab.fund.entity.TradeError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeErrorRepository extends JpaRepository<TradeError, Long> {
    TradeError findByDeliveryOrderId(Long deliveryOrderId);
} 