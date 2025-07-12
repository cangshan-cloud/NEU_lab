package com.neulab.fund.service;

import com.neulab.fund.entity.UserProfile;
import com.neulab.fund.repository.UserProfileRepository;
import com.neulab.fund.service.impl.UserProfileServiceImpl;
import com.neulab.fund.vo.UserProfileVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserProfileServiceTest {
    @Mock
    private UserProfileRepository userProfileRepository;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;
    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }
    @Test
    void testGetUserProfile_Found() {
        UserProfile profile = new UserProfile();
        profile.setUserId(1L);
        profile.setProfileText("test");
        profile.setTags("tag");
        when(userProfileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));
        UserProfileVO vo = userProfileService.getUserProfile(1L);
        assertEquals(1L, vo.getUserId());
        assertEquals("test", vo.getProfileText());
        assertEquals("tag", vo.getTags());
    }
    @Test
    void testGetUserProfile_NotFound() {
        when(userProfileRepository.findByUserId(2L)).thenReturn(Optional.empty());
        UserProfileVO vo = userProfileService.getUserProfile(2L);
        assertNull(vo.getUserId());
        assertNull(vo.getProfileText());
        assertNull(vo.getTags());
    }
} 