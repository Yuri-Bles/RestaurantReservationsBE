package nl.fontys.restaurantreservations.api.controllers;

import jakarta.validation.Valid;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;
import nl.fontys.restaurantreservations.api.dtos.UserDTO;
import nl.fontys.restaurantreservations.api.dtos.requests.user.CreateUserRequest;
import nl.fontys.restaurantreservations.api.dtos.requests.user.LoginRequest;
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

    private UserDomainDTOMapper mapper = new UserDomainDTOMapper();

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping // Default url, will make customer
    public ResponseEntity<ApiMessage> createUser(@Valid @RequestBody CreateUserRequest request)
    {
        UserDTO dto = new UserDTO(request.email(), request.name(), request.password(), UserRole.CUSTOMER);
        UserDomain domain = mapper.toDomain(dto);
        return userService.createUser(domain);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiMessage> login(@Valid @RequestBody LoginRequest request)
    {
        UserDTO dto = new UserDTO(request.email(), null, request.password(), null);
        UserDomain domain = mapper.toDomain(dto);
        return userService.login(domain);
    }
}
