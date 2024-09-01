package com.Banking.Controller;

import com.Banking.Dto.Request.LoanDto;
import com.Banking.Dto.Response.LoanResponseDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.LoanServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
@PreAuthorize("hasRole('MANAGER')")
@Tag(name = "Loan API", description = "Operations related to Loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;

    @PostMapping("/apply")
    public ResponseEntity<ApiResponse> applyForLoan(@Valid @RequestBody LoanDto LoanDto){
        return ResponseEntity.ok(loanService.applyForLoan(LoanDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<LoanResponseDto> getLoanById(@RequestParam Long id) {
        return ResponseEntity.ok(loanService.getLoanDetailsById(id));
    }

    @GetMapping("/getAllWithPaymentDetails")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<LoanResponseDto>> getAllLoanWithPaymentDetails(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(loanService.getAllLoanDetailsWithLoanPayments(pageNumber, pageSize));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<LoanDto>> getAllLoan(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(loanService.getAllLoanDetails(pageNumber, pageSize));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateLoanById(@RequestParam Long id,
                                                        @RequestBody LoanDto LoanDto) {
        return ResponseEntity.ok(loanService.updateLoanDetailsById(id, LoanDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteLoanByLoanNumber(@RequestParam String loanNumber){
        return ResponseEntity.ok(loanService.deleteLoanByLoanNumber(loanNumber));
    }
}
