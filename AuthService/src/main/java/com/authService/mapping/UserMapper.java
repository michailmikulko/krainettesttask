package com.authService.mapping;

import com.authService.dto.request.CreateUserRequest;
import com.authService.dto.request.UpdateMeRequest;
import com.authService.dto.request.UpdateUserRequest;
import com.authService.dto.response.UserResponse;
import com.authService.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(UserEntity entity);

    UserEntity toEntity(CreateUserRequest request);

    void updateMeEntity(UpdateMeRequest request, @MappingTarget UserEntity entity);

    void updateEntity(UpdateUserRequest request, @MappingTarget UserEntity entity);
}