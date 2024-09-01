package com.Irctc.Service;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.JwtSecurity.JwtRequest;
import com.Irctc.JwtSecurity.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

     ApiResponse registerUser(AppUserDto appUserDto) throws Exception;

     JwtResponse login(JwtRequest request);

     ApiResponse logout(String tokenPassed);

}
