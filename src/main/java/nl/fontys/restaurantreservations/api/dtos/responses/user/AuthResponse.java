package nl.fontys.restaurantreservations.api.dtos.responses.user;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
}
