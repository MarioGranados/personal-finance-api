package com.mario.personal_finance_api.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mario.personal_finance_api.Models.Expense;
import com.mario.personal_finance_api.Models.LoginRequest;
import com.mario.personal_finance_api.Models.User;
import com.mario.personal_finance_api.Repository.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepo;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        return ResponseEntity.ok(userRepo.save(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody LoginRequest loginRequest) {

        User user = userRepo.findByUsername(loginRequest.getUsername());
        if(user == null) {
            return ResponseEntity.status(404).build();
        }
        if(user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok().body(user);
        } 
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        userRepo.delete(user);
        return ResponseEntity.ok().build();
    }



}
