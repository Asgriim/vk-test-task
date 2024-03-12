package org.omega.vktesttask.controllers;


import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.aspect.Audit;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.repository.AuditRepository;
import org.omega.vktesttask.service.AuditService;
import org.omega.vktesttask.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(originPatterns = "**")
public class AdminController {
    private final AuditService auditService;

    @Audit
    @GetMapping("/audit")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(auditService.getAll());
    }

    @Audit
    @GetMapping("/audit/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(auditService.getByUsername(username));
    }

}
