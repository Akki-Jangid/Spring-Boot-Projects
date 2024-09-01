package com.Banking.Service;

import com.Banking.Dto.Request.LoanDto;
import com.Banking.Dto.Response.LoanResponseDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

public interface LoanService {

    ApiResponse applyForLoan(LoanDto LoanDto);

    LoanResponseDto getLoanDetailsById(Long id);

    Page<LoanResponseDto> getAllLoanDetailsWithLoanPayments(int pageNumber, int pageSize);

    Page<LoanDto> getAllLoanDetails(int pageNumber, int pageSize);

    ApiResponse updateLoanDetailsById(Long id, LoanDto LoanDto);

    ApiResponse deleteLoanByLoanNumber(String loanNumber);
}
