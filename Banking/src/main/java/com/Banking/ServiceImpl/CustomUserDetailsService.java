package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Dto.UserDetails.CustomUserDetail;
import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(username).orElseThrow( ()->
                new ResourceNotFoundException("Invalid credentials..."));
        return new CustomUserDetail(new AppUserDto(user));
    }
}
