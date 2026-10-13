package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.mappers.table.*;
import nl.fontys.restaurantreservations.repositories.TableRepositoryImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTableTest
{
    @Mock
    private TableRepositoryImpl repo;

    @InjectMocks
    private TableService service;

    TableDomainDTOMapper mapper = new TableDomainDTOMapper();

    @Test
    void getAllActiveTables_shouldReturnActiveTables()
    {
        // Arrange
        TableDomain tableDomain1 = new TableDomain("T01", 4, TableStatus.Active);
        TableDomain tableDomain2 = new TableDomain("T02", 6, TableStatus.Active);
        TableDomain tableDomain3 = new TableDomain("T03", 6, TableStatus.Active);
        TableDTO table1 = mapper.toDTO(tableDomain1);
        TableDTO table2 = mapper.toDTO(tableDomain2);
        TableDTO table3 = mapper.toDTO(tableDomain3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(
                200,
                "Got tables successfully",
                dtoList);

        List<TableDomain> repoReturn = List.of(tableDomain1, tableDomain2, tableDomain3);

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
        TableDomain tableDomain1 = new TableDomain("T01", 4, TableStatus.Active);
        TableDomain tableDomain2 = new TableDomain("T02", 6, TableStatus.Active);
        TableDomain tableDomain3 = new TableDomain("T03", 6, TableStatus.Active);
        TableDTO table1 = mapper.toDTO(tableDomain1);
        TableDTO table2 = mapper.toDTO(tableDomain2);
        TableDTO table3 = mapper.toDTO(tableDomain3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables");

        List<TableDomain> repoReturn = List.of();

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
        TableDomain tableDomain1 = new TableDomain("T01", 4, TableStatus.Active);
        TableDomain tableDomain2 = new TableDomain("T02", 6, TableStatus.Inactive);
        TableDomain tableDomain3 = new TableDomain("T03", 6, TableStatus.Inactive);
        TableDTO table1 = mapper.toDTO(tableDomain1);
        TableDTO table2 = mapper.toDTO(tableDomain2);
        TableDTO table3 = mapper.toDTO(tableDomain3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(
                200,
                "Got tables successfully",
                dtoList);

        List<TableDomain> repoReturnActive = List.of(tableDomain1);
        List<TableDomain> repoReturnInactive = List.of(tableDomain2, tableDomain3);

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

        List<TableDomain> repoReturn = List.of();

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);
        when(repo.findAllByStatus(TableStatus.Inactive)).thenReturn(repoReturn);

        // Act
        ResponseEntity<?> actual = service.getAllExistingTables();

        // Assert
        assertEquals(expected, actual);
    }
}