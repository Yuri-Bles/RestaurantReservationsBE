package nl.fontys.restaurantreservations.dtos;

public record ApiMessage(
        int status,
        String message
) {
}
