package nl.fontys.restaurantreservations.api.controllers;

import jakarta.validation.Valid;

import nl.fontys.restaurantreservations.api.dtos.UserDTO;
import nl.fontys.restaurantreservations.api.dtos.requests.user.CreateUserRequest;
import nl.fontys.restaurantreservations.api.mappers.UserDomainDTOMapper;
import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.bll.enums.UserRole;
import nl.fontys.restaurantreservations.bll.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiv1/users")
public class UserController {
    private final UserService userService;

    private UserDomainDTOMapper mapper;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping // Default url, will make customer
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        UserDTO dto = new UserDTO(request.email(), request.name(), request.password(), UserRole.Customer);
        UserDomain domain = mapper.toDomain(dto);
        return userService.createUser(domain);
    }
}
