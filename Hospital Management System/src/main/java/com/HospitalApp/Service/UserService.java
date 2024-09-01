package com.HospitalApp.Service;

import com.HospitalApp.DTOs.AppUserDto;
import com.HospitalApp.JwtSecurity.JwtRequest;
import com.HospitalApp.JwtSecurity.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public void registerUser(AppUserDto appUserDto) throws Exception;

    public JwtResponse login(JwtRequest request);

    public ResponseEntity<?> logout(String tokenPassed);

}
