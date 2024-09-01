package com.Banking.ServiceImpl;

import com.Banking.Dto.Jwt.JwtRequest;
import com.Banking.Dto.Jwt.JwtResponse;
import com.Banking.Dto.Jwt.Token;
import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Jwt.JwtHelper;
import com.Banking.Util.JwtTokenUtil;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Repository.AppUserRepository;
import com.Banking.Repository.TokenRepository;
import com.Banking.Service.UserService;
import com.Banking.Util.AppUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AppUserRepository appUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AppUserUtil userUtil;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil tokenDetails;

    @Override
    public ApiResponse registerUser(AppUserDto appUserDto) throws Exception {
        userUtil.existsByEmail(appUserDto.getEmail());
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
    public ApiResponse logout() {
        String token = tokenDetails.getTokenFromHeader();
        log.info("Token is " + token);

        if (!tokenRepository.existsByToken(token)) {
            throw new ResourceNotFoundException("No User Logged In");
        }
        tokenRepository.deleteByToken(token);
        return new ApiResponse(true, "Logged out Successfully");
    }
}
