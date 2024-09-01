package com.Irctc.ServiceImpl;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.JwtSecurity.JwtHelper;
import com.Irctc.JwtSecurity.JwtRequest;
import com.Irctc.JwtSecurity.JwtResponse;
import com.Irctc.Model.Common.Token;
import com.Irctc.Model.User.AppUser;
import com.Irctc.Repository.AppUserRepository;
import com.Irctc.Repository.TokenRepository;
import com.Irctc.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;


    @Override
    public ApiResponse registerUser(AppUserDto appUserDto) throws ResourceNotFoundException {

        if (appUserRepository.existsByEmail(appUserDto.getEmail()))
            throw new ResourceNotFoundException("Email is already taken, Choose something else");
        appUserDto.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        AppUser user = new AppUser(appUserDto);
        appUserRepository.save(user);
        return new ApiResponse(true, "User Registered Successfully...");
    }

    @Override
    public JwtResponse login(JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        // This is Used to Store the Token in DB
        tokenRepository.save(new Token(token, userDetails.getUsername()));
        return JwtResponse.builder().jwtToken(token).username(userDetails.getUsername()).build();
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            this.manager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password  !!");
        }
    }

    @Override
    public ApiResponse logout(String tokenPassed) {
        String token = tokenPassed.replace("Bearer ", "");
        log.info("Token is " + token);

        if (!tokenRepository.existsByToken(token)) {
            throw new ResourceNotFoundException("No User Logged In");
        }
        tokenRepository.deleteByToken(token);
        return new ApiResponse(true, "Logged out Successfully");
    }
}


