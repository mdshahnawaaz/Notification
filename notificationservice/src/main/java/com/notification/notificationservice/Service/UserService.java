package com.notification.notificationservice.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;
import com.notification.notificationservice.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsersByType(UserType userType) {
        return userRepository.findByUserType(userType);
    }

}
