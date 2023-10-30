package br.com.vinisolon.application.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotNull Long id,
        @NotBlank String username,
        @NotBlank @Size(min = 6, max = 16) String password,
        @NotBlank @Email String email
) {
}
