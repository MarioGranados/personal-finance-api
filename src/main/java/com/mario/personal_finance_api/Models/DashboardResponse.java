package com.mario.personal_finance_api.Models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long userId;
    private String username;
    private List<Expense> expenses;
    private double totalSpent;
}
