package nl.fontys.restaurantreservations.api.controllers;

import jakarta.validation.Valid;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;
import nl.fontys.restaurantreservations.api.dtos.TableDTO;
import nl.fontys.restaurantreservations.api.dtos.requests.table.CreateTableRequest;
import nl.fontys.restaurantreservations.api.dtos.requests.table.DeleteTableRequest;
import nl.fontys.restaurantreservations.api.dtos.requests.table.UpdateTableRequest;
import nl.fontys.restaurantreservations.bll.services.TableService;

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

    @GetMapping("/active")
    public ResponseEntity<ApiMessage> getAllActiveTables()
    {
        return tableService.getAllActiveTables();
    }

    @GetMapping
    public ResponseEntity<ApiMessage> getAllExistingTables() //Existing means any table without the Removed status.
    {
        return tableService.getAllExistingTables();
    }

    @PostMapping
    public ResponseEntity<ApiMessage> createTable(@Valid @RequestBody CreateTableRequest request)
    {
        TableDTO dto = new TableDTO(request.tableNumber(), request.capacity(), request.status());
        return tableService.createTable(dto);
    }

    @PutMapping
    public ResponseEntity<ApiMessage> updateTable(@Valid @RequestBody UpdateTableRequest request)
    {
        TableDTO dto = new TableDTO(request.newTableNumber(), request.capacity(), request.status());
        return tableService.updateTable(request.oldTableNumber(), dto);
    }

    @DeleteMapping
    public ResponseEntity<ApiMessage> deleteTable(@Valid @RequestBody DeleteTableRequest request)
    {
        return tableService.deleteTable(request.tableNumber());
    }
}
