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
public class PostsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private TokenDTO tokenDTO;

    @BeforeEach
    void loginBefore() {
        tokenDTO = userService.login(new UserDTO("post", "post", null));
    }

    @Test
    void testPostId1() throws Exception {
        mockMvc.perform(
                        get("/api/posts/1")
                                .header("Authorization", tokenDTO.getTokenType() + " " + tokenDTO.getToken())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\n" +
                        "  \"userId\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                        "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                        "}")));
    }


    @Test
    void testAccessDenied() throws Exception {
        mockMvc.perform(
                        get("/api/posts/")
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}