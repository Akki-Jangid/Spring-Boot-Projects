package com.Banking.Model.Loan;

import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Model.Common.CommonBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanPayment extends CommonBase {

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private BigDecimal principalComponent;

    @Column(nullable = false)
    private BigDecimal interestComponent;

    @Column(nullable = false)
    private BigDecimal remainingAmount;

    @ManyToOne
    private Loan loan;

    public LoanPayment(LoanPaymentDto loanPaymentDto, Loan loan){
        this.paymentDate = LocalDate.now();
        this.amountPaid = loanPaymentDto.getAmountPaid();
        this.principalComponent = loanPaymentDto.getPrincipalComponent();
        this.interestComponent = loanPaymentDto.getInterestComponent();
        this.remainingAmount = loanPaymentDto.getRemainingAmount();
        this.loan = loan;
    }
}

