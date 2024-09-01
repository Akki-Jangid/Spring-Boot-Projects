package com.Banking.Dto.Response;

import com.Banking.Dto.Request.AppUserDto;
import com.Banking.Dto.Request.BranchDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.Enum.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class AccountResponseDto {

    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private LocalDate createDate;
    private AppUserDto userDto;
    private BranchDto branchDto;

    public AccountResponseDto(Account account){
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType();
        this.balance = account.getBalance();
        this.createDate = account.getCreateDate();
        this.userDto = new AppUserDto(account.getUser());
        this.branchDto = new BranchDto(account.getBranch());
    }
}
