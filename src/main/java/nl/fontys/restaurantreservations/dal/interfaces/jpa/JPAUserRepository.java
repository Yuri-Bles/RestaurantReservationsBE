package nl.fontys.restaurantreservations.dal.interfaces.jpa;

import nl.fontys.restaurantreservations.dal.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JPAUserRepository extends JpaRepository {
    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity table);
}
