package nl.fontys.restaurantreservations.dal.mappers;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.dal.entities.UserEntity;

public class UserDomainEntityMapper {
    public UserDomain toDomain(UserEntity entity) {
        return new UserDomain(
                entity.getId(),
                entity.getEmail(),
                entity.getName(),
                entity.getPassword(),
                entity.getRole()
        );
    }

    public UserEntity toEntity(UserDomain domain) {
        UserEntity entity = new UserEntity();

        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setName(domain.getName());
        entity.setPassword(domain.getPassword());

        return entity;
    }
}
