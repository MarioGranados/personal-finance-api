package com.mario.personal_finance_api.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.*;
import com.mario.personal_finance_api.Repository.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Expense>> getUserExpensesById(@PathVariable Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("Error fetching user!"));
        return ResponseEntity.ok(expenseRepo.findAllByUserId(user.getId()));
    }

    @PostMapping("/user/{user_id}")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @PathVariable @NonNull Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("Error fetching user"));
        Category category = categoryRepo.findByCategoryName(expense.getCategory());

        if(category == null) {
            category = new Category();
            category.setCategoryName();
            category.setUser(user);

            categoryRepo.save(category);
        }
        expense.setUser(user);
        expense.setCategory(category);
        return ResponseEntity.ok(expenseRepo.save(expense));
    }
k

}
