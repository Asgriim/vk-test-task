package org.omega.vktesttask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omega.vktesttask.dto.TokenDTO;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private TokenDTO tokenDTO;

    @BeforeEach
    void loginBefore() {
        tokenDTO = userService.login(new UserDTO("album", "album", null));
    }

    @Test
    void testAlbumId1() throws Exception {
        mockMvc.perform(
                get("/api/albums/1")
                .header("Authorization", tokenDTO.getTokenType() + " " + tokenDTO.getToken())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\n" +
                        "  \"userId\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"title\": \"quidem molestiae enim\"\n" +
                        "}")));
    }


    @Test
    void testAccessDenied() throws Exception {
        mockMvc.perform(
                        get("/api/albums/")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}