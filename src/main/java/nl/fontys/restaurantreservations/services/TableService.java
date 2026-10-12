package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.dtos.*;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.exceptions.TableNotFoundException;
import nl.fontys.restaurantreservations.interfaces.JPATableRepository;
import nl.fontys.restaurantreservations.entities.TableEntity;
import nl.fontys.restaurantreservations.mappers.table.TableDomainDTOMapper;

import nl.fontys.restaurantreservations.repositories.TableRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TableService
{
    private final JPATableRepository repo;
    private final TableRepositoryImpl repo2;

    private final TableDomainDTOMapper mapper = new TableDomainDTOMapper();

    public TableService(JPATableRepository repo, TableRepositoryImpl repo2)
    {
        this.repo = repo;
        this.repo2 = repo2;
    }

    public ResponseEntity<?> getAllActiveTables()
    {
        List<TableDomain> tables = repo2.findAllByStatus(TableStatus.Active);

        if (tables.isEmpty())
        { return ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables"); }

        List<TableDTO> dtos = mapper.listToDTO(tables);

        return ResponseEntityCreator.returnResponseEntity(200, "Got tables successfully", dtos);
    }

    public ResponseEntity<?> getAllExistingTables()
    {
        List<TableDomain> tables = Stream.concat(
                        repo2.findAllByStatus(TableStatus.Active).stream(),
                        repo2.findAllByStatus(TableStatus.Inactive).stream())
                .sorted(
                        Comparator.comparing(TableDomain::getStatus)
                                .thenComparing(TableDomain::getTableNumber)
                )
                .toList();

        if (tables.isEmpty())
        { return ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables"); }

        List<TableDTO> dtos = mapper.listToDTO(tables);

        return ResponseEntityCreator.returnResponseEntity(200, "Got tables successfully", dtos);
    }

    public ResponseEntity<?> createTable(TableDTO dto)
    {
        if (dto.tableNumber().isBlank() || !isTableNumberAvailable(dto.tableNumber()))
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid.");
        }

        if (dto.capacity() <= 0)
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table capacity must be above 0.");
        }

        TableEntity model = new TableEntity(dto.tableNumber(), dto.capacity(), dto.status());

        try {
            repo.save(model);
        }
        catch (Exception ex) {
            return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong internally.");
        }
        return ResponseEntityCreator.returnResponseEntity(201, "Table successfully created.");
    }

    public ResponseEntity<?> updateTable(String oldTableNumber, TableDTO dto)
    {
        TableEntity model;

        try
        { model = getTableByTableNumber(oldTableNumber); }
        catch (TableNotFoundException ex)
        { return ResponseEntityCreator.returnResponseEntity(404, "Table doesn't exist"); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Please try again later. " + ex); }

        if (dto.tableNumber().isBlank())
        { return ResponseEntityCreator.returnResponseEntity(400, "Table number is not valid."); }

        if (dto.capacity() <= 0)
        {
            return ResponseEntityCreator.returnResponseEntity(400, "Table capacity must be above 0.");
        }

        model.setTableNumber(dto.tableNumber());
        model.setCapacity(dto.capacity());
        model.setStatus(dto.status());

        try
        { repo.save(model); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later. " + ex); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully updated.");
    }

    public ResponseEntity<?> deleteTable(String tableNumber)
    {
        TableEntity model;
        try
        { model = getTableByTableNumber(tableNumber); }
        catch (TableNotFoundException ex)
        { return ResponseEntityCreator.returnResponseEntity(404, "Table does not exist."); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later. " + ex); }

        model.setStatus(TableStatus.Removed);

        try
        { repo.save(model); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later."); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully removed.");
    }

    private boolean isTableNumberAvailable(String tableNumber)
    {
        Optional<TableEntity> activeResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active);
        Optional<TableEntity> inactiveResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive);
        return (activeResult.isEmpty() && inactiveResult.isEmpty());
    }

    private TableEntity getTableByTableNumber(String tableNumber)
    {
        return repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active).or(() ->
                repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive)).orElseThrow(() ->
                new TableNotFoundException("Table doesn't exist")
        );
    }
}
