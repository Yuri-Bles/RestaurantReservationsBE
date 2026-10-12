package nl.fontys.restaurantreservations.interfaces;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.models.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ITableRepository extends JpaRepository<TableEntity, Long>
{
    List<TableEntity> findAllByStatus(TableStatus status);

    Optional<TableEntity> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
