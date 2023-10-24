package br.com.vinisolon.application.services;

import br.com.vinisolon.application.mappers.UserMapper;
import br.com.vinisolon.application.repositories.UserRepository;
import br.com.vinisolon.application.responses.SuccessResponse;
import br.com.vinisolon.application.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.vinisolon.application.common.MockConstants.DEFAULT_LONG;
import static br.com.vinisolon.application.common.MockConstants.INVALID_UPDATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.USER;
import static br.com.vinisolon.application.common.MockConstants.USER_RESPONSE;
import static br.com.vinisolon.application.common.MockConstants.VALID_CREATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.VALID_UPDATE_USER_REQUEST;
import static br.com.vinisolon.application.enums.Messages.DEFAULT_SUCCESS_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    void whenCreateWithExistentEmailThrowException() {
        when(userRepository.existsByEmail(any())).thenReturn(Boolean.TRUE);

        assertThrows(RuntimeException.class, () -> userService.create(VALID_CREATE_USER_REQUEST));
    }

    @Test
    void whenCreateWithValidRequestReturnSuccessResponse() {
        when(userRepository.existsByEmail(any())).thenReturn(Boolean.FALSE);

        when(userMapper.requestToEntity(any())).thenReturn(USER);

        when(userRepository.save(any())).thenReturn(USER);

        SuccessResponse response = userService.create(VALID_CREATE_USER_REQUEST);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.message());
    }

    @Test
    void whenUpdateWithUnexistentUserIdThrowException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.update(VALID_UPDATE_USER_REQUEST));
    }

    @Test
    void whenUpdateWithExistentUserEmailThrowException() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        when(userRepository.existsByEmail(any())).thenReturn(Boolean.TRUE);

        assertThrows(RuntimeException.class, () -> userService.update(INVALID_UPDATE_USER_REQUEST));
    }

    @Test
    void whenUpdateWithValidRequestReturnSuccessResponse() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        doNothing().when(userMapper).updateEntity(any(), any());

        when(userRepository.save(any())).thenReturn(USER);

        SuccessResponse response = userService.update(VALID_UPDATE_USER_REQUEST);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.message());
    }

    @Test
    void whenDeleteReturnSuccessResponse() {
        doNothing().when(userRepository).deleteById(any());

        SuccessResponse response = userService.delete(DEFAULT_LONG);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.message());
    }

    @Test
    void whenGetWithUnexistentUserIdThrowException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.get(DEFAULT_LONG));
    }

    @Test
    void whenGetWithExistentUserIdReturnUserResponse() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        when(userMapper.entityToResponse(any())).thenReturn(USER_RESPONSE);

        UserResponse response = userService.get(DEFAULT_LONG);

        assertEquals(USER_RESPONSE, response);
    }

    @Test
    void whenGetAllReturnUserResponseList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(USER));

        when(userMapper.entityToResponse(any())).thenReturn(USER_RESPONSE);

        List<UserResponse> response = userService.getAll();

        assertFalse(response.isEmpty());
        assertEquals(USER_RESPONSE, response.get(0));
    }

}