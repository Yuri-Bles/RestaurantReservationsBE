package nl.fontys.restaurantreservations.api.dtos.requests.table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import nl.fontys.restaurantreservations.bll.enums.TableStatus;

public record CreateTableRequest(

        @NotBlank(message = "Table number is required")
        String tableNumber,

        @NotNull(message = "Table capacity is required")
        @Min(value = 1, message = "Table capacity must be above 0")
        Integer capacity,

        @NotNull(message = "Table status is required")
        TableStatus status
) {
}