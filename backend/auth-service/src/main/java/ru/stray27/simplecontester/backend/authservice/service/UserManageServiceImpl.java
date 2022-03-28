package ru.stray27.simplecontester.backend.authservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserInfoResponse;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserLoginRequest;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserRegisterRequest;
import ru.stray27.simplecontester.backend.authservice.exception.AuthenticationException;
import ru.stray27.simplecontester.backend.authservice.model.User;
import ru.stray27.simplecontester.backend.authservice.repository.UserRepository;

@Service
@Slf4j
@AllArgsConstructor
public class UserManageServiceImpl implements UserManageService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegisterRequest userRegisterRequest) {
        try {
            User user = User.builder()
                    .username(userRegisterRequest.getUsername())
                    .email(userRegisterRequest.getEmail())
                    .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                    .build();
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error while registering user: {}", userRegisterRequest.getUsername());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loginUser(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUsername(userLoginRequest.getUsername()).orElseThrow(() -> new AuthenticationException("Invalid username"));
        if (!user.getPassword().equals(passwordEncoder.encode(userLoginRequest.getPassword()))) {
            throw new AuthenticationException("Invalid password");
        }

    }

    @Override
    public UserInfoResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserInfoResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().toString())
                .build();
    }
}
