package nl.fontys.restaurantreservations.dal.interfaces.repo;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;

import java.util.Optional;

public interface UserRepository {
    Optional<UserDomain> findByEmail(String email);

    void save(UserDomain domain);
}
