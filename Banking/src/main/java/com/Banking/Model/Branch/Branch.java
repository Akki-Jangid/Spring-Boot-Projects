package com.Banking.Model.Branch;

import com.Banking.Dto.Request.BranchDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.Common.CommonBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends CommonBase {

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private String ifscCode;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Account> accounts;

    public Branch(BranchDto branchDto){
        this.branchName = branchDto.getBranchName();
        this.address = branchDto.getAddress();
        this.ifscCode = branchDto.getIfscCode();
        this.phoneNumber = branchDto.getPhoneNumber();
    }
}
