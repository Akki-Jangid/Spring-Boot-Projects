package com.Banking.Dto.Response;

import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Model.Enum.LoanStatus;
import com.Banking.Model.Enum.LoanType;
import com.Banking.Model.Loan.Loan;
import com.Banking.Model.Loan.LoanPayment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDto {

    private String loanNumber;
    private BigDecimal loanAmount;
    private BigDecimal amountPaid;
    private Double interestRate;
    private Integer tenureInMonths;
    private LocalDate startDate;
    private LocalDate endDate;
    private LoanType loanType;
    private LoanStatus status;
    private List<LoanPaymentDto> loanPaymentDtoList;

    public LoanResponseDto(Loan loan, List<LoanPaymentDto> loanPaymentDtoList){
        this.loanPaymentDtoList = loanPaymentDtoList;
        this.loanNumber = loan.getLoanNumber();
        this.loanAmount = loan.getLoanAmount();
        this.interestRate = loan.getInterestRate();
        this.tenureInMonths = loan.getTenureInMonths();
        this.startDate = loan.getStartDate();
        this.amountPaid = loan.getAmountPaid();
        this.endDate = loan.getEndDate();
        this.loanType = loan.getLoanType();
        this.status = loan.getStatus();
    }
}

