package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.dtos.ApiMessage;
import org.springframework.http.ResponseEntity;

public final class ResponseEntityCreator
{
    public static ResponseEntity<?> returnResponseEntity(Integer status, String message)
    {
        return ResponseEntity
                .status(status)
                .body(new ApiMessage(status, message));
    }
}
