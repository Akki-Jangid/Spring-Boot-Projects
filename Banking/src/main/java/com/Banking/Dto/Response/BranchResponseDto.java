package com.Banking.Dto.Response;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.Branch.Branch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponseDto {

    private String branchName;
    private String address;
    private String ifscCode;
    private String phoneNumber;
    private List<AccountDto> accountDtoList;

    public BranchResponseDto(Branch branch, List<AccountDto> accountDtoList){
        this.accountDtoList = accountDtoList;
        this.branchName = branch.getBranchName();
        this.address = branch.getAddress();
        this.ifscCode = branch.getIfscCode();
        this.phoneNumber = branch.getPhoneNumber();
    }
}
