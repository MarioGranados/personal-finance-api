package com.mario.personal_finance_api.Controller;

import com.mario.personal_finance_api.Models.*;

import com.mario.personal_finance_api.Repository.ExpenseRepo;
import com.mario.personal_finance_api.Repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseRepo expenseRepo;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    // -----------------------------
    // TEST 1: GET expenses by user
    // -----------------------------
    @Test
    void shouldGetExpensesByUserId() throws Exception {

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setExpenseCost(50.0);

        when(expenseRepo.findAllByUserId(1L))
                .thenReturn(List.of(expense));

        mockMvc.perform(get("/api/expenses/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].expenseCost").value(50.0));
    }

    // -----------------------------
    // TEST 2: CREATE expense
    // -----------------------------
    @Test
    void shouldCreateExpense() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setUsername("mario");

        Expense expense = new Expense();
        expense.setExpenseCost(100.00);

        Expense savedExpense = new Expense();
        savedExpense.setId(1L);
        savedExpense.setExpenseCost(100.00);
        savedExpense.setUser(user);

        when(userRepo.findById(1L))
                .thenReturn(Optional.of(user));

        when(expenseRepo.save(any(Expense.class)))
                .thenReturn(savedExpense);

        mockMvc.perform(post("/api/expenses/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expenseCost").value(100.00));
    }
}