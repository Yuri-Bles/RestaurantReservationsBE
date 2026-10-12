package nl.fontys.restaurantreservations.interfaces;

import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface JPATableRepository extends JpaRepository<TableEntity, Long>
{
    List<TableEntity> findAllByStatus(TableStatus status);

    TableEntity save(TableEntity table);

    Optional<TableEntity> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
