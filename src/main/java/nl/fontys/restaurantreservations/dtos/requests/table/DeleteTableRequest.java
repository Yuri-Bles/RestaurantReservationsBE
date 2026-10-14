package nl.fontys.restaurantreservations.dtos.requests.table;

import jakarta.validation.constraints.NotBlank;

public record DeleteTableRequest(
        @NotBlank(message = "Table number is required")
        String tableNumber
) {
}