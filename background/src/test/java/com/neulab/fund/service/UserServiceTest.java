package com.neulab.fund.service;

import com.neulab.fund.entity.User;
import com.neulab.fund.repository.UserRepository;
import com.neulab.fund.service.impl.UserServiceImpl;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.vo.UserVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("testuser");
        dto.setPassword("123456");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encoded123456");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User user = userService.register(dto);
        assertEquals("testuser", user.getUsername());
        assertEquals("encoded123456", user.getPassword());
    }

    @Test
    void testRegisterUsernameExists() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("existuser");
        when(userRepository.findByUsername("existuser")).thenReturn(Optional.of(new User()));
        assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
    }

    @Test
    void testLoginSuccess() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("123456");
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encoded123456");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encoded123456")).thenReturn(true);
        UserVO vo = userService.login(dto);
        assertEquals("testuser", vo.getUsername());
    }

    @Test
    void testLoginPasswordError() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("wrongpwd");
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encoded123456");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpwd", "encoded123456")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
    }

    @Test
    void testLoginUserNotFound() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("notexist");
        dto.setPassword("123456");
        when(userRepository.findByUsername("notexist")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
    }
} 