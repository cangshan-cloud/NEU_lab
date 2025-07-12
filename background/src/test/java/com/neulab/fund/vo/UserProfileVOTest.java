package com.neulab.fund.vo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UserProfileVOTest {
    @Test
    void testGetterSetter() {
        UserProfileVO vo = new UserProfileVO();
        vo.setUserId(1L);
        vo.setProfileText("profile");
        vo.setTags("tag1,tag2");
        assertEquals(1L, vo.getUserId());
        assertEquals("profile", vo.getProfileText());
        assertEquals("tag1,tag2", vo.getTags());
    }
} 