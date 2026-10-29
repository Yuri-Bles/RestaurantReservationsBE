package nl.fontys.restaurantreservations.api.dtos;

import nl.fontys.restaurantreservations.bll.enums.UserRole;

public record UserDTO(
        String email,
        String name,
        String password,
        UserRole role
) {
}
