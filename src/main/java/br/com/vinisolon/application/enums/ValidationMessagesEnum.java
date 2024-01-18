package br.com.vinisolon.application.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ValidationMessagesEnum {

    USERNAME_IS_REQUIRED("Username is required.");

    private final String message;

}
