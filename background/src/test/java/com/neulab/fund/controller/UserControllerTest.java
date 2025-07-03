package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterSuccess() throws Exception {
        String json = objectMapper.writeValueAsString(new RegisterDTO("testuser1", "123456", "张三", "test1@example.com", "12345678901"));
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testRegisterUsernameExists() throws Exception {
        // 先注册一次
        String json = objectMapper.writeValueAsString(new RegisterDTO("testuser2", "123456", "李四", "test2@example.com", "12345678902"));
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 再注册同名
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("用户名已存在"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        // 先注册
        String json = objectMapper.writeValueAsString(new RegisterDTO("testuser3", "123456", "王五", "test3@example.com", "12345678903"));
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 登录
        String loginJson = objectMapper.writeValueAsString(new LoginDTO("testuser3", "123456"));
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testLoginPasswordError() throws Exception {
        // 先注册
        String json = objectMapper.writeValueAsString(new RegisterDTO("testuser4", "123456", "赵六", "test4@example.com", "12345678904"));
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
        // 密码错误
        String loginJson = objectMapper.writeValueAsString(new LoginDTO("testuser4", "wrongpwd"));
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("密码错误"));
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        String loginJson = objectMapper.writeValueAsString(new LoginDTO("notexistuser", "123456"));
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("用户不存在"));
    }

    // DTO内部类
    static class RegisterDTO {
        public String username, password, realName, email, phone;
        public RegisterDTO(String u, String p, String r, String e, String ph) {
            this.username = u; this.password = p; this.realName = r; this.email = e; this.phone = ph;
        }
    }
    static class LoginDTO {
        public String username, password;
        public LoginDTO(String u, String p) { this.username = u; this.password = p; }
    }
} 