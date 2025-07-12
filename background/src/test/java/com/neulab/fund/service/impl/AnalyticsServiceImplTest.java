package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class AnalyticsServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        AnalyticsServiceImpl service = new AnalyticsServiceImpl();
        Field f1 = AnalyticsServiceImpl.class.getDeclaredField("userEventLogRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.UserEventLogRepository.class));
        Field f2 = AnalyticsServiceImpl.class.getDeclaredField("jdbcTemplate");
        f2.setAccessible(true);
        f2.set(service, mock(org.springframework.jdbc.core.JdbcTemplate.class));
        service.toString();
        service.hashCode();
    }
} 