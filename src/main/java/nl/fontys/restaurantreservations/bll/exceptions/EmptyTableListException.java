package nl.fontys.restaurantreservations.bll.exceptions;

public class EmptyTableListException extends RuntimeException {
    public EmptyTableListException(String message) {
        super(message);
    }
}
