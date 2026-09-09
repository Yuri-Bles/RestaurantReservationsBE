package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.TableModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableService
{
    private final ITableRepository repo;

    public TableService(ITableRepository repo)
    {
        this.repo = repo;
    }

    public List<TableDTO> getAllActiveTables()
    {
        return repo.findAllByStatus(TableStatus.Active)
                .stream()
                .map(TableDTO::new)
                .toList();
    }

    public void createTable(String tableNumber, Integer capacity, String statusString)
    {
        TableStatus status = TableStatus.valueOf(statusString);

        TableModel model = new TableModel(tableNumber, capacity, status);

        repo.save(model);
    }
}
