package br.com.vinisolon.application.repositories;

import br.com.vinisolon.application.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static br.com.vinisolon.application.common.MockConstants.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void save_ValidData_ReturnsUserEntity() {
        User savedUser = userRepository.save(USER);

        User findedUser = testEntityManager.find(User.class, savedUser.getId());

        assertNotNull(findedUser);
        assertEquals(findedUser.getUsername(), USER.getUsername());
        assertEquals(findedUser.getEmail(), USER.getEmail());
        assertEquals(findedUser.getPassword(), USER.getPassword());
    }

    @Test
    void save_NullAttributes_ThrowsException() {
        User user = new User();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(user);
            testEntityManager.flush();
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "12345678901234567"})
    void save_InvalidStringAttributes_ThrowsException(String value) {
        User user = User.builder()
                .username(value)
                .email(value)
                .password(value)
                .build();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(user);
            testEntityManager.flush();
        });
    }

}