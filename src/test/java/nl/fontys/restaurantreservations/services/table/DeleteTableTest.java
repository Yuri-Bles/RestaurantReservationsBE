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
        TableDomain existing = new TableDomain("T02", 4, TableStatus.INACTIVE);
        TableDomain expected = new TableDomain("T02", 4, TableStatus.REMOVED);
        TableDTO tableDTO = mapper.toDTO(expected);
        ArgumentCaptor<TableDomain> captor = ArgumentCaptor.forClass(TableDomain.class);

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.ACTIVE))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.INACTIVE))
                .thenReturn(Optional.of(existing));

        doAnswer(invocation -> invocation.getArgument(0))
                .when(repo)
                .save(any(TableDomain.class));

        // Act
        service.deleteTable(tableDTO.tableNumber());

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
        TableDomain existing = new TableDomain("T02", 4, TableStatus.INACTIVE);
        TableDomain expected = new TableDomain("T02", 4, TableStatus.REMOVED);
        TableDTO tableDTO = mapper.toDTO(expected);
        String tableNumber = tableDTO.tableNumber();

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.ACTIVE))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus(existing.getTableNumber(), TableStatus.INACTIVE))
                .thenReturn(Optional.empty());

        // Act
        TableNotFoundException exception = assertThrows(
                TableNotFoundException.class,
                () -> service.deleteTable(tableNumber)
        );

        // Assert
        assertEquals("Table doesn't exist", exception.getMessage());
    }
}