package com.Irctc.ServiceImpl;

import com.Irctc.Dto.ApiDto.ApiResponse;
import com.Irctc.Dto.UserDto.AppUserDto;
import com.Irctc.ExceptionHandler.ResourceNotFoundException;
import com.Irctc.JwtSecurity.JwtHelper;
import com.Irctc.Model.User.AppUser;
import com.Irctc.Repository.AppUserRepository;
import com.Irctc.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AppUserRepository appUserRepository;

    public AppUser findAppUserById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    public AppUser findAppUserByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));
    }

    @Override
    public AppUserDto getUserById(long id) {
        AppUser appUser = findAppUserById(id);
        return new AppUserDto(appUser);
    }

    @Override
    public Page<AppUserDto> getAllUser(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<AppUser> appUserPage = appUserRepository.findAll(pageable);
        List<AppUserDto> appUserList = appUserPage.stream().map(AppUserDto::new).toList();

        return new PageImpl<>(appUserList, pageable, appUserPage.getTotalElements());
    }

    @Override
    public ApiResponse updateUser(Long id, AppUserDto appUserDto) {

        AppUser appUser = findAppUserById(id);

        if(!appUserDto.getName().isEmpty()) appUser.setName(appUserDto.getName());
        if(!appUserDto.getPassword().isEmpty()) appUser.setPassword(passwordEncoder.encode(appUserDto.getPassword()));
        if(!appUserDto.getEmail().isEmpty())  appUser.setEmail(appUserDto.getEmail());
        if(appUserDto.getRole()!=null)  appUser.setRole(appUserDto.getRole());

        appUserRepository.save(appUser);
        return new ApiResponse(true, "User Updated Successfully!");
    }

    @Override
    public  ApiResponse deleteUserById(long id) {
        AppUser appUser = findAppUserById(id);
        appUserRepository.delete(appUser);
        return new ApiResponse(true, "User Delete Successfully!");
    }

}
