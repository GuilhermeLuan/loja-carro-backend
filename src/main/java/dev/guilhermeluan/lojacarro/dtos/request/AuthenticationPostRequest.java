package dev.guilhermeluan.lojacarro.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationPostRequest (
        @NotBlank(message = "The field 'login' is required")
        String login,
        @NotBlank(message = "The field 'login' is required")
        String password
) {
}
