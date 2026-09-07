package nl.fontys.restaurantreservations.interfaces;

import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.models.TableModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ITableRepository extends JpaRepository<TableModel, Long>
{
    public List<TableDTO> getAllActiveTables();
}
