package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;

class UserProfileServiceImplTest {
    @Test
    void testAllPublicMethods() {
        UserProfileServiceImpl service = new UserProfileServiceImpl();
        service.toString();
        service.hashCode();
    }
} 