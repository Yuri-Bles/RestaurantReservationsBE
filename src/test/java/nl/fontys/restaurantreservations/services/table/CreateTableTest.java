package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.models.TableModel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTableTest
{
    @Mock
    private ITableRepository repo;

    @InjectMocks
    private TableService service;

    @Test
    void createTable_shouldCreateTable_whenValuesAreCorrect()
    {
        // Arrange
        TableModel expected = new TableModel("T01", 4, TableStatus.Active);
        TableDTO newTableDTO = new TableDTO(expected);
        ArgumentCaptor<TableModel> captor = ArgumentCaptor.forClass(TableModel.class);

        when(repo.findByTableNumberAndStatus("T01", TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus("T01", TableStatus.Inactive))
                .thenReturn(Optional.empty());

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