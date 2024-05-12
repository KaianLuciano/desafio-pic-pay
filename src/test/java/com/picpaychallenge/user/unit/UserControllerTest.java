package com.picpaychallenge.user.unit;

import com.picpaychallenge.common.domain.utils.JsonConverter;
import com.picpaychallenge.user.UserController;
import com.picpaychallenge.user.UserService;
import com.picpaychallenge.user.factory.UserFactory;
import com.picpaychallenge.user.payload.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private UserService userService;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("User Controller Create method should return Success")
    void testPostUser() throws Exception {
        UserDTO userDTO = UserFactory.getUserDTOUnitTest();
        when(userService.create(any())).thenReturn(userDTO);
        mvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(JsonConverter.asJson(UserFactory.getUserFormForPost()))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUser").exists())
                .andExpect(jsonPath("$.document.value").value(userDTO.getDocument().getValue()))
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()))
                .andExpect(jsonPath("$.typeUser").value(userDTO.getTypeUser().name()));
    }
}
