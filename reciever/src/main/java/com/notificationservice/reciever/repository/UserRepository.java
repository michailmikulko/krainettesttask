package com.notificationservice.reciever.repository;

import com.notificationservice.reciever.entity.RoleType;
import com.notificationservice.reciever.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findAllByRole(RoleType role);
}