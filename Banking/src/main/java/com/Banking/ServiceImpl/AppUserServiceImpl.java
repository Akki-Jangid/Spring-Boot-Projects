package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Model.Loan.Loan;
import com.Banking.Model.Loan.LoanPayment;
import com.Banking.Repository.AppUserRepository;
import com.Banking.Service.AppUserService;
import com.Banking.Util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserUtil appUserUtil;
    @Autowired
    private LoanUtil loanUtil;
    @Autowired
    private LoanPaymentUtil paymentUtil;
    @Autowired
    private AccountUtil accountUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    public ApiResponse createUser(AppUserDto userDto) {
        AppUser user = new AppUser(userDto);
        appUserRepository.save(user);
        return new ApiResponse(true, "User Registered Successfully");
    }

    @Override
    public AppUserDto getUserById(Long id) {
        return new AppUserDto(appUserUtil.findById(id));
    }

    @Override
    public Page<AppUserDto> getAllUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AppUser> appUserPage = appUserRepository.findAll(pageable);
        List<AppUserDto> appUserDtoList = appUserPage.stream().map(AppUserDto::new).toList();

        return new PageImpl<>(appUserDtoList, pageable, appUserPage.getTotalElements());
    }

    @Override
    public ApiResponse updateUserById(Long id, AppUserDto userDto) {
        AppUser user = appUserUtil.findById(id);
        updateUserDetails(user, userDto);
        appUserRepository.save(user);
        return new ApiResponse(true, "User Details updated Successfully");
    }

    private void updateUserDetails(AppUser user, AppUserDto userDto) {
        if(!userDto.getUsername().isEmpty()) user.setUsername(userDto.getUsername());
        if(!userDto.getEmail().isEmpty()) user.setEmail(userDto.getEmail());
        if(!userDto.getPassword().isEmpty()) user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(!userDto.getPhoneNumber().isEmpty()) user.setPhoneNumber(userDto.getPhoneNumber());
        if(userDto.getRole()!=null) user.setRole(userDto.getRole());
    }

    @Override
    public ApiResponse deleteUserByEmail(String email) {
        AppUser user = appUserUtil.findByEmail(email);
        appUserUtil.deleteByEmail(email);
        return new ApiResponse(true, "User Account deleted Successfully");
    }

    @Override
    public ApiResponse deleteUserAccount() {
        AppUser user = tokenUtil.getAppUserFromToken();
        tokenUtil.deleteTokenFromRepository();
        appUserUtil.deleteByEmail(user.getEmail());
        return new ApiResponse(true, "Your Account deleted Successfully");
    }
}
