package com.mario.personal_finance_api.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import com.mario.personal_finance_api.Models.User;
import com.mario.personal_finance_api.Repository.UserRepo;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @Test
    void shouldCreateUser() throws Exception {

        User user = new User();
        user.setUsername("mario");

        when(userRepo.save(any(User.class))).thenReturn(user);

        String json = """
                    {
                        "username": "mario"
                    }
                """;

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mario"));
    }

    @Test
    void shouldGetUserByUsername() throws Exception {

        User user = new User();
        user.setUsername("mario");

        when(userRepo.findByUsername("mario"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/mario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mario"));
    }
}