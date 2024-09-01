package com.Banking.Dto.Request;

import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please enter the valid email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$",
            message = "Password must be at least 5 characters long, " +
                    "contain at least one digit, and one special character like @#$%^&+=")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Role is mandatory")
    private Role role;

    @NotBlank(message = "PhoneNumber is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    public AppUserDto(AppUser user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
    }
}
