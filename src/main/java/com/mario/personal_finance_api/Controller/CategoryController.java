package com.mario.personal_finance_api.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.User;
import com.mario.personal_finance_api.Models.Category;

import com.mario.personal_finance_api.Repository.CategoryRepo;
import com.mario.personal_finance_api.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepo categoryRepo;
    private final UserRepo userRepo;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Category>> getCategoriesByUserId(@PathVariable Long user_id) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new RuntimeException("Error fetching user"));
        return ResponseEntity.ok(categoryRepo.findAllByUserId(user.getId()));
    }

    @PostMapping("/user/{user_id}")
    public ResponseEntity<Category> createCategory(@RequestParam Category newCategory) {
        return ResponseEntity.ok(categoryRepo.save(newCategory));
    }

}
