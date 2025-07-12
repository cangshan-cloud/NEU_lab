package com.neulab.fund.vo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class UserVOTest {
    @Test
    void testGetterSetter() {
        UserVO vo = new UserVO();
        vo.setId(1L);
        vo.setUsername("user");
        vo.setRealName("real");
        vo.setEmail("mail");
        vo.setPhone("phone");
        vo.setStatus("active");
        vo.setRoleName("admin");
        vo.setCompanyId(2L);
        vo.setManagerId(3L);
        vo.setRoleId(4L);
        assertEquals(1L, vo.getId());
        assertEquals("user", vo.getUsername());
        assertEquals("real", vo.getRealName());
        assertEquals("mail", vo.getEmail());
        assertEquals("phone", vo.getPhone());
        assertEquals("active", vo.getStatus());
        assertEquals("admin", vo.getRoleName());
        assertEquals(2L, vo.getCompanyId());
        assertEquals(3L, vo.getManagerId());
        assertEquals(4L, vo.getRoleId());
    }
} 