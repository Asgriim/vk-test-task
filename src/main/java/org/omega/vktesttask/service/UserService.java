package org.omega.vktesttask.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.omega.vktesttask.dto.TokenDTO;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.exceptions.*;
import org.omega.vktesttask.repository.UserRepository;
import org.omega.vktesttask.security.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JWT jwt;

    @Value("${reg.admin.login}")
    private String adminLogin;

    @Value("${reg.admin.pass}")
    private String adminPass;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean isValidUser(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new EmptyLoginException();
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new EmptyPasswordException();
        }
        if (user.getRoleEntities() == null || user.getRoleEntities().isEmpty()) {
            throw new EmptyRoleException();
        }
        return true;
    }

    public void addUser(User user) {
        if (isValidUser(user)) {
            if (userRepository.existsByLogin(user.getLogin())) {
                throw new UserAlreadyExistException();
            }
            userRepository.save(user);
        }
    }

    public void register(UserDTO userDTO) {
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new EmptyPasswordException();
        }
        User user = new User(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        addUser(user);
    }

    public TokenDTO login(UserDTO userDTO) {
        User user = new User(userDTO);
        if (isValidUser(user)) {
            UserDetails userToLogin = loadUserByUsername(user.getUsername());
            if (!encoder.matches(user.getPassword(), userToLogin.getPassword())) {
                throw new InvalidLoginOrPasswordException();
            }
            String jwtToken = jwt.generateToken(user.getLogin());
            return new TokenDTO(jwtToken);
        }
        //can't reach here
        throw new InvalidLoginOrPasswordException();
    }


    public void insertDefaultUsers() {
        UserDTO adminUser = new UserDTO();

        adminUser.setLogin(adminLogin);
        adminUser.setPassword(adminPass);
        adminUser.setRoles(new ArrayList<>());
        adminUser.getRoles().add(Role.ADMIN);

        register(adminUser);
        System.out.println(loadUserByUsername(adminLogin));
    }

    public void deleteByLogin(String login){
        userRepository.deleteUserByLogin(login);
    }
}
