package org.omega.vktesttask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omega.vktesttask.controllers.AuthController;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.exceptions.InvalidLoginOrPasswordException;
import org.omega.vktesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthTests {
    @Autowired
    private AuthController authController;

    @Autowired
    private UserService userService;

    private UserDTO testUser;

    @BeforeEach
    void createTestUser() {
        testUser = new UserDTO("username","passs", Collections.singletonList(Role.ALBUM_DELETER));
    }

    @AfterEach
    void deleteTestUser() {
        userService.deleteByLogin(testUser.getLogin());
    }

    @Test
    void testRegister() {
        assertThat(authController.register(testUser).getStatusCode().is2xxSuccessful())
                .isTrue();
    }

    @Test
    void testLogin() {
        assertThat(authController.register(testUser).getStatusCode().is2xxSuccessful())
                .isTrue();
        assertThat(authController.login(testUser).getStatusCode().is2xxSuccessful())
                .as("must return 200")
                .isTrue();
    }

    @Test
    void testInvalidPassword() {
        assertThat(authController.register(testUser).getStatusCode().is2xxSuccessful())
                .isTrue();
        testUser.setPassword("s");
        assertThrows(InvalidLoginOrPasswordException.class,() -> authController.login(testUser));
    }

    @Test
    void testRegisterBadAdmin() {
        testUser.setRoles(Collections.singletonList(Role.ADMIN));
        assertThat(authController.register(testUser).getStatusCode().is4xxClientError())
                .as("permission must be denied")
                .isTrue();
    }


}
