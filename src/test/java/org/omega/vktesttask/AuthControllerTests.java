package org.omega.vktesttask;

import org.junit.jupiter.api.Test;
import org.omega.vktesttask.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testValidLogin() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .content("{\n" +
                                "  \"login\": \"admin\",\n" +
                                "  \"password\": \"pass\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
    }

    @Test
    void testInvalidLogin() throws Exception {
        mockMvc.perform(
                post("/api/auth/login")
                        .content("a")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testInvalidRegister() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .content("{\n" +
                                "  \"login\": \"admin\",\n" +
                                "  \"password\": \"pass\"\n" +

                                "}")
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
