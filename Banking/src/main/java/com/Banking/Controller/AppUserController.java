package com.Banking.Controller;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.AppUserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
@Tag(name = "User API", description = "Operations related to Users")
public class AppUserController {

    @Autowired
    private AppUserServiceImpl appUserService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody AppUserDto userDto){
        return ResponseEntity.ok(appUserService.createUser(userDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<AppUserDto> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(appUserService.getUserById(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Page<AppUserDto>> getAllUser(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(appUserService.getAllUsers(pageNumber, pageSize));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse> updateUserById(@RequestParam Long id,
                                                        @RequestBody AppUserDto userDto) {
        return ResponseEntity.ok(appUserService.updateUserById(id, userDto));
    }

    @DeleteMapping("/deleteByEmail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(appUserService.deleteUserByEmail(email));
    }

    @DeleteMapping("/deleteMyAccount")
    public ResponseEntity<ApiResponse> deleteUserAccount() {
        return ResponseEntity.ok(appUserService.deleteUserAccount());
    }
}
