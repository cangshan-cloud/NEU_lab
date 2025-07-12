package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class RoleChangeRequestServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        RoleChangeRequestServiceImpl service = new RoleChangeRequestServiceImpl();
        Field f1 = RoleChangeRequestServiceImpl.class.getDeclaredField("requestRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.RoleChangeRequestRepository.class));
        Field f2 = RoleChangeRequestServiceImpl.class.getDeclaredField("userRepository");
        f2.setAccessible(true);
        f2.set(service, mock(com.neulab.fund.repository.UserRepository.class));
        Field f3 = RoleChangeRequestServiceImpl.class.getDeclaredField("roleRepository");
        f3.setAccessible(true);
        f3.set(service, mock(com.neulab.fund.repository.RoleRepository.class));
        service.toString();
        service.hashCode();
    }
} 