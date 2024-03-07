package org.omega.vktesttask;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.entity.RoleEntity;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.exceptions.*;
import org.omega.vktesttask.repository.UserRepository;
import org.omega.vktesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final String adminLogin = "adminTest";

    private final String adminPass = "passTest";

    User createAdminUser() {
        User adminUser = new User();
        adminUser.setPassword(encoder.encode(adminPass));
        adminUser.setLogin(adminLogin);
        adminUser.setRoleEntities(Collections.singletonList(new RoleEntity(Role.ADMIN)));
        return adminUser;

    }

    @BeforeEach
    void initBefore() {
        userRepository.deleteAll();
        userRepository.save(createAdminUser());
    }

    @AfterEach
    void deleteAfterEach() {
        userRepository.deleteAll();
    }

    @Test
    void testUserNotFound() {
        assertThrows(UsernameNotFoundException.class,() -> userService.loadUserByUsername(""));
    }

    @Test
    void testLoadByUsername() {

        User user = (User) userService.loadUserByUsername(adminLogin);
        System.out.println(user);
        assertNotNull(user);
        assertEquals(adminLogin, user.getLogin());
        assertTrue(encoder.matches(adminPass, user.getPassword()));
        assertEquals(Role.ADMIN,user.getRoleEntities().get(0).getRole());

    }

    @Test
    void testRegisterAlreadyExist() {
        User adminUser = createAdminUser();
        assertThrows(UserAlreadyExistException.class,() -> userService.addUser(adminUser));
    }

    @Test
    void testRegisterDTO() {
        User user = createAdminUser();
        UserDTO userDTO = new UserDTO(user);

        assertTrue(userRepository.existsByLogin(userDTO.getLogin()));
        assertThrows(UserAlreadyExistException.class,() -> userService.register(userDTO));

        userDTO.setLogin("login");
        userService.register(userDTO);

        assertTrue(userRepository.existsByLogin(userDTO.getLogin()));

        user = userRepository.findUserByLogin(userDTO.getLogin());

        assertNotNull(user);
        assertEquals(Role.ADMIN,user.getRoleEntities().get(0).getRole());
    }

    @Test
    void testEmptyLoginReg() {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("s");
        assertThrows(EmptyLoginException.class,()-> userService.register(userDTO));
    }

    @Test
    void testEmptyPassReg() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("s");
        assertThrows(EmptyPasswordException.class,()-> userService.register(userDTO));
    }

    @Test
    void testEmptyRoleReg() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("s");
        userDTO.setPassword("s");
        assertThrows(EmptyRoleException.class,()-> userService.register(userDTO));
    }

    @Test
    void testLogin() {
        UserDTO userDTO = new UserDTO(createAdminUser());
        userDTO.setPassword(adminPass);
        assertNotNull(userService.login(userDTO));
    }

    @Test
    void testInvalidPass() {
        UserDTO userDTO = new UserDTO(createAdminUser());
        userDTO.setPassword("invalid");
        assertThrows(InvalidLoginOrPasswordException.class, () -> userService.login(userDTO));
    }
}
