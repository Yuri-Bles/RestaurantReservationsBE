package nl.fontys.restaurantreservations.api.dtos.requests.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "E-mail is required")
        String email,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be between 8 and 100 characters")
        String password
) {
}
