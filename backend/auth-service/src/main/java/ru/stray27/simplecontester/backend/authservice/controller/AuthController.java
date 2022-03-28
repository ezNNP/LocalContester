package ru.stray27.simplecontester.backend.authservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserLoginRequest;
import ru.stray27.simplecontester.backend.authservice.controller.dto.UserRegisterRequest;
import ru.stray27.simplecontester.backend.authservice.service.UserManageService;

@RestController("/api/auth")
@AllArgsConstructor
public class AuthController {

    private UserManageService userManageService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            userManageService.registerUser(userRegisterRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            userManageService.loginUser(userLoginRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userinfo/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        return new ResponseEntity<>(userManageService.getUserInfo(username), HttpStatus.OK);
    }

}
