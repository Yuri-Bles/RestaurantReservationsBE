package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.api.dtos.UserDTO;
import nl.fontys.restaurantreservations.dal.interfaces.repo.UserRepository;
import org.springframework.http.ResponseEntity;

public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<?> createUser(UserDTO dto) {
        
    }
}
