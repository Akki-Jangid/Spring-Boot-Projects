package com.Banking.Model.Account;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Branch.Branch;
import com.Banking.Model.Common.CommonBase;
import com.Banking.Model.Enum.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends CommonBase {

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private LocalDate createDate;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Branch branch;

    public Account(AccountDto accountDto, AppUser appUser, Branch branch){
        this.accountNumber = accountDto.getAccountNumber();
        this.accountType = accountDto.getAccountType();
        this.balance = accountDto.getBalance();
        this.createDate = accountDto.getCreateDate();
        this.user = appUser;
        this.branch = branch;
    }
}
