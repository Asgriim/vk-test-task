package org.omega.vktesttask.controllers;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.omega.vktesttask.aspect.Audit;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "**")
public class AuthController {
    private final UserService userService;

    @Audit
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.login(userDTO));
    }

    @Audit
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {

        if(userDTO.getRoles().contains(Role.ADMIN)) {
            return ResponseEntity.badRequest().body("Only admin can register admin");
        }
        userService.register(userDTO);
        return ResponseEntity.ok("ok");
    }

    @Audit
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        return ResponseEntity.ok("ok");
    }
}
