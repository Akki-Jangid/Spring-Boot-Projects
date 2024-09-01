package com.Banking.Dto.Request;

import com.Banking.Model.Account.Account;
import com.Banking.Model.Branch.Branch;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BranchDto {

    @NotBlank(message = "Branch name is mandatory")
    private String branchName;

    @NotBlank(message = "Branch Address is mandatory")
    private String address;

    @NotBlank(message = "IFSC Code is mandatory")
    @Size(min = 11, max = 11, message = "IFSC Code must have only 11 Characters")
    private String ifscCode;

    @NotBlank(message = "Branch PhoneNumber is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be of 10 digits")
    private String phoneNumber;

    public BranchDto(Branch branch){
        this.branchName = branch.getBranchName();
        this.address = branch.getAddress();
        this.ifscCode = branch.getIfscCode();
        this.phoneNumber = branch.getPhoneNumber();
    }
}
