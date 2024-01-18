package br.com.vinisolon.application.requests;

import br.com.vinisolon.application.validation.CustomValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static br.com.vinisolon.application.enums.ValidationMessagesEnum.USERNAME_IS_REQUIRED;

public record CreateUserRequest(
        @CustomValidator(USERNAME_IS_REQUIRED) String username,
        @NotBlank @Size(min = 6, max = 16) String password,
        @NotBlank @Email String email
) {
}
