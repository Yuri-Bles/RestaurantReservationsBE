package nl.fontys.restaurantreservations.dtos.requests.table;

import jakarta.validation.constraints.*;
import nl.fontys.restaurantreservations.enums.TableStatus;

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