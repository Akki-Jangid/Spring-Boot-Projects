package com.Banking.Model.AppUser;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.CommonBase;
import com.Banking.Model.Common.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser extends CommonBase {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    public AppUser(AppUserDto userDto){
        this.username = userDto.getUsername();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.role = userDto.getRole();
        this.phoneNumber = userDto.getPhoneNumber();
    }

}
