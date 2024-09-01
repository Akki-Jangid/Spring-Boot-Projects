//package com.Irctc.Dto.UserDto;
//
//import com.Irctc.Model.User.AppUser;
//import com.Irctc.Model.User.Role;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@NoArgsConstructor
//@Setter
//@Getter
//public class AppUserDetailsDto implements UserDetails {
//
//    private String email;
//    private Role role;
//    private String password;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(getRole().name()));
//    }
//
//    @Override
//    public String getPassword() { return this.password; }
//
//    @Override
//    public String getUsername() {
//        return this.getEmail();
//    }
//
//    public AppUserDetailsDto(AppUser appUser){
//        this.email = appUser.getEmail();
//        this.password = appUser.getPassword();
//        this.role = appUser.getRole();
//    }
//}
