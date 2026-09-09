package nl.fontys.restaurantreservations.repositories;

import jakarta.persistence.EntityManager;
import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public abstract class TableRepository implements ITableRepository
{
    private final EntityManager entityManager;

    public TableRepository(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

//    public List<TableDTO> findAllByStatus(TableStatus status)
//    {
//        List<TableModel> result = entityManager
//                .createQuery("SELECT t FROM TableModel t WHERE t.status = :status", TableModel.class)
//                .setParameter("status", status)
//                .getResultList();
//
//        if (result.isEmpty())
//        {
//            return null;
//        }
//
//        return result.stream()
//                .map(TableDTO::new)
//                .toList();
//    }
}
