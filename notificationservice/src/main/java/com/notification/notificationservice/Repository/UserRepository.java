package com.notification.notificationservice.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByUserType(UserType userType);
}
