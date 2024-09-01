package com.Irctc.Model.User;

import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.Model.Booking;
import com.Irctc.Model.Common.CommonBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.awt.print.Book;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser extends CommonBase implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    public AppUser(AppUserDto appUserDto){
        this.name = appUserDto.getName();
        this.email = appUserDto.getEmail();
        this.password = appUserDto.getPassword();
        this.role = appUserDto.getRole();
    }
}

