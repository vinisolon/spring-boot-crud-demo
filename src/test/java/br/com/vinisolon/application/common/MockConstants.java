package br.com.vinisolon.application.common;

import br.com.vinisolon.application.entities.User;
import br.com.vinisolon.application.requests.UserRequest;
import br.com.vinisolon.application.responses.MessageResponse;
import br.com.vinisolon.application.responses.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static br.com.vinisolon.application.enums.ExceptionMessagesEnum.DEFAULT_SUCCESS_MESSAGE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockConstants {

    public static final LocalDateTime DEFAULT_LOCALDATETIME = LocalDateTime.now();
    public static final String BLANK_STRING = "";
    public static final String INVALID_STRING = "gnirtS";
    public static final String DEFAULT_STRING = "String";
    public static final String DEFAULT_EMAIL = "test@email.com.br";
    public static final long DEFAULT_LONG = 1L;

    public static final User USER = User.builder()
            .email(DEFAULT_EMAIL)
            .username(DEFAULT_STRING)
            .password(DEFAULT_STRING)
            .build();

    public static final UserRequest VALID_CREATE_USER_REQUEST = new UserRequest(
            null, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_EMAIL
    );

    public static final UserRequest BLANK_USER_REQUEST = new UserRequest(
            null, BLANK_STRING, BLANK_STRING, BLANK_STRING
    );

    public static final UserRequest NULL_USER_REQUEST = new UserRequest(
            null, null, null, null
    );

    public static final UserRequest VALID_UPDATE_USER_REQUEST = new UserRequest(
            DEFAULT_LONG, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_EMAIL
    );

    public static final UserRequest INVALID_UPDATE_USER_REQUEST = new UserRequest(
            DEFAULT_LONG, DEFAULT_STRING, DEFAULT_STRING, INVALID_STRING
    );

    public static final UserResponse USER_RESPONSE = new UserResponse(
            DEFAULT_STRING, DEFAULT_STRING, DEFAULT_LOCALDATETIME, DEFAULT_LOCALDATETIME
    );

    public static final MessageResponse DEFAULT_SUCCESS_RESPONSE = new MessageResponse(DEFAULT_SUCCESS_MESSAGE.getMessage());

    public static User getUserInstance() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .username(DEFAULT_STRING)
                .password(DEFAULT_STRING)
                .build();
    }

}
