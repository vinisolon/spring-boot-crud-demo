package br.com.vinisolon.application.repositories;

import br.com.vinisolon.application.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static br.com.vinisolon.application.common.MockConstants.getUserInstance;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
    void save_ValidData_ReturnsEntity() {
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
        User savedUser = testEntityManager.persist(userEntity);
        testEntityManager.detach(savedUser);
        savedUser.setId(null);

        assertThrows(RuntimeException.class, () -> userRepository.save(savedUser));
    }

    @Test
    void existsByEmail_ExistingEmail_ReturnsTrue() {
        testEntityManager.persist(userEntity);

        assertTrue(userRepository.existsByEmail(userEntity.getEmail()));
    }

    @Test
    void existsByEmail_UnexistingEmail_ReturnsFalse() {
        assertFalse(userRepository.existsByEmail(userEntity.getEmail()));
    }

    @Test
    void delete_WithExistingId_RemoveUserFromDatabase() {
        User savedUser = testEntityManager.persistFlushFind(userEntity);
        userRepository.deleteById(savedUser.getId());
        User deletedUser = testEntityManager.find(User.class, savedUser.getId());

        assertNull(deletedUser);
    }

    @Test
    void delete_WithUnexistingId_DoesNothing() {
        assertDoesNotThrow(() -> userRepository.deleteById(1L));
    }

    @Test
    void findById_ExistingId_ReturnsEntity() {
        User savedUser = testEntityManager.persistFlushFind(userEntity);
        Optional<User> findedUser = userRepository.findById(savedUser.getId());

        assertTrue(findedUser.isPresent());
        assertEquals(savedUser, findedUser.get());
    }

    @Test
    void findById_UnexistingId_ReturnsEmpty() {
        Optional<User> findedUser = userRepository.findById(1L);

        assertTrue(findedUser.isEmpty());
    }

    @Test
    void findAll_ReturnsListOfUser() {
        testEntityManager.persistAndFlush(userEntity);
        List<User> findedUserList = userRepository.findAll();

        assertNotNull(findedUserList);
        assertFalse(findedUserList.isEmpty());
        assertEquals(1, findedUserList.size());
    }

    @Test
    void findAll_ReturnsEmptyList() {
        List<User> findedUserList = userRepository.findAll();

        assertNotNull(findedUserList);
        assertTrue(findedUserList.isEmpty());
    }

}