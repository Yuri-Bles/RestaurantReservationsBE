package nl.fontys.restaurantreservations.repositories;

import jakarta.persistence.Table;
import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.JPATableRepository;
import nl.fontys.restaurantreservations.interfaces.TableRepository;
import nl.fontys.restaurantreservations.mappers.table.TableDomainEntityMapper;

import java.util.List;

public class TableRepositoryImpl implements TableRepository {
    private final JPATableRepository jpaRepository;
    private final TableDomainEntityMapper mapper;

    public TableRepositoryImpl(
            JPATableRepository jpaRepository,
            TableDomainEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public List<TableDomain> findAllByStatus(TableStatus status)
    {
        return jpaRepository.findAllByStatus(status)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
