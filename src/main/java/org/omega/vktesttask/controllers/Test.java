package org.omega.vktesttask.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.repository.UserRepository;
import org.omega.vktesttask.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Transactional
public class Test {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("test")
    public ResponseEntity<?> test() {
        User user = (User) userService.loadUserByUsername("admin");
        System.out.println(user);
        return ResponseEntity.ok(new UserDTO(user));
    }
}
