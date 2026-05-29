package com.mario.personal_finance_api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.Budget;
import com.mario.personal_finance_api.Models.Expense;
import com.mario.personal_finance_api.Models.User;
import com.mario.personal_finance_api.Repository.BudgetRepo;
import com.mario.personal_finance_api.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetRepo budgetRepo;
    private final UserRepo userRepo;

    @PostMapping("/user/{user_id}")
    public ResponseEntity<Budget> createBudget(@RequestParam Budget newBudget) {
        return ResponseEntity.ok(budgetRepo.save(newBudget));

    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Budget>> fetchUserBudget(@PathVariable Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("error fetching user"));
        return ResponseEntity.ok(budgetRepo.findAllByUserId(user.getId()));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable Long id) {
        Budget budget = budgetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        budgetRepo.delete(budget);
        return ResponseEntity.ok().build();
    }

}
