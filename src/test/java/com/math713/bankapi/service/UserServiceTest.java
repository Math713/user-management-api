package com.math713.bankapi.service;

import com.math713.bankapi.exceptions.EmailAlreadyExistsException;
import com.math713.bankapi.exceptions.UserNotFoundException;
import com.math713.bankapi.model.User;
import com.math713.bankapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private User newUser;
    private User existingUser;

    @BeforeEach
    void setup() {
        newUser = new User(null, "Matheus", "Matheus@gmail.com");
        existingUser = new User(7L, "João", "João@gmail.com");
    }

    @Nested
    class CreateTests {
        // Happy path for create
        @Test
        void shouldCreateUserSuccessfully() {
            // Arrange
            when(repository.findByEmail("matheus@gmail.com")).thenReturn(Optional.empty());
            when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

            // Act
            User result = service.create(newUser);

            // Assert
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals("matheus@gmail.com", result.getEmail());

            verify(repository).findByEmail("matheus@gmail.com");
            verify(repository).save(any(User.class));
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldThrowAndNotSaveWhenEmailAlreadyExists() {
            when(repository.findByEmail("matheus@gmail.com")).thenReturn(Optional.of(existingUser));

            assertThrows(EmailAlreadyExistsException.class, () -> service.create(newUser));

            verify(repository).findByEmail("matheus@gmail.com");
            verify(repository, never()).save(any(User.class));
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldThrowWhenEmailIsNull() {
            User user = new User(null, "Matheus", null);
            assertThrows(IllegalArgumentException.class, () -> service.create(user));
        }
    }

    @Nested
    class UpdateTests {
        // Happy path for update
        @Test
        void shouldUpdateUserSuccessfully() {
            // Arrange
            when(repository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
            when(repository.findByEmail("matheus@gmail.com")).thenReturn(Optional.empty());
            when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

            // Act
            User result = service.update(existingUser.getId(), newUser);

            // Assert
            assertEquals(existingUser.getId(), result.getId());
            assertEquals("Matheus", result.getName());
            assertEquals("matheus@gmail.com", result.getEmail());

            verify(repository).findById(existingUser.getId());
            verify(repository).findByEmail("matheus@gmail.com");

            ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
            verify(repository).save(captor.capture());
            assertEquals("Matheus", captor.getValue().getName());
            assertEquals("matheus@gmail.com", captor.getValue().getEmail());
            assertEquals(existingUser.getId(), captor.getValue().getId());

            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldThrowWhenUserNotFoundOnUpdate() {
            when(repository.findById(existingUser.getId())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> service.update(existingUser.getId(), existingUser));

            verify(repository).findById(existingUser.getId());
            verify(repository, never()).findByEmail(any());
            verify(repository, never()).save(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldThrowWhenEmailAlreadyExistsOnUpdate() {
            User activeUser = new User(2L, "Miguel", "matheus@gmail.com");

            when(repository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
            when(repository.findByEmail("matheus@gmail.com")).thenReturn(Optional.of(activeUser));

            assertThrows(EmailAlreadyExistsException.class, () -> service.update(existingUser.getId(), newUser));

            verify(repository).findById(existingUser.getId());
            verify(repository).findByEmail("matheus@gmail.com");
            verify(repository, never()).save(any());
            verifyNoMoreInteractions(repository);
        }

        @Test
        void shouldNotThrowWhenEmailBelongsToSameUser() {
            User changes = new User(null, "João Melo", "joão@gmail.com");

            when(repository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
            when(repository.findByEmail("joão@gmail.com")).thenReturn(Optional.of(existingUser));

            assertDoesNotThrow(() -> service.update(existingUser.getId(), changes));

            verify(repository).findById(existingUser.getId());
            verify(repository).findByEmail("joão@gmail.com");
            verify(repository).save(any(User.class));
            verifyNoMoreInteractions(repository);
        }
    }
}