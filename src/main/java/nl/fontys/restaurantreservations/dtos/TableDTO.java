package nl.fontys.restaurantreservations.dtos;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.entities.TableEntity;

public record TableDTO(
        Long id,
        String tableNumber,
        Integer capacity,
        TableStatus status
) {
    public TableDTO(TableEntity table) {
        this(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getStatus()
        );
    }

    public TableDTO(String tableNumber, Integer capacity, TableStatus status) {
        this(null, tableNumber, capacity, status);
    }
}
