import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.Controllerç.User;
import org.example.Controllerç.UserRepository;
import org.example.Controllerç.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById_UserExists() {
        User user = new User(1L, "John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertEquals(user, result);
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User result = userService.getUserById(1L);
        assertNull(result);
    }

    @Test
    public void testCreateUser() {
        User user = new User(null, "Jane");
        User savedUser = new User(1L, "Jane");
        when(userRepository.save(user)).thenReturn(savedUser);
        User result = userService.createUser(user);
        assertEquals(savedUser, result);
    }

    @Test
    public void testUpdateUser_UserExists() {
        User user = new User(null, "Jane");
        User updatedUser = new User(1L, "Jane");
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(updatedUser);
        User result = userService.updateUser(1L, user);
        assertEquals(updatedUser, result);
    }

    @Test
    public void testUpdateUser_UserDoesNotExist() {
        User user = new User(null, "Jane");
        when(userRepository.existsById(1L)).thenReturn(false);
        User result = userService.updateUser(1L, user);
        assertNull(result);
    }

    @Test
    public void testDeleteUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);
        boolean result = userService.deleteUser(1L);
        assertTrue(result);
    }

    @Test
    public void testDeleteUser_UserDoesNotExist() {
        when(userRepository.existsById(1L)).thenReturn(false);
        boolean result = userService.deleteUser(1L);
        assertFalse(result);
    }

    @Test
    public void testCreateUser_NullUser() {
        assertThrows(NullPointerException.class, () -> {
            userService.createUser(null);
        });
    }

    @Test
    public void testUpdateUser_InvalidId() {
        User user = new User(null, "Jane");
        when(userRepository.existsById(null)).thenReturn(false);
        User result = userService.updateUser(null, user);
        assertNull(result);
    }

    @Test
    public void testDeleteUser_NullId() {
        assertThrows(NullPointerException.class, () -> {
            userService.deleteUser(null);
        });
    }

    @Test
    public void testGetUserById_Exception() {
        when(userRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
    }
}

