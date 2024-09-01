package com.Irctc.Dto.UserDto;


import com.Irctc.Model.User.AppUser;
import com.Irctc.Model.User.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDto {

    @NotBlank(message = "Name is Mandatory")
    private String name;

    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Please enter the valid Email")
    private String email;

    @NotNull(message = "Role is Mandatory")
    private Role role;

    @NotBlank(message = "Password is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // Only allow setting the password during input
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$",
            message = "Password must be at least 5 characters long, " +
                    "contain at least one digit, and one special character like @#$%^&+=")
    private String password;

    public AppUserDto(AppUser appUser) {
        this.name = appUser.getName();
        this.email = appUser.getEmail();
        this.role = appUser.getRole();
        this.password = appUser.getPassword();
    }

}
