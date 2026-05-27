package com.mario.personal_finance_api.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mario.personal_finance_api.Models.Budget;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    List<Budget> findAllByUserId(Long id);

}
