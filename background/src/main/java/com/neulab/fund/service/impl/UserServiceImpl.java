package com.neulab.fund.service.impl;

import com.neulab.fund.entity.User;
import com.neulab.fund.repository.UserRepository;
import com.neulab.fund.service.UserService;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.vo.UserVO;
import com.neulab.fund.vo.UserUpdateDTO;
import com.neulab.fund.repository.FundManagerRepository;
import com.neulab.fund.repository.FundCompanyRepository;
import com.neulab.fund.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import com.neulab.fund.util.JwtUtil;
import com.neulab.fund.entity.FundManager;
import com.neulab.fund.entity.FundCompany;
import com.neulab.fund.entity.Role;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FundManagerRepository fundManagerRepository;
    @Autowired
    private FundCompanyRepository fundCompanyRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User register(UserRegisterDTO dto) {
        // 用户名唯一校验
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 密码加密
        user.setRealName(dto.getRealName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setStatus("ACTIVE");
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        // 新增：注册时支持companyId、managerId、roleId
        user.setCompanyId(dto.getCompanyId());
        user.setManagerId(dto.getManagerId());
        user.setRoleId(dto.getRoleId());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserVO login(UserLoginDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) { // 密码加密比对
            throw new IllegalArgumentException("密码错误");
        }
        // 登录成功，生成token
        String token = jwtUtil.generateToken(user.getUsername());
        UserVO vo = toUserVO(user);
        vo.setStatus(token); // 临时复用status字段传递token，Controller需调整为标准返回
        return vo;
    }

    @Override
    public UserVO toUserVO(User user) {
        if (user == null) return null;
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setRealName(user.getRealName());
        vo.setEmail(user.getEmail());
        vo.setPhone(user.getPhone());
        vo.setStatus(user.getStatus());
        // 通过roleId查找角色名
        String roleName = null;
        if (user.getRoleId() != null) {
            Role role = roleService.findById(user.getRoleId());
            if (role != null) roleName = role.getRoleName();
        }
        vo.setRoleName(roleName);
        vo.setRoleId(user.getRoleId());
        vo.setCompanyId(user.getCompanyId());
        vo.setManagerId(user.getManagerId());
        return vo;
    }

    @Override
    public UserVO updateUser(Long id, UserUpdateDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        if (dto.getRealName() != null) user.setRealName(dto.getRealName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhone() != null) user.setPhone(dto.getPhone());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }

    @Override
    public Page<UserVO> listUsers(String role, String status, String keyword, Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return new PageImpl<>(
            page.getContent().stream()
                .filter(u -> role == null || (u.getRoleId() != null && role.equals(getRoleNameById(u.getRoleId()))))
                .filter(u -> status == null || status.equals(u.getStatus()))
                .filter(u -> keyword == null || u.getUsername().contains(keyword) || (u.getRealName() != null && u.getRealName().contains(keyword)))
                .map(this::toUserVO)
                .collect(Collectors.toList()),
            pageable,
            page.getTotalElements()
        );
    }

    private String getRoleNameById(Long roleId) {
        if (roleId == null) return null;
        Role role = roleService.findById(roleId);
        return role != null ? role.getRoleName() : null;
    }

    @Override
    public UserVO bindManager(Long userId, Long managerId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setManagerId(managerId);
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }

    @Override
    public UserVO bindCompany(Long userId, Long companyId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setCompanyId(companyId);
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }

    @Override
    public UserVO updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        Role role = roleService.findByRoleName(roleName);
        if (role == null) throw new IllegalArgumentException("角色不存在");
        user.setRoleId(role.getId());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }

    @Override
    public UserVO disableUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setStatus("INACTIVE");
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserVO resetPassword(Long userId, com.neulab.fund.vo.UserResetPasswordDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        user.setUpdatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
        return toUserVO(user);
    }
} 