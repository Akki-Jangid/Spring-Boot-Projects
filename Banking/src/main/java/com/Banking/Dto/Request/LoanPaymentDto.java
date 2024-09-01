package com.Banking.Dto.Request;

import com.Banking.Model.Loan.Loan;
import com.Banking.Model.Loan.LoanPayment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LoanPaymentDto {

    @Positive(message = "Amount Paid should be positive")
    private BigDecimal amountPaid;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate paymentDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal principalComponent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal interestComponent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal remainingAmount;

    @NotNull(message = "LoanId cannot be null")
    private Long loanId;

    public LoanPaymentDto(LoanPayment loanPayment){
        this.loanId = loanPayment.getLoan().getId();
        this.amountPaid = loanPayment.getAmountPaid();
        this.paymentDate = loanPayment.getPaymentDate();
        this.remainingAmount = loanPayment.getRemainingAmount();
        this.principalComponent = loanPayment.getPrincipalComponent();
        this.interestComponent = loanPayment.getInterestComponent();
    }
}

