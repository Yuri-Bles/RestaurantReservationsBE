package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.dal.interfaces.repo.UserRepository;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
}
