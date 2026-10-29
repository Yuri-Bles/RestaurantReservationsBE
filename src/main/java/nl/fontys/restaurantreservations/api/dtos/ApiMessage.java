package nl.fontys.restaurantreservations.api.dtos;

public record ApiMessage(
        int status,
        String message,
        Object body
) {
}
