package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.entities.TableEntity;
import nl.fontys.restaurantreservations.services.ResponseEntityCreator;
import nl.fontys.restaurantreservations.services.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTableTest
{
    @Mock
    private ITableRepository repo;

    @InjectMocks
    private TableService service;

    @Test
    void getAllActiveTables_shouldReturnActiveTables()
    {
        // Arrange
        TableEntity tableEntity1 = new TableEntity("T01", 4, TableStatus.Active);
        TableEntity tableEntity2 = new TableEntity("T02", 6, TableStatus.Active);
        TableEntity tableEntity3 = new TableEntity("T03", 6, TableStatus.Active);
        TableDTO table1 = new TableDTO(tableEntity1);
        TableDTO table2 = new TableDTO(tableEntity2);
        TableDTO table3 = new TableDTO(tableEntity3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(
                200,
                "Got tables successfully",
                dtoList);

        List<TableEntity> repoReturn = List.of(tableEntity1, tableEntity2, tableEntity3);

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);

        // Act
        ResponseEntity<?> actual = service.getAllActiveTables();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllActiveTables_shouldReturn500_whenRepoDoesntReturnTables()
    {
        // Arrange
        TableEntity tableEntity1 = new TableEntity("T01", 4, TableStatus.Active);
        TableEntity tableEntity2 = new TableEntity("T02", 6, TableStatus.Active);
        TableEntity tableEntity3 = new TableEntity("T03", 6, TableStatus.Active);
        TableDTO table1 = new TableDTO(tableEntity1);
        TableDTO table2 = new TableDTO(tableEntity2);
        TableDTO table3 = new TableDTO(tableEntity3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables");

        List<TableEntity> repoReturn = List.of();

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);

        // Act
        ResponseEntity<?> actual = service.getAllActiveTables();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllExistingTables_shouldReturnExistingTables()
    {
        // Arrange
        TableEntity tableEntity1 = new TableEntity("T01", 4, TableStatus.Active);
        TableEntity tableEntity2 = new TableEntity("T02", 6, TableStatus.Inactive);
        TableEntity tableEntity3 = new TableEntity("T03", 6, TableStatus.Inactive);
        TableDTO table1 = new TableDTO(tableEntity1);
        TableDTO table2 = new TableDTO(tableEntity2);
        TableDTO table3 = new TableDTO(tableEntity3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(
                200,
                "Got tables successfully",
                dtoList);

        List<TableEntity> repoReturnActive = List.of(tableEntity1);
        List<TableEntity> repoReturnInactive = List.of(tableEntity2, tableEntity3);

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturnActive);
        when(repo.findAllByStatus(TableStatus.Inactive)).thenReturn(repoReturnInactive);

        // Act
        ResponseEntity<?> actual = service.getAllExistingTables();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getAllExistingTables_shouldReturn500_whenRepoDoesntReturnTables()
    {
        // Arrange
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables");

        List<TableEntity> repoReturn = List.of();

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);
        when(repo.findAllByStatus(TableStatus.Inactive)).thenReturn(repoReturn);

        // Act
        ResponseEntity<?> actual = service.getAllExistingTables();

        // Assert
        assertEquals(expected, actual);
    }
}