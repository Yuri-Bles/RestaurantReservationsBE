package nl.fontys.restaurantreservations.services.table;

import nl.fontys.restaurantreservations.api.mappers.TableDomainDTOMapper;
import nl.fontys.restaurantreservations.bll.domains.TableDomain;
import nl.fontys.restaurantreservations.api.dtos.TableDTO;
import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.bll.exceptions.TableNumberAlreadyInUseException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTableTest
{
    @Mock
    private TableRepositoryImpl repo;

    @InjectMocks
    private TableService service;

    TableDomainDTOMapper mapper = new TableDomainDTOMapper();

    @Test
    void createTable_shouldCreateTable_whenValuesAreCorrect()
    {
        // Arrange
        TableDomain expected = new TableDomain("T01", 4, TableStatus.Active);
        TableDTO newTableDTO = mapper.toDTO(expected);
        ArgumentCaptor<TableDomain> captor = ArgumentCaptor.forClass(TableDomain.class);

        when(repo.findByTableNumberAndStatus("T01", TableStatus.Active))
                .thenReturn(Optional.empty());

        when(repo.findByTableNumberAndStatus("T01", TableStatus.Inactive))
                .thenReturn(Optional.empty());

        // Act
        service.createTable(newTableDTO);

        // Assert
        verify(repo).save(captor.capture());

        TableDomain actual = captor.getValue();

        assertEquals(expected.getTableNumber(), actual.getTableNumber());
        assertEquals(expected.getCapacity(), actual.getCapacity());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    @Test
    void createTable_shouldReturn400_whenTableNumberIsDuplicate()
    {
        // Arrange
        TableDomain newTableDomain = new TableDomain("T01", 4, TableStatus.Active);
        TableDTO newTableDTO = mapper.toDTO(newTableDomain);
        when(repo.findByTableNumberAndStatus(newTableDTO.tableNumber(), TableStatus.Active))
                .thenReturn(Optional.of(newTableDomain));

        // Act
        TableNumberAlreadyInUseException exception = assertThrows(
            TableNumberAlreadyInUseException.class,
            () -> service.createTable(newTableDTO)
        );

        // Assert
        assertEquals("Table number is already in use.", exception.getMessage());
    }
}