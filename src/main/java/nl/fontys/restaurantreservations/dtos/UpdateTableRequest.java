package nl.fontys.restaurantreservations.dtos;

import nl.fontys.restaurantreservations.enums.TableStatus;

public record UpdateTableRequest(
        String oldTableNumber,
        String newTableNumber,
        Integer capacity,
        TableStatus status
) {
}
