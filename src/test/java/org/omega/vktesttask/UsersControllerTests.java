package org.omega.vktesttask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omega.vktesttask.dto.TokenDTO;
import org.omega.vktesttask.dto.UserDTO;
import org.omega.vktesttask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private TokenDTO tokenDTO;

    @BeforeEach
    void loginBefore() {
        tokenDTO = userService.login(new UserDTO("user", "user", null));
    }

    @Test
    void testUsersId1() throws Exception {
        mockMvc.perform(
                        get("/api/users/1")
                                .header("Authorization", tokenDTO.getTokenType() + " " + tokenDTO.getToken())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Leanne Graham\",\n" +
                                "  \"username\": \"Bret\",\n" +
                                "  \"email\": \"Sincere@april.biz\",\n" +
                                "  \"address\": {\n" +
                                "    \"street\": \"Kulas Light\",\n" +
                                "    \"suite\": \"Apt. 556\",\n" +
                                "    \"city\": \"Gwenborough\",\n" +
                                "    \"zipcode\": \"92998-3874\",\n" +
                                "    \"geo\": {\n" +
                                "      \"lat\": \"-37.3159\",\n" +
                                "      \"lng\": \"81.1496\"\n" +
                                "    }\n" +
                                "  },\n" +
                                "  \"phone\": \"1-770-736-8031 x56442\",\n" +
                                "  \"website\": \"hildegard.org\",\n" +
                                "  \"company\": {\n" +
                                "    \"name\": \"Romaguera-Crona\",\n" +
                                "    \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                                "    \"bs\": \"harness real-time e-markets\"\n" +
                                "  }\n" +
                                "}"
                )));
    }


    @Test
    void testAccessDenied() throws Exception {
        mockMvc.perform(
                        get("/api/users/")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}