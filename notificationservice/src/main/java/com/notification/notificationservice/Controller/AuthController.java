package com.notification.notificationservice.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.notificationservice.Dto.AuthResponse;
import com.notification.notificationservice.Dto.LoginRequest;
import com.notification.notificationservice.Dto.SignupRequest;
import com.notification.notificationservice.Dto.UserResponse;
import com.notification.notificationservice.Entity.Role;
import com.notification.notificationservice.Entity.User;
import com.notification.notificationservice.Entity.UserType;
import com.notification.notificationservice.Repository.UserRepository;
import com.notification.notificationservice.config.security.JwtService;
import com.notification.notificationservice.config.security.JwtUserPrincipal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (isBlank(request.getName()) || isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Name, email and password are required."));
        }

        if (request.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(Map.of("message", "Password must be at least 6 characters."));
        }

        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email is already registered."));
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserType(request.getUserType() == null ? UserType.FREE : request.getUserType());
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(jwtService.generateToken(savedUser), savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (isBlank(request.getEmail()) || isBlank(request.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email and password are required."));
        }

        String email = request.getEmail().trim().toLowerCase();
        return userRepository.findByEmail(email)
                .filter(user -> passwordMatches(request.getPassword(), user))
                .<ResponseEntity<?>>map(user -> {
                    User normalizedUser = normalizeLegacyUser(user);
                    return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(normalizedUser), normalizedUser));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Invalid email or password.")));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof JwtUserPrincipal principal)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Authentication required."));
        }

        return userRepository.findByEmail(principal.email())
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(new UserResponse(normalizeLegacyUser(user))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "User account no longer exists.")));
    }

    private User normalizeLegacyUser(User user) {
        boolean changed = false;

        if (user.getRole() == null) {
            user.setRole(Role.USER);
            changed = true;
        }

        if (user.getUserType() == null) {
            user.setUserType(UserType.FREE);
            changed = true;
        }

        if (changed) {
            return userRepository.save(user);
        }

        return user;
    }

    private boolean passwordMatches(String rawPassword, User user) {
        if (isBcryptHash(user.getPassword())) {
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }

        if (!user.getPassword().equals(rawPassword)) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(user);
        return true;
    }

    private boolean isBcryptHash(String value) {
        return value != null && (value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$"));
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
