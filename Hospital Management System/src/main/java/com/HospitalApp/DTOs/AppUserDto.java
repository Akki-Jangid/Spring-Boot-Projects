package com.HospitalApp.DTOs;

import com.HospitalApp.Model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class AppUserDto {

    @JsonIgnore
    private long id;

    @NotBlank(message = "Username is Mandatory")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Enter Valid Email")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotNull(message = "Role is Mandatory")
    private Role role;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$",
            message = "Password must be at least 5 characters long, " +
                    "contain at least one digit, and one special character like @#$%^&+=")
    private String password;


    public AppUserDto(AppUser appUser){
        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.email = appUser.getEmail();
        this.phoneNumber = appUser.getPhoneNumber();
        this.role = appUser.getRole();
    }
}
