package com.math713.bankapi.controller;

import com.math713.bankapi.dto.CreateUserRequest;
import com.math713.bankapi.dto.UpdateUserRequest;
import com.math713.bankapi.dto.UserResponse;
import com.math713.bankapi.mapper.UserMapper;
import com.math713.bankapi.model.User;
import com.math713.bankapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Find user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toResponse(user));
    }

    @Operation(summary = "List all users")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> userResponseList = userService.findAll().stream().
                map(UserMapper::toResponse).toList();

        return ResponseEntity.ok(userResponseList);
    }

    @Operation(summary = "Create user")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = userService.create(UserMapper.toModel(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(UserMapper.toResponse(createdUser));
    }

    @Operation(summary = "Update user by id")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.update(id, UserMapper.toModel(request));
        return ResponseEntity.ok(UserMapper.toResponse(updatedUser));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}