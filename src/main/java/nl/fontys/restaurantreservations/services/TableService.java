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

    public ResponseEntity<?> createTable(String tableNumber, Integer capacity, TableStatus status)
    {
        if (!tableNumber.isBlank() && isTableNumberAvailable(tableNumber))
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid.");
        }

        TableModel model = new TableModel(tableNumber, capacity, status);
        repo.save(model);
        return ResponseEntityCreator.returnResponseEntity(201, "Table successfully created.");
    }

    public ResponseEntity<?> updateTable(String oldTableNumber, String newTableNumber, Integer capacity, TableStatus status)
    {
        TableModel model;

        try
        { model = getTableIdTableNumber(oldTableNumber); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(400, "Table doesn't exist"); }

        if (newTableNumber.isBlank())
        { return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid."); }

        model.setTableNumber(newTableNumber);
        model.setCapacity(capacity);
        model.setStatus(status);
        
        try
        { repo.save(model); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later. " + ex); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully updated.");
    }

    private boolean isTableNumberAvailable(String tableNumber)
    {
        Optional<TableModel> activeResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active);
        Optional<TableModel> inactiveResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive);
        return (activeResult.isEmpty() && inactiveResult.isEmpty());
    }

    private TableModel getTableIdTableNumber(String tableNumber)
    {
        return repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active).or(() ->
                repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive)).orElseThrow();
    }
}
