package com.mario.personal_finance_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mario.personal_finance_api.Models.Expense;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserId(Long userId);
}
