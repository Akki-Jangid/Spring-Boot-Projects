package com.Banking.Dto.UserDetails;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {

    private String name;
    private String email;
    private Role role;
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getPassword() { return this.password;  }

    @Override
    public String getUsername() { return this.email;  }

    public CustomUserDetail(AppUserDto appUserDto){
        this.name = appUserDto.getUsername();
        this.email = appUserDto.getEmail();
        this.password = appUserDto.getPassword();
        this.role = appUserDto.getRole();
    }
}
