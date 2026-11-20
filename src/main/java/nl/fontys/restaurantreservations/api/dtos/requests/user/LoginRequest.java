package nl.fontys.restaurantreservations.api.dtos.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "E-mail is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {
}
