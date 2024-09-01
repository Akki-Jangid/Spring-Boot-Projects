package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Dto.Response.AccountResponseDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Branch.Branch;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Repository.AccountRepository;
import com.Banking.Service.AccountService;
import com.Banking.Util.AccountUtil;
import com.Banking.Util.AppUserUtil;
import com.Banking.Util.BranchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AppUserUtil appUserUtil;

    @Autowired
    private BranchUtil branchUtil;

    @Autowired
    private AccountUtil accountUtil;

    @Override
    public ApiResponse createAccount(AccountDto accountDto) {
        Account account = storingAccountDetails(accountDto);
        accountRepository.save(account);
        return new ApiResponse(true, "Account Created Successfully");
    }

    private Account storingAccountDetails(AccountDto accountDto) {
        AppUser appUser = appUserUtil.findById(accountDto.getAppUserId());
        Branch branch =  branchUtil.findById(accountDto.getBranchId());
        Account account = new Account(accountDto, appUser, branch);
        account.setCreateDate(LocalDate.now());
        return account;
    }

    @Override
    public AccountResponseDto getAccountById(Long id) {
        return new AccountResponseDto(accountUtil.findById(id));
    }

    @Override
    public Page<AccountResponseDto> getAllAccount(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<AccountResponseDto> accountResponseDtoList = accountPage.stream().
                map(AccountResponseDto::new).toList();
        return new PageImpl<>(accountResponseDtoList, pageable, accountPage.getTotalElements());
    }

    @Override
    public Page<AccountResponseDto> getAllAccountWithSearchAndFilter(String username, String date, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Account> accountPage = validateInput(username, date, pageable);
        List<AccountResponseDto> accountResponseDtoList =
                accountPage.stream().map(AccountResponseDto::new).toList();
        return new PageImpl<>(accountResponseDtoList, pageable, accountPage.getTotalElements());
    }

    private Page<Account> validateInput(String username, String date, Pageable pageable) {
        Page<Account> accountPage;
        if(username==null && date==null)  return accountPage = accountRepository.findAll(pageable);
        else {
            if(username==null) username = "";
            if(date==null) date="" ;
            return accountRepository.findBySearchAndFilter(username, date, pageable);
        }
    }

    @Override
    public ApiResponse updateAccountById(Long id, AccountDto accountDto) {
        Account account = accountUtil.findById(id);
        updateAccountDetails(account, accountDto);
        return new ApiResponse(true, "Account Details updated Successfully");
    }

    private void updateAccountDetails(Account account, AccountDto accountDto) {
        if(!accountDto.getAccountNumber().isEmpty()) account.setAccountNumber(accountDto.getAccountNumber());
        if(accountDto.getAccountType()!=null) account.setAccountType(accountDto.getAccountType());
        if(accountDto.getBalance()!=null) account.setBalance(accountDto.getBalance());
        if(accountDto.getCreateDate()!=null) account.setCreateDate(accountDto.getCreateDate());
        if(accountDto.getCreateDate()!=null) account.setCreateDate(accountDto.getCreateDate());
        if(accountDto.getBranchId()!=null) {
            Branch branch = branchUtil.findById(accountDto.getBranchId());
            account.setBranch(branch);
        }
        if(accountDto.getAppUserId()!=null) {
            AppUser appUser = appUserUtil.findById(accountDto.getAppUserId());
            account.setUser(appUser);
        }
    }

    @Override
    public ApiResponse deleteAccountByAccountNumber(String accountNumber) {
        accountUtil.deleteByAccountNumber(accountNumber);
        return new ApiResponse(true, "Account Deleted Successfully");
    }
}
