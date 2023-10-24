package br.com.vinisolon.application.responses;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String password,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
