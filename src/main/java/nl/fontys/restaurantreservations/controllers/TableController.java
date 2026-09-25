package nl.fontys.restaurantreservations.controllers;

import nl.fontys.restaurantreservations.dtos.requests.table.CreateTableRequest;
import nl.fontys.restaurantreservations.dtos.requests.table.DeleteTableRequest;
import nl.fontys.restaurantreservations.dtos.requests.table.UpdateTableRequest;
import nl.fontys.restaurantreservations.services.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/tables")
public class TableController
{
    private final TableService tableService;

    public TableController(TableService tableService)
    {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<?> getAllActiveTables()
    {
        return tableService.getAllActiveTables();
    }

    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody CreateTableRequest request)
    {
        return tableService.createTable(request.tableNumber(), request.capacity(), request.status());
    }

    @PutMapping
    public ResponseEntity<?> updateTable(@RequestBody UpdateTableRequest request)
    {
        return tableService.updateTable(request.oldTableNumber(), request.newTableNumber(), request.capacity(), request.status());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTable(@RequestBody DeleteTableRequest request)
    {
        return tableService.deleteTable(request.tableNumber());
    }
}
