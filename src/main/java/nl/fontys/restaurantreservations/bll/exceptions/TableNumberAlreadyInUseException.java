package nl.fontys.restaurantreservations.bll.exceptions;

public class TableNumberAlreadyInUseException extends RuntimeException {
    public TableNumberAlreadyInUseException(String message) {
        super(message);
    }
}
