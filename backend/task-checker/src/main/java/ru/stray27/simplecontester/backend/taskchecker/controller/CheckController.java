package ru.stray27.simplecontester.backend.taskchecker.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/checker")
public class CheckController {
    @GetMapping("/isAdmin")
    @RolesAllowed({"Admin"})
    public String admin() {
        return "admin";
    }
}
