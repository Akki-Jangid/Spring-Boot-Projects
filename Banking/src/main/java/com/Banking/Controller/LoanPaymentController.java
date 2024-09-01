package com.Banking.Controller;

import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.LoanPaymentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loanPayment")
@PreAuthorize("hasRole('MANAGER')")
@Tag(name = "Loan Payment API", description = "Operations related to Loan Payments")
public class LoanPaymentController {

    @Autowired
    private LoanPaymentServiceImpl loanPaymentService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addLoanPayment(@Valid @RequestBody LoanPaymentDto LoanPaymentDto){
        return ResponseEntity.ok(loanPaymentService.payLoan(LoanPaymentDto));
    }

    @GetMapping("/getById")
    public ResponseEntity<LoanPaymentDto> getLoanPaymentById(@RequestParam Long id) {
        return ResponseEntity.ok(loanPaymentService.getLoanPaymentDetailsById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<LoanPaymentDto>> getAllLoanPayment(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(loanPaymentService.getAllLoanPaymentDetails(pageNumber, pageSize));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateLoanPaymentById(@RequestParam Long id,
                                                        @RequestBody LoanPaymentDto LoanPaymentDto) {
        return ResponseEntity.ok(loanPaymentService.updateLoanPaymentDetailsById(id, LoanPaymentDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteLoanPaymentByRepaymentDate(@RequestParam String date){
        return ResponseEntity.ok(loanPaymentService.deleteLoanPaymentByPaymentDate(date));
    }
}
