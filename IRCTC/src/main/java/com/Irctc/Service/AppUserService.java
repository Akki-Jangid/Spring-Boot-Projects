package com.Irctc.Service;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.Model.User.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface AppUserService {

    AppUser findAppUserById(Long id);

    AppUser findAppUserByEmail(String email);

    AppUserDto getUserById(long id);

    Page<AppUserDto> getAllUser(int pageNumber, int pageSize);

    ApiResponse updateUser(Long id, AppUserDto appUserDto);

    ApiResponse deleteUserById(long id);
}
