package nl.fontys.restaurantreservations.dtos.requests.table;

import nl.fontys.restaurantreservations.enums.TableStatus;

public record DeleteTableRequest(
        String tableNumber,
        TableStatus status
) {
}