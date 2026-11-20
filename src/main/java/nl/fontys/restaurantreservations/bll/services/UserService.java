package nl.fontys.restaurantreservations.bll.services;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;
import nl.fontys.restaurantreservations.api.dtos.responses.user.AuthResponse;
import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.bll.exceptions.InvalidCredentialsException;
import nl.fontys.restaurantreservations.dal.interfaces.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;

    private final PasswordService ps = new PasswordService();

    private final JwtService jwtService;

    public UserService(UserRepository repo, JwtService jwtService) {
        this.repo = repo;
        this.jwtService = jwtService;
    }

    public ResponseEntity<ApiMessage> createUser(UserDomain domain) {
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

    public ResponseEntity<ApiMessage> login(UserDomain domain) {
        if (domain.getEmail() == null) {
            throw new IllegalArgumentException("Email is required.");
        }
        if (domain.getPassword() == null) {
            throw new IllegalArgumentException("Password is required.");
        }

        UserDomain dbDomain = repo.findByEmail(domain.getEmail()).orElseThrow(() ->
                new InvalidCredentialsException("Email or password is incorrect."));

        if (!ps.verify(domain.getPassword(), dbDomain.getPassword())) {
            throw new InvalidCredentialsException("Email or Password is incorrect.");
        }
        domain = dbDomain;

        String token = jwtService.generateToken(domain);
        AuthResponse responseBody = new AuthResponse(token, "Bearer");

        return ResponseEntityCreator.returnResponseEntity(200, "Logged in successfully.", responseBody);
    }
}
