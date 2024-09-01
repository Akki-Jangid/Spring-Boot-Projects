package com.Banking.Service;

import com.Banking.Dto.Jwt.JwtRequest;
import com.Banking.Dto.Jwt.JwtResponse;
import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.ApiResponse;

public interface UserService {

    ApiResponse registerUser(AppUserDto appUserDto) throws Exception;

    JwtResponse login(JwtRequest request);

    ApiResponse logout();

}
