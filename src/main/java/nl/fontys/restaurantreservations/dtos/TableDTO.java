package nl.fontys.restaurantreservations.dtos;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.models.TableModel;

public record TableDTO(
        Long id,
        String tableNumber,
        Integer capacity,
        TableStatus status
) {
    public TableDTO(TableModel table) {
        this(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getStatus()
        );
    }
}
