package nl.fontys.restaurantreservations.dal.repositories;

import nl.fontys.restaurantreservations.bll.domains.TableDomain;
import nl.fontys.restaurantreservations.bll.enums.TableStatus;
import nl.fontys.restaurantreservations.dal.entities.TableEntity;
import nl.fontys.restaurantreservations.dal.interfaces.JPATableRepository;
import nl.fontys.restaurantreservations.dal.interfaces.TableRepository;
import nl.fontys.restaurantreservations.dal.mappers.TableDomainEntityMapper;

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
