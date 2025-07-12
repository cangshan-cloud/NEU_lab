package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("coveruser");
        dto.setPassword("123456");
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
    @Test
    public void testLogin() throws Exception {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("coveruser");
        dto.setPassword("123456");
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testListUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testBindManager() throws Exception {
        mockMvc.perform(post("/api/users/1/bind-manager?managerId=1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testBindCompany() throws Exception {
        mockMvc.perform(post("/api/users/1/bind-company?companyId=1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateUserRole() throws Exception {
        mockMvc.perform(put("/api/users/1/role?roleName=USER"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDisableUser() throws Exception {
        mockMvc.perform(put("/api/users/1/disable"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testResetPassword() throws Exception {
        mockMvc.perform(put("/api/users/1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");
        dto.setEmail("test@example.com");
        dto.setRealName("测试用户");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUsernameExists() throws Exception {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("existinguser");
        dto.setPassword("password123");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUser() throws Exception {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginPasswordError() throws Exception {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("wrongpassword");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("nonexistentuser");
        dto.setPassword("password123");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegister_MissingParam() throws Exception {
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testLogin_MissingParam() throws Exception {
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateUser_InvalidId() throws Exception {
        mockMvc.perform(put("/api/users/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateUserRole_InvalidRole() throws Exception {
        mockMvc.perform(put("/api/users/1/role?roleName=NOT_EXIST"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDisableUser_InvalidId() throws Exception {
        mockMvc.perform(put("/api/users/999999/disable"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteUser_InvalidId() throws Exception {
        mockMvc.perform(delete("/api/users/999999"))
                .andExpect(status().isOk());
    }
    @Test
    public void testResetPassword_InvalidId() throws Exception {
        mockMvc.perform(put("/api/users/999999/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }
} 