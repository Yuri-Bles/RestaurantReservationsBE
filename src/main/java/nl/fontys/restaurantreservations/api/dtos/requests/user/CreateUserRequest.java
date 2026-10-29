package nl.fontys.restaurantreservations.api.dtos.requests.user;

import jakarta.validation.constraints.NotBlank;
import nl.fontys.restaurantreservations.bll.enums.UserRole;

public record CreateUserRequest(
        @NotBlank(message = "E-mail is required")
        String email,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Role is required")
        UserRole role
) {
}
