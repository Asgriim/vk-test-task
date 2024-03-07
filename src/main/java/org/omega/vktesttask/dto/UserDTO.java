package org.omega.vktesttask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omega.vktesttask.entity.Role;
import org.omega.vktesttask.entity.RoleEntity;
import org.omega.vktesttask.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String password;
    private List<Role> roles;

    public UserDTO(User user) {
        login = user.getLogin();
        password = user.getPassword();
        if (user.getRoleEntities() != null) {
            roles = user.getRoleEntities().stream().map(RoleEntity::getRole).toList();
        }

    }
}
