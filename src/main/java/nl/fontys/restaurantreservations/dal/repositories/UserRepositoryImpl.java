package nl.fontys.restaurantreservations.dal.repositories;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.dal.entities.UserEntity;
import nl.fontys.restaurantreservations.dal.interfaces.jpa.JPAUserRepository;
import nl.fontys.restaurantreservations.dal.interfaces.repo.UserRepository;
import nl.fontys.restaurantreservations.dal.mappers.UserDomainEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JPAUserRepository jpaRepository;

    private final UserDomainEntityMapper mapper;

    public UserRepositoryImpl(
            JPAUserRepository jpaRepository,
            UserDomainEntityMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    public Optional<UserDomain> findByEmail(String email)
    {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    public void save(UserDomain domain) {
        UserEntity entity = mapper.toEntity(domain);
        jpaRepository.save(entity);
    }
}
