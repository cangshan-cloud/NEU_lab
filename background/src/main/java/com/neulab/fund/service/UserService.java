package com.neulab.fund.service;

import com.neulab.fund.entity.User;
import java.util.Optional;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.vo.UserVO;
import com.neulab.fund.vo.UserUpdateDTO;
import com.neulab.fund.vo.UserResetPasswordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务接口
 */
public interface UserService {
    User register(UserRegisterDTO dto);
    Optional<User> findByUsername(String username);
    User findById(Long id);
    UserVO login(UserLoginDTO dto);
    UserVO toUserVO(User user);
    UserVO updateUser(Long id, UserUpdateDTO dto);
    Page<UserVO> listUsers(String role, String status, String keyword, Pageable pageable);
    UserVO bindManager(Long userId, Long managerId);
    UserVO bindCompany(Long userId, Long companyId);
    UserVO updateUserRole(Long userId, String roleName);
    UserVO disableUser(Long userId);
    void deleteUser(Long userId);
    UserVO resetPassword(Long userId, UserResetPasswordDTO dto);
} 