package com.Banking.Controller;

import com.Banking.Dto.Jwt.JwtRequest;
import com.Banking.Dto.Jwt.JwtResponse;
import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@Tag(name = "User Authentication API", description = "Operations related to Users Authentication")
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Operation(summary = "Register a new user")
    @PostMapping("/create-user")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody AppUserDto appUserDto) throws Exception {
        return ResponseEntity.ok(userServiceImpl.registerUser(appUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        return ResponseEntity.ok(userServiceImpl.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout() {
        return ResponseEntity.ok(userServiceImpl.logout());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
