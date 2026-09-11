package nl.fontys.restaurantreservations.services;

import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.TableModel;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TableServiceTest
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

        List<TableDTO> expected = List.of(table1, table2, table3);
        List<TableModel> repoReturn = List.of(tableModel1, tableModel2, tableModel3);

        when(repo.findAllByStatus(TableStatus.Active)).thenReturn(repoReturn);

        // Act
        List<TableDTO> actual = service.getAllActiveTables();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void createTable_shouldCreateTable_whenValuesAreCorrect()
    {
        // Arrange
        TableModel expected = new TableModel("T01", 4, TableStatus.Active);
        TableDTO newTableDTO = new TableDTO(expected);
        ArgumentCaptor<TableModel> captor = ArgumentCaptor.forClass(TableModel.class);

        // Act
        service.createTable(newTableDTO.tableNumber(), newTableDTO.capacity(), newTableDTO.status());

        // Assert
        verify(repo).save(captor.capture());

        TableModel actual = captor.getValue();

        assertEquals(expected.getTableNumber(), actual.getTableNumber());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void createTable_shouldReturn400_whenTableNumberIsBlank()
    {
        // Arrange
        TableModel newTableModel = new TableModel(" ", 4, TableStatus.Active);
        TableDTO newTableDTO = new TableDTO(newTableModel);

        // Act
        ResponseEntity<?> response = service.createTable(
                newTableDTO.tableNumber(),
                newTableDTO.capacity(),
                newTableDTO.status()
        );

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void createTable_shouldReturn400_whenTableNumberIsDuplicate()
    {
        // Arrange
        TableModel newTableModel = new TableModel("T01", 4, TableStatus.Active);
        TableDTO newTableDTO = new TableDTO(newTableModel);
        when(repo.findByTableNumberAndStatus(newTableDTO.tableNumber(), TableStatus.Active)).thenReturn(Optional.of(newTableModel));

        // Act
        ResponseEntity<?> response = service.createTable(
                newTableDTO.tableNumber(),
                newTableDTO.capacity(),
                newTableDTO.status()
        );

        // Assert
        assertEquals(400, response.getStatusCode().value());
    }
}