package nl.fontys.restaurantreservations.mappers.table;

import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.entities.TableEntity;

public class TableDomainEntityMapper {
    public TableDomain toDomain(TableEntity entity) {
        return new TableDomain(
                entity.getId(),
                entity.getTableNumber(),
                entity.getCapacity(),
                entity.getStatus()
        );
    }

    public TableEntity toEntity(TableDomain domain) {
        TableEntity entity = new TableEntity();

        entity.setId(domain.getId());
        entity.setTableNumber(domain.getTableNumber());
        entity.setCapacity(domain.getCapacity());
        entity.setStatus(domain.getStatus());

        return entity;
    }
}
