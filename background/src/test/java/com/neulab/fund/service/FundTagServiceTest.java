package com.neulab.fund.service;

import com.neulab.fund.repository.FundTagRepository;
import com.neulab.fund.service.impl.FundTagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FundTagServiceTest {

    @Mock
    private FundTagRepository fundTagRepository;

    @InjectMocks
    private FundTagServiceImpl fundTagService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> fundTagService.toString());
    }
} 