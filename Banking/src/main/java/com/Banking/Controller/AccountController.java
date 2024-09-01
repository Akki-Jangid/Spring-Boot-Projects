package com.Banking.Controller;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Dto.Response.AccountResponseDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@PreAuthorize("hasRole('MANAGER')")
@Tag(name = "Account API", description = "Operations related to Accounts")
public class AccountController {

    @Autowired
    private AccountServiceImpl AccountService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addAccount(@Valid @RequestBody AccountDto AccountDto){
        return ResponseEntity.ok(AccountService.createAccount(AccountDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<AccountResponseDto> getAccountById(@RequestParam Long id) {
        return ResponseEntity.ok(AccountService.getAccountById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<AccountResponseDto>> getAllAccount(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(AccountService.getAllAccount(pageNumber, pageSize));
    }

    @GetMapping("/getAllBySearchAndFilter")
    public ResponseEntity<Page<AccountResponseDto>> getAllAccountBySearchAndFilter(@RequestParam(required = false) String username,
                                                        @RequestParam(required = false) String date,
                                                        @RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(AccountService.getAllAccountWithSearchAndFilter(username, date, pageNumber, pageSize));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateAccountById(@RequestParam Long id,
                                                        @RequestBody AccountDto AccountDto) {
        return ResponseEntity.ok(AccountService.updateAccountById(id, AccountDto));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAccountByAccountNumber(@RequestParam String accountNumber) {
        return ResponseEntity.ok(AccountService.deleteAccountByAccountNumber(accountNumber));
    }
}
