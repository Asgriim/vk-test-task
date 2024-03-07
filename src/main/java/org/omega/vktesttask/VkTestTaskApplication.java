package org.omega.vktesttask;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.entity.RoleEntity;
import org.omega.vktesttask.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@RequiredArgsConstructor
public class VkTestTaskApplication implements CommandLineRunner {


    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(VkTestTaskApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.insertDefaultUsers();
    }
}
