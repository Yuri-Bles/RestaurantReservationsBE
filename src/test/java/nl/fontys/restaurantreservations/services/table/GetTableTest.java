package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.dtos.ApiMessage;
import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.TableModel;
import nl.fontys.restaurantreservations.services.ResponseEntityCreator;
import nl.fontys.restaurantreservations.services.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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
        TableModel tableModel1 = new TableModel("T01", 4, TableStatus.Active);
        TableModel tableModel2 = new TableModel("T02", 6, TableStatus.Active);
        TableModel tableModel3 = new TableModel("T03", 6, TableStatus.Active);
        TableDTO table1 = new TableDTO(tableModel1);
        TableDTO table2 = new TableDTO(tableModel2);
        TableDTO table3 = new TableDTO(tableModel3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(
                200,
                "Got tables successfully",
                dtoList);

        List<TableModel> repoReturn = List.of(tableModel1, tableModel2, tableModel3);

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
        TableModel tableModel1 = new TableModel("T01", 4, TableStatus.Active);
        TableModel tableModel2 = new TableModel("T02", 6, TableStatus.Active);
        TableModel tableModel3 = new TableModel("T03", 6, TableStatus.Active);
        TableDTO table1 = new TableDTO(tableModel1);
        TableDTO table2 = new TableDTO(tableModel2);
        TableDTO table3 = new TableDTO(tableModel3);

        List<TableDTO> dtoList = List.of(table1, table2, table3);
        ResponseEntity<?> expected = ResponseEntityCreator.returnResponseEntity(500, "Failed to get tables");

        List<TableModel> repoReturn = List.of();

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);

        // Act
        ResponseEntity<?> actual = service.getAllActiveTables();

        // Assert
        assertEquals(expected, actual);
    }
}