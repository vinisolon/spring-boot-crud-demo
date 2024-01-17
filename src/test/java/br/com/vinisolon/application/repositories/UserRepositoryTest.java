package br.com.vinisolon.application.repositories;

import br.com.vinisolon.application.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static br.com.vinisolon.application.common.MockConstants.getUserInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User userEntity;

    @BeforeEach
    void setUp() {
        userEntity = getUserInstance();
    }

    @Test
    void save_ValidData_ReturnsUserEntity() {
        User savedUser = userRepository.save(userEntity);
        User findedUser = testEntityManager.find(User.class, savedUser.getId());

        assertNotNull(findedUser);
        assertEquals(savedUser, findedUser);
    }

    @Test
    void save_NullAttributes_ThrowsException() {
        User nullUser = new User();

        assertThrows(RuntimeException.class, () -> userRepository.save(nullUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "12345678901234567"})
    void save_InvalidStringAttributes_ThrowsException(String value) {
        userEntity = User.builder()
                .username(value)
                .email(value)
                .password(value)
                .build();

        assertThrows(RuntimeException.class, () -> userRepository.save(userEntity));
    }

    @Test
    void save_ExistingEmail_ThrowsException() {
        User persistedUser = testEntityManager.persist(userEntity);

        persistedUser.setId(null);

        assertThrows(RuntimeException.class, () -> userRepository.saveAndFlush(persistedUser));
    }

    @Test
    void existsByEmail_ExistingEmail_True() {
        testEntityManager.persist(userEntity);

        assertTrue(userRepository.existsByEmail(userEntity.getEmail()));
    }

    @Test
    void existsByEmail_ExistingEmail_False() {
        assertFalse(userRepository.existsByEmail(userEntity.getEmail()));
    }

}