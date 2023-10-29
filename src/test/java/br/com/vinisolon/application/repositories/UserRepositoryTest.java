package br.com.vinisolon.application.repositories;

import br.com.vinisolon.application.entities.User;
import org.junit.jupiter.api.Test;
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
    void save_InvalidData_ThrowsException() {
        User emptyUser = new User();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(emptyUser);
            testEntityManager.flush();
        });

        User blankUser = User.builder()
                .username("")
                .email("")
                .password("")
                .build();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(blankUser);
            testEntityManager.flush();
        });

        User shortPasswordUser = User.builder()
                .username("a")
                .email("a")
                .password("123")
                .build();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(shortPasswordUser);
            testEntityManager.flush();
        });

        User longPasswordUser = User.builder()
                .username("a")
                .email("a")
                .password("12345678901234567")
                .build();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(longPasswordUser);
            testEntityManager.flush();
        });


        // Não deveria lançar RuntimeException e falhar o teste
        User validUser = User.builder()
                .username("vinicius")
                .email("vinicius@email.com")
                .password("123456789")
                .build();

        assertThrows(RuntimeException.class, () -> {
            userRepository.save(validUser);
            testEntityManager.flush();
        });
    }

}