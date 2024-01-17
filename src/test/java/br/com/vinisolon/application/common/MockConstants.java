package br.com.vinisolon.application.common;

import br.com.vinisolon.application.entities.User;
import br.com.vinisolon.application.requests.CreateUserRequest;
import br.com.vinisolon.application.requests.UpdateUserRequest;
import br.com.vinisolon.application.responses.UserResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MockConstants {

    public static final LocalDateTime DEFAULT_LOCALDATETIME = LocalDateTime.now();
    public static final String INVALID_STRING = "gnirtS";
    public static final String DEFAULT_STRING = "String";
    public static final long DEFAULT_LONG = 1L;

    public static final User USER = User.builder()
            .email(DEFAULT_STRING)
            .username(DEFAULT_STRING)
            .password(DEFAULT_STRING)
            .build();

    public static final CreateUserRequest VALID_CREATE_USER_REQUEST = new CreateUserRequest(
            DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING
    );

    public static final UpdateUserRequest VALID_UPDATE_USER_REQUEST = new UpdateUserRequest(
            DEFAULT_LONG, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING
    );

    public static final UpdateUserRequest INVALID_UPDATE_USER_REQUEST = new UpdateUserRequest(
            DEFAULT_LONG, DEFAULT_STRING, DEFAULT_STRING, INVALID_STRING
    );

    public static final UserResponse USER_RESPONSE = new UserResponse(
            DEFAULT_STRING, DEFAULT_STRING, DEFAULT_STRING, DEFAULT_LOCALDATETIME, DEFAULT_LOCALDATETIME
    );

    public static User getUserInstance() {
        return User.builder()
                .email(DEFAULT_STRING)
                .username(DEFAULT_STRING)
                .password(DEFAULT_STRING)
                .build();
    }

}
