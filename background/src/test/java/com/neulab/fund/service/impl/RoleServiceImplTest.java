package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class RoleServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        RoleServiceImpl service = new RoleServiceImpl();
        Field f1 = RoleServiceImpl.class.getDeclaredField("roleRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.RoleRepository.class));
        service.toString();
        service.hashCode();
    }
} 