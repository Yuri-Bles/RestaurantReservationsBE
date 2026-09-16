package nl.fontys.restaurantreservations.interfaces;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.models.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ITableRepository extends JpaRepository<TableModel, Long>
{
    List<TableModel> findAllByStatus(TableStatus status);
    Optional<TableModel> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
