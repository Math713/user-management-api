package com.math713.bankapi.service;

import com.math713.bankapi.exceptions.EmailAlreadyExistsException;
import com.math713.bankapi.exceptions.UserNotFoundException;
import com.math713.bankapi.model.User;
import com.math713.bankapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        validateUser(user);

        String normalizedEmail = normalizedEmail(user.getEmail());
        validateEmailForUniqueness(normalizedEmail, null);

        user.setEmail(normalizedEmail);

        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return requireExisting(id);
    }

    public User update(Long id, User data) {
        validateUser(data);
        User existing = requireExisting(id);

        String normalizedEmail = normalizedEmail(data.getEmail());
        validateEmailForUniqueness(normalizedEmail, id);

        existing.setName(data.getName());
        existing.setEmail(normalizedEmail);

        return repository.save(existing);
    }

    public void delete(Long id) {
        User existing = requireExisting(id);
        repository.deleteById(existing.getId());
    }

    // =========================
    // Private Helpers
    // =========================

    private void validateUser(User user) {
        Objects.requireNonNull(user, "User must not be null");
        if (user.getName() == null || user.getName().isBlank())
            throw new IllegalArgumentException("User name must not be null or blank");
        if (user.getEmail() == null || user.getEmail().isBlank())
            throw new IllegalArgumentException("User email must not be null or blank");
    }

    private User requireExisting(Long id) {
        if (id == null) throw new IllegalArgumentException("User id must not be null");
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void validateEmailForUniqueness(String email, Long currentUserId) {
        repository.findByEmail(email).ifPresent(user -> {
                    boolean emailBelongsToAnotherUser =
                            currentUserId == null || !Objects.equals(user.getId(), currentUserId);

                    if (emailBelongsToAnotherUser){
                        throw new EmailAlreadyExistsException(email);
                    }
        });
    }

    private String normalizedEmail(String email) {
        return email.trim().toLowerCase();
    }
}