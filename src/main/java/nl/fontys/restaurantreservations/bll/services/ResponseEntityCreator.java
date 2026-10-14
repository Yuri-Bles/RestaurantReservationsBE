package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;

import org.springframework.http.ResponseEntity;

public final class ResponseEntityCreator
{
    public static ResponseEntity<?> returnResponseEntity(Integer status, String message)
    {
        return ResponseEntity
                .status(status)
                .body(new ApiMessage(status, message, null));
    }

    public static ResponseEntity<?> returnResponseEntity(Integer status, String message, Object body)
    {
        return ResponseEntity
                .status(status)
                .body(new ApiMessage(status, message, body));
    }
}
