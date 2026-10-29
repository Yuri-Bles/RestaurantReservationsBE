package nl.fontys.restaurantreservations.dal.interfaces.repo;

import nl.fontys.restaurantreservations.bll.domains.TableDomain;
import nl.fontys.restaurantreservations.bll.enums.TableStatus;

import java.util.List;
import java.util.Optional;

public interface TableRepository {
    List<TableDomain> findAllByStatus(TableStatus status);

    Optional<TableDomain> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
