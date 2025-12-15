package com.hypermarket.auth_service.controller;

import com.hypermarket.auth_service.dto.*;
import com.hypermarket.auth_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ده بيستقبل HTTP Requests ويرجع Responses
@RestController
@RequestMapping("/api/users")  // كل الـ endpoints تبدأ بـ /api/users
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // POST /api/users/register - تسجيل مستخدم جديد
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // POST /api/users/login - تسجيل الدخول
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
    
    // GET /api/users - جلب كل المستخدمين
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    // GET /api/users/{id} - جلب مستخدم معين
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    // PUT /api/users/{id} - تحديث مستخدم
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, 
                                                    @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }
    
    // DELETE /api/users/{id} - حذف مستخدم
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}