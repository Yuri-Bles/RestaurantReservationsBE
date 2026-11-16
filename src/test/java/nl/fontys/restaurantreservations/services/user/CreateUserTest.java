package nl.fontys.restaurantreservations.services.user;

import nl.fontys.restaurantreservations.bll.domains.UserDomain;
import nl.fontys.restaurantreservations.bll.enums.UserRole;
import nl.fontys.restaurantreservations.bll.services.UserService;
import nl.fontys.restaurantreservations.dal.repositories.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserTest {

    @Mock
    private UserRepositoryImpl repo;

    @InjectMocks
    private UserService service;

    @Test
    void createUser_shouldCreateTable_whenValuesAreCorrect()
    {
        // Arrange
        UserDomain expected = new UserDomain("test@mail.com", "test", "pass123", UserRole.Customer);
        ArgumentCaptor<UserDomain> captor = ArgumentCaptor.forClass(UserDomain.class);

        when(repo.findByEmail(expected.getEmail()))
                .thenReturn(Optional.empty());

        // Act
        service.createUser(expected);

        // Assert
        verify(repo).save(captor.capture());

        UserDomain actual = captor.getValue();

        assertEquals(expected, actual);
    }

    @Test
    void createUser_shouldReturn400_whenRoleIsNull()
    {
        // Arrange
        UserDomain user = new UserDomain("test@mail.com", "test", "pass123", null);

        // Act
        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUser(user)
        );

        // Assert
        verify(repo, never()).save(any(UserDomain.class));
    }

    @Test
    void createUser_shouldReturn400_whenEmailAlreadyExists()
    {
        // Arrange
        UserDomain user = new UserDomain("test@mail.com", "test", "pass123", UserRole.Customer);

        when(repo.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        // Act
        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUser(user)
        );

        // Assert
        verify(repo, never()).save(any(UserDomain.class));
    }
}
