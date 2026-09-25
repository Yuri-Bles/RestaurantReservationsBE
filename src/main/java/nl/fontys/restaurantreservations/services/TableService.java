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

    public ResponseEntity<?> getAllActiveTables()
    {
        List<TableDTO> tables = repo.findAllByStatus(TableStatus.Active)
                .stream()
                .map(TableDTO::new)
                .toList();

        if (tables.isEmpty())
        { return ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables"); }

        return ResponseEntityCreator.returnResponseEntity(200, "Got tables successfully", tables);
    }

    public ResponseEntity<?> createTable(String tableNumber, Integer capacity, TableStatus status)
    {
        if (tableNumber.isBlank() || !isTableNumberAvailable(tableNumber))
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid.");
        }

        if (capacity <= 0)
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table capacity must be above 0.");
        }

        TableModel model = new TableModel(tableNumber, capacity, status);
        repo.save(model);
        return ResponseEntityCreator.returnResponseEntity(201, "Table successfully created.");
    }

    public ResponseEntity<?> updateTable(String oldTableNumber, String newTableNumber, Integer capacity, TableStatus status)
    {
        TableModel model;

        try
        { model = getTableByTableNumber(oldTableNumber); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(400, "Table doesn't exist"); }

        if (newTableNumber.isBlank())
        { return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid."); }

        if (capacity <= 0)
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table capacity must be above 0.");
        }

        model.setTableNumber(newTableNumber);
        model.setCapacity(capacity);
        model.setStatus(status);

        try
        { repo.save(model); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later. " + ex); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully updated.");
    }

    public ResponseEntity<?> deleteTable(String tableNumber)
    {
        TableModel model;
        try
        { model = getTableByTableNumber(tableNumber); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(400, "Table does not exist."); }

        model.setStatus(TableStatus.Removed);

        try
        { repo.save(model); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later."); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully removed.");
    }

    private boolean isTableNumberAvailable(String tableNumber)
    {
        Optional<TableModel> activeResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active);
        Optional<TableModel> inactiveResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive);
        return (activeResult.isEmpty() && inactiveResult.isEmpty());
    }

    private TableModel getTableByTableNumber(String tableNumber)
    {
        return repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active).or(() ->
                repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive)).orElseThrow();
    }
}
