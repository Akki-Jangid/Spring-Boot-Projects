package com.Banking.Dto.Request;

import com.Banking.Model.Account.Account;
import com.Banking.Model.Enum.AccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @NotBlank(message = "AccountNumber is mandatory")
    private String accountNumber;

    @NotNull(message = "Account type cannot be null")
    private AccountType accountType;

    @Positive(message = "Balance must be positive")
    private BigDecimal balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate createDate;

    @NotNull(message = "Userid is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long appUserId;

    @NotNull(message = "Branch_id is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long branchId;

    public AccountDto(Account account){
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType();
        this.balance = account.getBalance();
        this.createDate = account.getCreateDate();
    }
}
