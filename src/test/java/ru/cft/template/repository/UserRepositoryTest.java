package ru.cft.template.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.cft.template.entity.User;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("existsByEmail - SUCCESS")
    void test_existsByEmail_Success() throws Exception {
        // Given
        String email = "shumleshas@gmail.com";

        // When + Then
        boolean exists = userRepository.existsByEmail(email);
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("existsByPhoneNumber - SUCCESS")
    void test_existsByPhoneNumber_Success() throws Exception {
        // Given
        String phoneNumber = "+79061982546";

        // When + Then
        boolean exists = userRepository.existsByPhoneNumber(phoneNumber);
        assertThat(exists).isTrue();
    }
}
