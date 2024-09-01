package com.Banking.Dto.Request;

import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Loan.Loan;
import com.Banking.Model.Loan.LoanPayment;
import com.Banking.Model.Enum.LoanStatus;
import com.Banking.Model.Enum.LoanType;
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
public class LoanDto {

    @NotBlank(message = "LoanNumber is mandatory")
    @Size(min = 5, max = 5, message = "Loan Number Size must be 5 ")
    private String loanNumber;

    @Positive(message = "Loan Amount must be positive")
    private BigDecimal loanAmount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal amountPaid;

    @DecimalMax(value = "50", message = "Maximum Interest rate is 50%")
    private Double interestRate;

    @NotNull(message = "Tenure month cannot be null")
    private Integer tenureInMonths;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate startDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate endDate;

    private LoanType loanType;
    private LoanStatus status;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "AccountId is mandatory")
    private  Long accountId;

    public LoanDto(Loan loan){
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

