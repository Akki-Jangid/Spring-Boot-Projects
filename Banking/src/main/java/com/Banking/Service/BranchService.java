package com.Banking.Service;

import com.Banking.Dto.Request.BranchDto;
import com.Banking.Dto.Response.BranchResponseDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

public interface BranchService {

    ApiResponse createBranch(BranchDto branchDto);

    BranchResponseDto getBranchById(Long id);

    Page<BranchDto> getAllBranch(int pageNumber, int pageSize);

    Page<BranchResponseDto> getAllBranchWithAccounts(int pageNumber, int pageSize);

    ApiResponse updateBranchById(Long id, BranchDto branchDto);

    ApiResponse deleteBranchByIfscCode(String ifsc);
}
