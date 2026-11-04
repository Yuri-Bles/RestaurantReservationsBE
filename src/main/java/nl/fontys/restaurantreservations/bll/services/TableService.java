package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.api.dtos.TableDTO;
import nl.fontys.restaurantreservations.api.mappers.TableDomainDTOMapper;
import nl.fontys.restaurantreservations.bll.domains.TableDomain;
import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.bll.exceptions.EmptyTableListException;
import nl.fontys.restaurantreservations.bll.exceptions.TableNotFoundException;

import nl.fontys.restaurantreservations.bll.exceptions.TableNumberAlreadyInUseException;
import nl.fontys.restaurantreservations.dal.interfaces.repo.TableRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TableService
{
    private final TableRepository repo;

    private final TableDomainDTOMapper mapper = new TableDomainDTOMapper();

    public TableService(TableRepository repo)
    {
        this.repo = repo;
    }

    public ResponseEntity<?> getAllActiveTables()
    {
        List<TableDomain> tables;
        tables = repo.findAllByStatus(TableStatus.Active);

        if (tables.isEmpty())
        { throw new EmptyTableListException("Failed to get tables"); }

        List<TableDTO> dtos = mapper.listToDTO(tables);
        return ResponseEntityCreator.returnResponseEntity(200, "Got tables successfully", dtos);
    }

    public ResponseEntity<?> getAllExistingTables()
    {
        List<TableDomain> tables = Stream.concat(
                repo.findAllByStatus(TableStatus.Active).stream(),
                repo.findAllByStatus(TableStatus.Inactive).stream()
            )
            .sorted(
                Comparator.comparing(TableDomain::getStatus)
                        .thenComparing(TableDomain::getTableNumber)
            )
            .toList();

        if (tables.isEmpty())
        { throw new EmptyTableListException("Failed to get tables"); }

        List<TableDTO> dtos = mapper.listToDTO(tables);
        return ResponseEntityCreator.returnResponseEntity(200, "Got tables successfully", dtos);
    }

    public ResponseEntity<?> createTable(TableDTO dto)
    {
        TableDomain domain = mapper.toDomain(dto);

        if (!isTableNumberAvailable(domain.getTableNumber())) {
            throw new TableNumberAlreadyInUseException("Table number is already in use.");
        }

        repo.save(domain);
        return ResponseEntityCreator.returnResponseEntity(201, "Table successfully created.");
    }

    public ResponseEntity<?> updateTable(String oldTableNumber, TableDTO dto)
    {
        TableDomain domain;
        domain = getTableByTableNumber(oldTableNumber);

        domain.setTableNumber(dto.tableNumber());
        domain.setCapacity(dto.capacity());
        domain.setStatus(dto.status());

        repo.save(domain);
        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully updated.");
    }

    public ResponseEntity<?> deleteTable(String tableNumber)
    {
        TableDomain domain;
        try
        { domain = getTableByTableNumber(tableNumber); }
        catch (TableNotFoundException ex)
        { return ResponseEntityCreator.returnResponseEntity(404, "Table does not exist."); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later. " + ex); }

        domain.setStatus(TableStatus.Removed);

        try
        { repo.save(domain); }
        catch (Exception ex)
        { return ResponseEntityCreator.returnResponseEntity(500, "Something went wrong. Try again later."); }

        return ResponseEntityCreator.returnResponseEntity(200, "Table successfully removed.");
    }

    private boolean isTableNumberAvailable(String tableNumber)
    {
        Optional<TableDomain> activeResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active);
        Optional<TableDomain> inactiveResult = repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive);
        return (activeResult.isEmpty() && inactiveResult.isEmpty());
    }

    private TableDomain getTableByTableNumber(String tableNumber)
    {
        return repo.findByTableNumberAndStatus(tableNumber, TableStatus.Active).or(() ->
                repo.findByTableNumberAndStatus(tableNumber, TableStatus.Inactive)).orElseThrow(() ->
                new TableNotFoundException("Table doesn't exist")
        );
    }
}
