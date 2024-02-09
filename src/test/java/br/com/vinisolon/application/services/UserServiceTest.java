package br.com.vinisolon.application.services;

import br.com.vinisolon.application.mappers.UserMapper;
import br.com.vinisolon.application.repositories.UserRepository;
import br.com.vinisolon.application.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static br.com.vinisolon.application.common.MockConstants.CREATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.DEFAULT_LONG;
import static br.com.vinisolon.application.common.MockConstants.UPDATE_USER_REQUEST;
import static br.com.vinisolon.application.common.MockConstants.UPDATE_USER_REQUEST_DIFFERENT_EMAIL;
import static br.com.vinisolon.application.common.MockConstants.USER;
import static br.com.vinisolon.application.common.MockConstants.USER_RESPONSE;
import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.DEFAULT_SUCCESS_MESSAGE;
import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.USER_ALREADY_EXISTS_WITH_EMAIL;
import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.USER_NOT_FOUND;
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
    void create_ExistingEmail_ThrowsException() {
        when(userRepository.existsByEmail(any())).thenReturn(Boolean.TRUE);

        var exception = assertThrows(RuntimeException.class, () -> userService.create(CREATE_USER_REQUEST));

        assertEquals(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage(), exception.getMessage());
    }

    @Test
    void create_ValidRequest_ReturnsSuccessResponse() {
        when(userRepository.existsByEmail(any())).thenReturn(Boolean.FALSE);

        when(userMapper.requestToEntity(any())).thenReturn(USER);

        when(userRepository.save(any())).thenReturn(USER);

        var response = userService.create(CREATE_USER_REQUEST);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.getMessage());
    }

    @Test
    void update_UnexistingId_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> userService.update(UPDATE_USER_REQUEST));

        assertEquals(USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void update_ExistingEmail_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        when(userRepository.existsByEmail(any())).thenReturn(Boolean.TRUE);

        var exception = assertThrows(RuntimeException.class, () -> userService.update(UPDATE_USER_REQUEST_DIFFERENT_EMAIL));

        assertEquals(USER_ALREADY_EXISTS_WITH_EMAIL.getMessage(), exception.getMessage());
    }

    @Test
    void update_ValidRequest_ReturnsSuccessResponse() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        doNothing().when(userMapper).updateEntity(any(), any());

        when(userRepository.save(any())).thenReturn(USER);

        var response = userService.update(UPDATE_USER_REQUEST_DIFFERENT_EMAIL);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.getMessage());
    }

    @Test
    void delete_ValidId_ReturnsSuccessResponse() {
        doNothing().when(userRepository).deleteById(any());

        var response = userService.delete(DEFAULT_LONG);

        assertEquals(DEFAULT_SUCCESS_MESSAGE.getMessage(), response.getMessage());
    }

    @Test
    void get_UnexistingId_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        var exception = assertThrows(RuntimeException.class, () -> userService.get(DEFAULT_LONG));

        assertEquals(USER_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void get_ExistingId_ReturnsUserResponse() {
        when(userRepository.findById(any())).thenReturn(Optional.of(USER));

        when(userMapper.entityToResponse(any())).thenReturn(USER_RESPONSE);

        var response = userService.get(DEFAULT_LONG);

        assertEquals(USER_RESPONSE, response);
    }

    @Test
    void getAll_ExistingData_ReturnsListOfUserResponse() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(USER));

        when(userMapper.entityToResponse(any())).thenReturn(USER_RESPONSE);

        List<UserResponse> response = userService.getAll();

        assertFalse(response.isEmpty());
        assertEquals(USER_RESPONSE, response.get(0));
    }

}