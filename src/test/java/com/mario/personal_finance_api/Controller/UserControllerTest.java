package com.mario.personal_finance_api.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.http.MediaType;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mario.personal_finance_api.Models.LoginRequest;
import com.mario.personal_finance_api.Models.User;
import com.mario.personal_finance_api.Repository.UserRepo;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepo userRepo;

    @Test
    void shouldCreateUser() throws Exception {

        User user = new User();
        user.setUsername("mario");
        user.setPassword("password");

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
    void shouldLoginUser() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setUsername("mario");
        user.setPassword("1234");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mario");
        loginRequest.setPassword("1234");

        when(userRepo.findByUsername("mario")).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("mario"));
    }

    @Test
    void shouldFailLoginWrongPassword() throws Exception {

        User user = new User();
        user.setUsername("mario");
        user.setPassword("1234");

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mario");
        loginRequest.setPassword("wrong");

        when(userRepo.findByUsername("mario")).thenReturn(user);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldFailLoginUserNotFound() throws Exception {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ghost");
        loginRequest.setPassword("1234");

        when(userRepo.findByUsername("ghost")).thenReturn(null);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }
}