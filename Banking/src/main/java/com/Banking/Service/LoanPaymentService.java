package com.Banking.Service;

import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

public interface LoanPaymentService {

    ApiResponse payLoan(LoanPaymentDto loanPaymentDto);

    LoanPaymentDto getLoanPaymentDetailsById(Long id);

    Page<LoanPaymentDto> getAllLoanPaymentDetails(int pageNumber, int pageSize);

    ApiResponse updateLoanPaymentDetailsById(Long id, LoanPaymentDto loanPaymentDto);

    ApiResponse deleteLoanPaymentByPaymentDate(String paymentDate);
}
