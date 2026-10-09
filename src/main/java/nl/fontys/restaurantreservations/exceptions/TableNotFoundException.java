package nl.fontys.restaurantreservations.exceptions;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
}
