package com.math713.bankapi.repository;

import com.math713.bankapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final Map<Long, User> storage = new ConcurrentHashMap<>();

    public User save(User user) {
        Objects.requireNonNull(user, "User must not be null");
        if (user.getId() == null) throw new IllegalArgumentException("User id must not be null");
        storage.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return List.copyOf(storage.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public void deleteById(Long id) {
        storage.remove(id);
    }

    public Optional<User> findByEmail(String email) {
        return storage.values().stream().
                filter(user -> email.equalsIgnoreCase(user.getEmail())).findFirst();
    }
}