package com.Irctc.ServiceImpl;

import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.Model.User.AppUser;
import com.Irctc.Repository.AppUserRepository;
import com.Irctc.Service.AppUserService;
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
        return appUserRepository.findByEmail(username).orElseThrow( ()->
                new ResourceNotFoundException("Invalid credentials..."));
    }
}
