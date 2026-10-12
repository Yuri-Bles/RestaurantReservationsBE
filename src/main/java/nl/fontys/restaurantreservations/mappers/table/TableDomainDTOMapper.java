package nl.fontys.restaurantreservations.mappers.table;

import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.dtos.TableDTO;

import java.util.Collections;
import java.util.List;

public class TableDomainDTOMapper {
    public TableDomain toDomain(TableDTO dto) {
        return new TableDomain(
                dto.tableNumber(),
                dto.capacity(),
                dto.status()
        );
    }

    public List<TableDomain> listToDomain(List<TableDTO> dtos) {
        List<TableDomain> domains = new java.util.ArrayList<>(Collections.emptyList());
        for (TableDTO dto : dtos)
        {
            domains.add(toDomain(dto));
        }
        return domains;
    }

    public TableDTO toDTO(TableDomain domain) {
        return new TableDTO(
                domain.getTableNumber(),
                domain.getCapacity(),
                domain.getStatus()
        );
    }

    public List<TableDTO> listToDTO(List<TableDomain> domains) {
        List<TableDTO> dtos = new java.util.ArrayList<>(Collections.emptyList());
        for (TableDomain domain : domains)
        {
            dtos.add(toDTO(domain));
        }
        return dtos;
    }
}
