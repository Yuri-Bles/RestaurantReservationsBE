package nl.fontys.restaurantreservations.mappers.table;

import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.dtos.TableDTO;

public class TableDomainDTOMapper {
    public static TableDomain toDomain(TableDTO dto) {
        return new TableDomain(
                dto.tableNumber(),
                dto.capacity(),
                dto.status()
        );
    }

    public static TableDTO toDTO(TableDomain domain) {
        return new TableDTO(
                domain.getTableNumber(),
                domain.getCapacity(),
                domain.getStatus()
        );
    }
}
