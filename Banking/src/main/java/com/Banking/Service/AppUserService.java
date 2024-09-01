package com.Banking.Service;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

public interface AppUserService {

    ApiResponse createUser(AppUserDto userDto);

    AppUserDto getUserById(Long id);

    Page<AppUserDto> getAllUsers(int pageNumber, int pageSize);

    ApiResponse updateUserById(Long id, AppUserDto userDto);

    ApiResponse deleteUserByEmail(String email);

    ApiResponse deleteUserAccount();
}
