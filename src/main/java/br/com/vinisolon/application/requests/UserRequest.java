package br.com.vinisolon.application.requests;

import br.com.vinisolon.application.validation.groups.CreateGroup;
import br.com.vinisolon.application.validation.groups.UpdateGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotNull(message = "{id.required}", groups = UpdateGroup.class)
        Long id,

        @NotBlank(message = "{username.required}", groups = CreateGroup.class)
        String username,

        @NotBlank(message = "{password.required}", groups = CreateGroup.class)
        @Size(min = 6, max = 16, message = "{password.invalid.size}", groups = {CreateGroup.class, UpdateGroup.class})
        String password,

        @NotBlank(message = "{password.required}", groups = CreateGroup.class)
        @Email(message = "{email.invalid.format}", groups = {CreateGroup.class, UpdateGroup.class})
        String email
) {
}
