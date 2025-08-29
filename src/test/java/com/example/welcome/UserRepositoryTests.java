package com.example.welcome;

import com.example.welcome.model.User;
import com.example.welcome.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsername_shouldReturnSavedUser() {
        User u = new User();
        u.setUsername("alice");
        u.setPassword("secret");
        u.setRole("admin");
        userRepository.save(u);

        var found = userRepository.findByUsername("alice");
        assertThat(found).isPresent();
        assertThat(found.get().getRole()).isEqualTo("admin");
    }
}

