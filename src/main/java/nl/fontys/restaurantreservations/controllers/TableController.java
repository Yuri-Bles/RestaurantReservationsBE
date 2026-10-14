package nl.fontys.restaurantreservations.controllers;

import jakarta.validation.Valid;
import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.dtos.requests.table.CreateTableRequest;
import nl.fontys.restaurantreservations.dtos.requests.table.DeleteTableRequest;
import nl.fontys.restaurantreservations.dtos.requests.table.UpdateTableRequest;
import nl.fontys.restaurantreservations.services.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/tables")
@CrossOrigin(origins = "http://localhost:5173")
public class TableController
{
    private final TableService tableService;

    public TableController(TableService tableService)
    {
        this.tableService = tableService;
    }

    @GetMapping("/active")
    public ResponseEntity<?> getAllActiveTables()
    {
        return tableService.getAllActiveTables();
    }

    @GetMapping("/existing")
    public ResponseEntity<?> getAllExistingTables() //Existing means any table without the Removed status.
    {
        return tableService.getAllExistingTables();
    }

    @PostMapping
    public ResponseEntity<?> createTable(@Valid @RequestBody CreateTableRequest request)
    {
        TableDTO dto = new TableDTO(request.tableNumber(), request.capacity(), request.status());
        return tableService.createTable(dto);
    }

    @PutMapping
    public ResponseEntity<?> updateTable(@Valid @RequestBody UpdateTableRequest request)
    {
        TableDTO dto = new TableDTO(request.newTableNumber(), request.capacity(), request.status());
        return tableService.updateTable(request.oldTableNumber(), dto);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTable(@Valid @RequestBody DeleteTableRequest request)
    {
        return tableService.deleteTable(request.tableNumber());
    }
}
