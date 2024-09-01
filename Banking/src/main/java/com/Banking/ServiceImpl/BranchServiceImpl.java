package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.AccountDto;
import com.Banking.Dto.Request.BranchDto;
import com.Banking.Dto.Response.BranchResponseDto;
import com.Banking.Model.Branch.Branch;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Repository.BranchRepository;
import com.Banking.Service.BranchService;
import com.Banking.Util.AccountUtil;
import com.Banking.Util.BranchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchUtil branchUtil;

    @Autowired
    private AccountUtil accountUtil;

    @Override
    public ApiResponse createBranch(BranchDto branchDto) {
        Branch branch = new Branch(branchDto);
        branchRepository.save(branch);
        return new ApiResponse(true, "Branch Created Successfully");
    }

    @Override
    public BranchResponseDto getBranchById(Long id) {
        Branch branch = branchUtil.findById(id);
        List<AccountDto> accountDtoList = findAllAccountsInBranch(id);
        return new BranchResponseDto(branch, accountDtoList);
    }

    private List<AccountDto> findAllAccountsInBranch(Long id) {
        return accountUtil.findByBranchId(id).stream().map(AccountDto::new).toList();
    }

    @Override
    public Page<BranchDto> getAllBranch(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Branch> branchPage = branchRepository.findAll(pageable);
        List<BranchDto> branchDtoList = branchPage.stream().map(BranchDto::new).toList();
        return new PageImpl<>(branchDtoList, pageable, branchPage.getTotalElements());
    }

    @Override
    public Page<BranchResponseDto> getAllBranchWithAccounts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Branch> branchPage = branchRepository.findAll(pageable);
        List<BranchResponseDto> branchResponseDtoList = branchPage.stream()
                .map(branch -> new BranchResponseDto(branch, findAllAccountsInBranch(branch.getId()))).toList();
        return new PageImpl<>(branchResponseDtoList, pageable, branchPage.getTotalElements());
    }

    @Override
    public ApiResponse updateBranchById(Long id, BranchDto branchDto) {
        Branch branch = branchUtil.findById(id);
        updatingBranchDetails(branch, branchDto);
        branchRepository.save(branch);
        return new ApiResponse(true, "Branch Details Updated Successfully");
    }

    private void updatingBranchDetails(Branch branch, BranchDto branchDto) {
        if(!branchDto.getBranchName().isEmpty()) branch.setBranchName(branchDto.getBranchName());
        if(!branchDto.getAddress().isEmpty()) branch.setAddress(branchDto.getAddress());
        if(!branchDto.getIfscCode().isEmpty()) branch.setIfscCode(branchDto.getIfscCode());
        if(!branchDto.getPhoneNumber().isEmpty()) branch.setPhoneNumber(branchDto.getPhoneNumber());
    }

    @Override
    public ApiResponse deleteBranchByIfscCode(String ifsc) {
        branchUtil.deleteByIfscCode(ifsc);
        return new ApiResponse(true, "Branch Deleted Successfully");
    }
}
