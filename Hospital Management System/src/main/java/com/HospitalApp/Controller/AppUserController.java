package com.HospitalApp.Controller;

import com.HospitalApp.DTOs.AppUserDto;
import com.HospitalApp.Service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appUser")
@Validated
@PreAuthorize("hasRole('USER')")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/getById")
    public ResponseEntity<AppUserDto> getAppUserById(@Valid @RequestParam(name = "id") Long id){
        return ResponseEntity.ok(appUserService.getAppUserById(id));
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateAppUser(@Valid @RequestParam int id,
                                                    @RequestBody AppUserDto appUserDto){
        appUserService.updateAppUser(id, appUserDto);
        return new ResponseEntity<>("AppUser Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAppUser(@RequestParam(name = "id") Long id){
        appUserService.deleteAppUserById(id);
        return new ResponseEntity<>("AppUser Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<AppUserDto>> getAllAppUsers(
                        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        return ResponseEntity.ok(appUserService.getAllAppUsers(pageNumber, pageSize));
    }
}
