package nl.fontys.restaurantreservations.dtos;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.entities.TableEntity;

public record TableDTO(
        String tableNumber,
        Integer capacity,
        TableStatus status
) {
    public TableDTO(TableEntity table) {
        this(
                table.getTableNumber(),
                table.getCapacity(),
                table.getStatus()
        );
    }
}
