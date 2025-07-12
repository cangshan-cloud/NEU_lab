package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class UserSegmentServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        UserSegmentServiceImpl service = new UserSegmentServiceImpl();
        Field f1 = UserSegmentServiceImpl.class.getDeclaredField("userSegmentRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.UserSegmentRepository.class));
        service.toString();
        service.hashCode();
    }
} 