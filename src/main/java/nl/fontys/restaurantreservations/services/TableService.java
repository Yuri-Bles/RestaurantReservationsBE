package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.TableModel;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity createTable(String tableNumber, Integer capacity, TableStatus status)
    {
        TableModel model = new TableModel(tableNumber, capacity, status);

        if (tableNumber.isBlank())
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table number is required");
        }
        else if (!isTableNumberAvailable(tableNumber))
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table number must be unique");
        }

        repo.save(model);
        return null;
    }

    private boolean isTableNumberAvailable(String tableNumber)
    {
        Optional<TableModel> activeResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active);
        Optional<TableModel> inactiveResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive);
        return (activeResult.isEmpty() && inactiveResult.isEmpty());
    }
}
