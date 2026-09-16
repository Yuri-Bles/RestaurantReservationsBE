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
class UpdateTableTest
{
    @Mock
    private ITableRepository repo;

    @InjectMocks
    private TableService service;

    @Test
    void updateTable_shouldUpdateTable_whenValuesAreValidAndTableExists()
    {
        // Arrange
        String oldTableNumber = "T01";
        TableModel existing = new TableModel(oldTableNumber, 2, TableStatus.Inactive);
        TableModel expected = new TableModel("T02", 4, TableStatus.Active);
        TableDTO TableDTO = new TableDTO(expected);
        ArgumentCaptor<TableModel> captor = ArgumentCaptor.forClass(TableModel.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.of(existing));

        when(repo.save(any(TableModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        service.updateTable(oldTableNumber, TableDTO.tableNumber(), TableDTO.capacity(), TableDTO.status());

        // Assert
        verify(repo).save(captor.capture());

        TableModel actual = captor.getValue();

        assertEquals(expected.getTableNumber(), actual.getTableNumber());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}