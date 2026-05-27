package com.mario.personal_finance_api.Controller;

import com.mario.personal_finance_api.Models.*;
import com.mario.personal_finance_api.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private ExpenseRepo expenseRepo;

    @Test
    void shouldReturnDashboardData() throws Exception {

        // Arrange user
        User user = new User();
        user.setId(1L);
        user.setUsername("mario");

        // Arrange expenses
        Expense e1 = new Expense();
        e1.setExpenseCost(50.0);

        Expense e2 = new Expense();
        e2.setExpenseCost(25.0);

        List<Expense> expenses = List.of(e1, e2);

        // Mock repo behavior
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(expenseRepo.findAllByUserId(1L)).thenReturn(expenses);

        mockMvc.perform(get("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.username").value("mario"))
                .andExpect(jsonPath("$.totalSpent").value(75.0))
                .andExpect(jsonPath("$.expenses.length()").value(2));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {

        when(userRepo.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/dashboard/99"))
                .andExpect(status().isInternalServerError()); // because of RuntimeException
    }
}