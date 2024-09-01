package com.Irctc.Controller;

import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.JwtSecurity.JwtRequest;
import com.Irctc.ServiceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "API for Authentication")
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Operation(summary = "Register a new user")
    @PostMapping("/create-user")
    public ResponseEntity<?> registerUser(@RequestBody AppUserDto appUserDto) {
        return new ResponseEntity<>(userServiceImpl.registerUser(appUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        return new ResponseEntity<>(userServiceImpl.login(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Authorization",
                    required = true,
                    schema = @Schema(type = "string", format = "Bearer <JWT>"))
            @RequestHeader("Authorization") String authorizationHeader) {
        return new ResponseEntity<>(userServiceImpl.logout(authorizationHeader), HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
