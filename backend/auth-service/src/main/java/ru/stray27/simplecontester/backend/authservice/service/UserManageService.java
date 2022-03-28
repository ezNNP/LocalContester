package ru.stray27.simplecontester.backend.authservice.service;

import ru.stray27.simplecontester.backend.authservice.controller.dto.UserInfoResponse;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserLoginRequest;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserRegisterRequest;

public interface UserManageService {
    void registerUser(UserRegisterRequest userRegisterRequest);
    void loginUser(UserLoginRequest userLoginRequest);
    UserInfoResponse getUserInfo(String username);
}
