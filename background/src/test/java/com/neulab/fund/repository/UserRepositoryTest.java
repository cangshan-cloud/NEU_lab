package com.neulab.fund.repository;

import com.neulab.fund.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("repoUser");
        user.setPassword("pwd");
        user = userRepository.save(user);
        Optional<User> found = userRepository.findByUsername("repoUser");
        assertTrue(found.isPresent());
        assertEquals("repoUser", found.get().getUsername());
    }

    @Test
    void testExistsByUsername() {
        User user = new User();
        user.setUsername("existRepoUser");
        user.setPassword("pwd");
        userRepository.save(user);
        assertTrue(userRepository.existsByUsername("existRepoUser"));
        assertFalse(userRepository.existsByUsername("notExistUser"));
    }
} 