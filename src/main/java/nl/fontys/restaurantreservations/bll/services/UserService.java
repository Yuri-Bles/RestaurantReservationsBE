package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.dal.interfaces.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordService ps = new PasswordService();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<?> createUser(UserDomain domain) {
        if (domain.getRole() == null) {
            throw new IllegalArgumentException("User role is required.");
        }
        if (repo.findByEmail(domain.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        domain.setPassword(ps.hash(domain.getPassword()));

        repo.save(domain);
        return ResponseEntityCreator.returnResponseEntity(201, "Account successfully created.");
    }
}
