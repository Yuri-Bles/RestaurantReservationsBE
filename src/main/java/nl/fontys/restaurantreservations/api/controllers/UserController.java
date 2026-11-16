package nl.fontys.restaurantreservations.api.controllers;

import jakarta.validation.Valid;

import nl.fontys.restaurantreservations.api.dtos.UserDTO;
import nl.fontys.restaurantreservations.api.dtos.requests.user.CreateUserRequest;
import nl.fontys.restaurantreservations.bll.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        UserDTO dto = new UserDTO(request.email(), request.name(), request.password(), request.role());
        return userService.createUser(dto);
    }
}
