package nl.fontys.restaurantreservations.repositories;

import jakarta.persistence.Table;
import nl.fontys.restaurantreservations.domain.TableDomain;
import nl.fontys.restaurantreservations.entities.TableEntity;
import nl.fontys.restaurantreservations.enums.TableStatus;
import nl.fontys.restaurantreservations.interfaces.JPATableRepository;
import nl.fontys.restaurantreservations.interfaces.TableRepository;
import nl.fontys.restaurantreservations.mappers.table.TableDomainEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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

    public void save(TableDomain domain) {
        TableEntity entity = mapper.toEntity(domain);
        jpaRepository.save(entity);
    }

    public Optional<TableDomain> findByTableNumberAndStatus(String tableNumber, TableStatus status)
    {
        return jpaRepository.findByTableNumberAndStatus(tableNumber, status)
                .map(mapper::toDomain);
    }
}
