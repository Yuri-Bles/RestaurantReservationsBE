package nl.fontys.restaurantreservations.controllers;

import nl.fontys.restaurantreservations.dtos.CreateTableRequest;
import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.services.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController
{
    private final TableService tableService;

    public TableController(TableService tableService)
    {
        this.tableService = tableService;
    }

    @GetMapping
    public List<TableDTO> getAllActiveTables()
    {
        return tableService.getAllActiveTables();
    }

    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody CreateTableRequest request)
    {
        return tableService.createTable(request.tableNumber(), request.capacity(), request.status());
    }
}
