package nl.fontys.restaurantreservations.dal.interfaces;

import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.dal.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface JPATableRepository extends JpaRepository<TableEntity, Long>
{
    List<TableEntity> findAllByStatus(TableStatus status);

    TableEntity save(TableEntity table);

    Optional<TableEntity> findByTableNumberAndStatus(String tableNumber, TableStatus status);
}
