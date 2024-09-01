package com.Banking.Controller;

import com.Banking.Dto.Request.BranchDto;
import com.Banking.Dto.Response.BranchResponseDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.BranchServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
@PreAuthorize("hasRole('MANAGER')")
@Tag(name = "Bank Branch API", description = "Operations related to Bank Branch")
public class BranchController {

    @Autowired
    private BranchServiceImpl branchService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> addBranch(@Valid @RequestBody BranchDto branchDto){
        return ResponseEntity.ok(branchService.createBranch(branchDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<BranchResponseDto> getBranchById(@RequestParam Long id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<BranchDto>> getAllBranch(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(branchService.getAllBranch(pageNumber, pageSize));
    }

    @GetMapping("/getAllWithAccounts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BranchResponseDto>> getAllBranchWithAccounts(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(branchService.getAllBranchWithAccounts(pageNumber, pageSize));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateBranchById(@RequestParam Long id,
                                                        @RequestBody BranchDto branchDto) {
        return ResponseEntity.ok(branchService.updateBranchById(id, branchDto));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteBranchByIfscCode(@RequestParam String code) {
        return ResponseEntity.ok(branchService.deleteBranchByIfscCode(code));
    }
}
