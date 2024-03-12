package org.omega.vktesttask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    ADMIN("ADMIN"),

    POST_VIEWER("POST_VIEWER"),
    POST_EDITOR("POST_EDITOR"),
    POST_DELETER("POST_DELETER"),
    POST_CREATOR("POST_CREATOR"),

    USER_VIEWER("USER_VIEWER"),
    USER_EDITOR("USER_EDITOR"),
    USER_CREATOR("USER_CREATOR"),
    USER_DELETER("USER_DELETER"),

    ALBUM_VIEWER("ALBUM_VIEWER"),
    ALBUM_EDITOR("ALBUM_EDITOR"),
    ALBUM_CREATOR("ALBUM_CREATOR"),
    ALBUM_DELETER("ALBUM_DELETER");

    private String roleName;

}
