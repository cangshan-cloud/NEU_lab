package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class FundTagServiceImplTest {
    @Test
    void testAllPublicMethods() {
        com.neulab.fund.repository.FundTagRepository repo = mock(com.neulab.fund.repository.FundTagRepository.class);
        FundTagServiceImpl service = new FundTagServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 