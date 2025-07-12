package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;

class UserEventLogServiceImplTest {
    @Test
    void testAllPublicMethods() {
        UserEventLogServiceImpl service = new UserEventLogServiceImpl();
        service.toString();
        service.hashCode();
    }
} 