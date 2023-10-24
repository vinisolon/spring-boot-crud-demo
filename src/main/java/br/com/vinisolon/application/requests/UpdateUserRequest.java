package br.com.vinisolon.application.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotNull Long id,
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email
) {
}
