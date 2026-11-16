package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.api.mappers.TableDomainDTOMapper;
import nl.fontys.restaurantreservations.bll.domains.TableDomain;
import nl.fontys.restaurantreservations.api.dtos.TableDTO;
import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.bll.exceptions.TableNotFoundException;
import nl.fontys.restaurantreservations.dal.repositories.TableRepositoryImpl;
import nl.fontys.restaurantreservations.bll.services.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteTableTest
{
    @Mock
    private TableRepositoryImpl repo;

    @InjectMocks
    private TableService service;

    TableDomainDTOMapper mapper = new TableDomainDTOMapper();

    @Test
    void deleteTable_shouldRemoveTable_whenValuesAreValidAndTableExists()
    {
        // Arrange
        TableDomain existing = new TableDomain("T02", 4, TableStatus.Inactive);
        TableDomain expected = new TableDomain("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = mapper.toDTO(expected);
        ArgumentCaptor<TableDomain> captor = ArgumentCaptor.forClass(TableDomain.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.of(existing));

        doAnswer(invocation -> invocation.getArgument(0))
                .when(repo)
                .save(any(TableDomain.class));

        // Act
        service.deleteTable(TableDTO.tableNumber());

        // Assert
        verify(repo).save(captor.capture());

        TableDomain actual = captor.getValue();

        assertEquals(expected.getTableNumber(), actual.getTableNumber());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void deleteTable_shouldReturn404_whenTableNumberIsInvalid()
    {
        // Arrange
        TableDomain existing = new TableDomain("T02", 4, TableStatus.Inactive);
        TableDomain expected = new TableDomain("T02", 4, TableStatus.Removed);
        TableDTO TableDTO = mapper.toDTO(expected);
        ArgumentCaptor<TableDomain> captor = ArgumentCaptor.forClass(TableDomain.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.Inactive))
                .thenReturn(Optional.empty());

        // Act
        TableNotFoundException exception = assertThrows(
                TableNotFoundException.class,
                () -> service.deleteTable(TableDTO.tableNumber())
        );

        // Assert
        assertEquals("Table doesn't exist", exception.getMessage());
    }
}