package nl.fontys.restaurantreservations.services.user;

import nl.fontys.restaurantreservations.api.dtos.ApiMessage;
import nl.fontys.restaurantreservations.api.dtos.responses.user.AuthResponse;
import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.bll.enums.UserRole;
import nl.fontys.restaurantreservations.bll.exceptions.InvalidCredentialsException;
import nl.fontys.restaurantreservations.bll.services.JwtService;
import nl.fontys.restaurantreservations.bll.services.PasswordService;
import nl.fontys.restaurantreservations.bll.services.ResponseEntityCreator;
import nl.fontys.restaurantreservations.bll.services.UserService;
import nl.fontys.restaurantreservations.dal.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @Mock
    private UserRepositoryImpl repo;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService service;

    private final PasswordService ps = new PasswordService();

    @Test
    void login_shouldLogin_whenValuesAreCorrect()
    {
        // Arrange
        String hashedPassword = ps.hash("pass123");
        UserDomain input = new UserDomain("test@mail.com", null, "pass123", null);
        UserDomain dbUser = new UserDomain("test@mail.com", "Test", hashedPassword, UserRole.CUSTOMER);

        String accessToken = "test-access-token";

        when(repo.findByEmail(input.getEmail()))
                .thenReturn(Optional.of(dbUser));

        when(jwtService.generateToken(dbUser))
                .thenReturn(accessToken);

        // Act
        ResponseEntity<ApiMessage> actual = service.login(input);

        // Assert
        assertEquals(HttpStatusCode.valueOf(200), actual.getStatusCode());

        verify(repo).findByEmail(input.getEmail());
        verify(jwtService).generateToken(dbUser);
    }

    @Test
    void login_shouldReturn400_whenEmailIsMissing()
    {
        // Arrange
        UserDomain input = new UserDomain(null, null, "pass123", null);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.login(input)
        );

        // Assert
        assertEquals("Email is required.", exception.getMessage());
    }

    @Test
    void login_shouldReturn400_whenPasswordIsMissing()
    {
        // Arrange
        UserDomain input = new UserDomain("test@mail.com", null, null, null);

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.login(input)
        );

        // Assert
        assertEquals("Password is required.", exception.getMessage());
    }

    @Test
    void login_shouldReturn401_whenEmailIsInvalid()
    {
        // Arrange
        UserDomain input = new UserDomain("test@mail.com", null, "pass123", null);

        when(repo.findByEmail(input.getEmail()))
                .thenReturn(Optional.empty());

        // Act
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> service.login(input)
        );

        // Assert
        assertEquals("Email or password is incorrect.", exception.getMessage());
    }

    @Test
    void login_shouldReturn401_whenPasswordIsInvalid()
    {
        // Arrange
        String hashedPassword = ps.hash("password123");
        UserDomain input = new UserDomain("test@mail.com", null, "pass123", null);
        UserDomain dbUser = new UserDomain("test@mail.com", "Test", hashedPassword, UserRole.CUSTOMER);

        when(repo.findByEmail(input.getEmail()))
                .thenReturn(Optional.of(dbUser));

        // Act
        InvalidCredentialsException exception = assertThrows(
                InvalidCredentialsException.class,
                () -> service.login(input)
        );

        // Assert
        assertEquals("Email or password is incorrect.", exception.getMessage());
    }
}
