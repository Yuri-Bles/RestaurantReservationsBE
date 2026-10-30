package nl.fontys.restaurantreservations.api.dtos.requests.table;

import jakarta.validation.constraints.NotBlank;

public record DeleteTableRequest(
        @NotBlank(message = "Table number is required")
        String tableNumber
) {
}