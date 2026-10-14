package nl.fontys.restaurantreservations.dtos.requests.table;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import nl.fontys.restaurantreservations.enums.TableStatus;

public record UpdateTableRequest(
        @NotBlank(message = "Old table number is required")
        String oldTableNumber,

        @NotBlank(message = "New table number is required")
        String newTableNumber,

        @NotNull(message = "Table capacity is required")
        @Min(value = 1, message = "Table capacity must be above 0")
        Integer capacity,

        @NotNull(message = "Table status is required")
        TableStatus status
) {
}
