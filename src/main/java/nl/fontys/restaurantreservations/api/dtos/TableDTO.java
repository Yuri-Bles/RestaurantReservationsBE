package nl.fontys.restaurantreservations.api.dtos;

import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.dal.entities.TableEntity;

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
