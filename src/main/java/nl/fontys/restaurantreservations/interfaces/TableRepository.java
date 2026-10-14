package nl.fontys.restaurantreservations.interfaces;

import nl.fontys.restaurantreservations.domains.TableDomain;
import nl.fontys.restaurantreservations.enums.TableStatus;

import java.util.List;
import java.util.Optional;

public interface TableRepository {
    List<TableDomain> findAllByStatus(TableStatus status);

    Optional<TableDomain> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
