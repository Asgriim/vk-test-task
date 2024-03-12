package org.omega.vktesttask.service;

import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.dto.AuditDTO;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.repository.AuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final UserService userService;
    private final AuditRepository auditRepository;

    public List<AuditDTO> getAll() {
        return auditRepository.findAll().stream()
                .map(AuditDTO::new)
                .toList();
    }

    public List<AuditDTO> getByUsername(String username) {
        User user = (User) userService.loadUserByUsername(username);

        return auditRepository.findByUser(user).stream()
                .map(AuditDTO::new)
                .toList();
    }

}
