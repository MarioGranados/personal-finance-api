package com.mario.personal_finance_api.Controller;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.*;
import com.mario.personal_finance_api.Repository.*;

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
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @PathVariable Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("Error fetching user"));
        if (expense.getCategory() == null) {
            throw new RuntimeException("no category found");

        }
        Category category;
        if (expense.getCategory().getId() == null) {
            category = categoryRepo.findById(expense.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

        } else {
            category = new Category();
            category.setCategoryName(expense.getCategory().getCategoryName());
            category.setUser(user);
            categoryRepo.save(category);

        }
        expense.setUser(user);
        expense.setCategory(category);
        return ResponseEntity.ok(expenseRepo.save(expense));
    }

    // @PutMapping("/edit/{expense_id}")
    // public ResponseEntity<Expense> editExpense(@RequestBody Expense expense, @PathVariable Long expense_id) {
    //     expense = expenseRepo.findById(expense.getCategory().getId())
    //             .orElseThrow(() -> new RuntimeException("Category not found"));
    //     Expense newExpense = expense;
        
    //     return ResponseEntity.ok(expenseRepo.save(newExpense));
    // }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Long id) {
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepo.delete(expense);
        return ResponseEntity.ok().build();
    }

}
