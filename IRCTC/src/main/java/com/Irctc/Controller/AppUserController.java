package com.Irctc.Controller;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.ServiceImpl.AppUserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "App User Management", description = "APIs for Managing Users")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/appUser")
public class AppUserController {

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @GetMapping("/getById")
    public ResponseEntity<AppUserDto> getAppUserById(@RequestParam Long id) {
        return ResponseEntity.ok(appUserServiceImpl.getUserById(id));
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAppUser(@RequestParam long id,
                                                     @RequestBody AppUserDto appUserDto) {
        return ResponseEntity.ok(appUserServiceImpl.updateUser(id, appUserDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAppUser(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(appUserServiceImpl.deleteUserById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<AppUserDto>> getAllAppUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        return ResponseEntity.ok(appUserServiceImpl.getAllUser(pageNumber, pageSize));
    }

}
