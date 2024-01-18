package br.com.vinisolon.application.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionMessagesEnum {

    DEFAULT_SUCCESS_MESSAGE("Success."),
    DEFAULT_ERROR_MESSAGE("Unexpected error."),
    USER_NOT_FOUND("User not found."),
    USER_ALREADY_EXISTS_WITH_EMAIL("Already exists an user with this e-mail.");

    private final String message;

}
