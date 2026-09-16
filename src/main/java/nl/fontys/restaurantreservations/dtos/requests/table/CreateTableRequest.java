package nl.fontys.restaurantreservations.dtos.requests.table;

import nl.fontys.restaurantreservations.enums.TableStatus;

public record CreateTableRequest(
        String tableNumber,
        Integer capacity,
        TableStatus status
) {
}