package com.neulab.fund.service;

import com.neulab.fund.entity.UserEventLog;
import com.neulab.fund.repository.UserEventLogRepository;
import com.neulab.fund.service.impl.UserEventLogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserEventLogServiceTest {
    @Mock
    private UserEventLogRepository userEventLogRepository;
    @InjectMocks
    private UserEventLogServiceImpl userEventLogService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }
    @Test
    void testSaveEventLog() {
        UserEventLog log = new UserEventLog();
        when(userEventLogRepository.save(log)).thenReturn(log);
        assertEquals(log, userEventLogService.saveEventLog(log));
    }
} 