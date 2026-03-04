package com.math713.bankapi.mapper;

import com.math713.bankapi.dto.CreateUserRequest;
import com.math713.bankapi.dto.UpdateUserRequest;
import com.math713.bankapi.dto.UserResponse;
import com.math713.bankapi.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static User toModel(CreateUserRequest request){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return user;
    }

    public static User toModel(UpdateUserRequest request){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return user;
    }

    public static UserResponse toResponse(User user){
        return new UserResponse(user.getId(),user.getName(), user.getEmail());
    }
}