package com.mario.personal_finance_api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.*;
import com.mario.personal_finance_api.Repository.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

    private final UserRepo userRepo;
    private final ExpenseRepo expenseRepo;
    private final BudgetRepo budgetRepo;
    private final CategoryRepo categoryRepo;

    @GetMapping("/{user_id}")
    public ResponseEntity<DashboardResponse> getDashboard(@PathVariable Long user_id) {

        return userRepo.findById(user_id)
                .map(user -> {

                    List<Expense> expenses = expenseRepo.findAllByUserId(user_id);

                    double totalSpent = expenses.stream()
                            .mapToDouble(Expense::getExpenseCost)
                            .sum();

                    DashboardResponse res = new DashboardResponse(
                            user.getId(),
                            user.getUsername(),
                            expenses,
                            totalSpent);

                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(500).build());
    }
}
