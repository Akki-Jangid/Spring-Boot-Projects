package com.Banking.Service;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Dto.Response.AccountResponseDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    ApiResponse createAccount(AccountDto AccountDto);

    AccountResponseDto getAccountById(Long id);

    Page<AccountResponseDto> getAllAccount(int pageNumber, int pageSize);

    Page<AccountResponseDto> getAllAccountWithSearchAndFilter(String username, String date, int pageNumber, int pageSize);

    ApiResponse updateAccountById(Long id, AccountDto AccountDto);

    ApiResponse deleteAccountByAccountNumber(String accountNumber);
}
