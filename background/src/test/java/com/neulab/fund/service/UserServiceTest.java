package com.neulab.fund.service;

import com.neulab.fund.entity.User;
import com.neulab.fund.repository.UserRepository;
import com.neulab.fund.service.impl.UserServiceImpl;
import com.neulab.fund.util.JwtUtil;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.vo.UserRegisterDTO;
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
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterSuccess() {
        // Given
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");
        dto.setEmail("test@example.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("testuser");
        savedUser.setPassword("encodedPassword");
        savedUser.setEmail("test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        User result = userService.register(dto);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUsernameExists() {
        // Given
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("existinguser");
        dto.setPassword("password123");

        User existingUser = new User();
        existingUser.setUsername("existinguser");
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.register(dto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        // Given
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("password123");

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("test@example.com");
        user.setStatus("active");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testuser")).thenReturn("jwt-token");

        // When
        UserVO result = userService.login(dto);

        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("jwt-token", result.getStatus()); // 临时存储token
    }

    @Test
    void testLoginUserNotFound() {
        // Given
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("nonexistent");
        dto.setPassword("password123");

        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
    }

    @Test
    void testLoginPasswordError() {
        // Given
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("wrongpassword");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(dto));
    }

    @Test
    public void testLogin() {
        assertThrows(IllegalArgumentException.class, () -> userService.login(new UserLoginDTO()));
    }
    @Test
    public void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(userService.findById(1L));
    }
    @Test
    public void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, null));
    }
    @Test
    public void testListUsers() {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(0, 10);
        when(userRepository.findAll(pageable)).thenReturn(new org.springframework.data.domain.PageImpl<>(java.util.Collections.emptyList()));
        assertNotNull(userService.listUsers(null, null, null, pageable));
    }
    @Test
    public void testBindManager() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.bindManager(1L, 1L));
    }
    @Test
    public void testBindCompany() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.bindCompany(1L, 1L));
    }
    @Test
    public void testUpdateUserRole() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.updateUserRole(1L, "USER"));
    }
    @Test
    public void testDisableUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.disableUser(1L));
    }
    @Test
    public void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
    }
    @Test
    public void testResetPassword() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.resetPassword(1L, null));
    }
} 