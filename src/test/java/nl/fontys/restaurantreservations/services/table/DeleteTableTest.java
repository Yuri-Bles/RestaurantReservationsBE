package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.dtos.TableDTO;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.ITableRepository;
import nl.fontys.restaurantreservations.entities.TableEntity;
import nl.fontys.restaurantreservations.services.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTableTest
{
    @Mock
    private ITableRepository repo;

    @InjectMocks
    private TableService service;

    @Test
    void deleteTable_shouldRemoveTable_whenValuesAreValidAndTableExists()
    {
        // Arrange
        TableEntity existing = new TableEntity("T02", 4, TableStatus.Inactive);
        TableEntity expected = new TableEntity("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = new TableDTO(expected);
        ArgumentCaptor<TableEntity> captor = ArgumentCaptor.forClass(TableEntity.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.of(existing));

        when(repo.save(any(TableEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        service.deleteTable(TableDTO.tableNumber());

        // Assert
        verify(repo).save(captor.capture());

        TableEntity actual = captor.getValue();

        assertEquals(expected.getTableNumber(), actual.getTableNumber());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void deleteTable_shouldReturn404_whenTableNumberIsInvalid()
    {
        // Arrange
        TableEntity existing = new TableEntity("T02", 4, TableStatus.Inactive);
        TableEntity expected = new TableEntity("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = new TableDTO(expected);
        ArgumentCaptor<TableEntity> captor = ArgumentCaptor.forClass(TableEntity.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = service.deleteTable(TableDTO.tableNumber());

        // Assert
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void deleteTable_shouldReturn500_whenFailsToGetModel()
    {
        // Arrange
        TableEntity existing = new TableEntity("T02", 4, TableStatus.Inactive);
        TableEntity expected = new TableEntity("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = new TableDTO(expected);
        ArgumentCaptor<TableEntity> captor = ArgumentCaptor.forClass(TableEntity.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = service.deleteTable(TableDTO.tableNumber());

        // Assert
        assertEquals(500, response.getStatusCode().value());
    }

    @Test
    void deleteTable_shouldReturn500_whenModelFailsToUpdate()
    {
        // Arrange
        TableEntity existing = new TableEntity("T02", 4, TableStatus.Inactive);
        TableEntity expected = new TableEntity("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = new TableDTO(expected);
        ArgumentCaptor<TableEntity> captor = ArgumentCaptor.forClass(TableEntity.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.of(existing));

        when(repo.save(any(TableEntity.class)))
                .thenThrow(new RuntimeException());

        // Act
        ResponseEntity<?> response = service.deleteTable(TableDTO.tableNumber());

        // Assert
        assertEquals(500, response.getStatusCode().value());
        verify(repo).save(any(TableEntity.class));
    }
}