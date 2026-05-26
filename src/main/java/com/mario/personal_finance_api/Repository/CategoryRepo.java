package com.mario.personal_finance_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mario.personal_finance_api.Models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long>{
    
}