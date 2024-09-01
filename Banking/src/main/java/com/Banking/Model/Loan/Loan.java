package com.Banking.Model.Loan;

import com.Banking.Dto.Request.LoanDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.CommonBase;
import com.Banking.Model.Enum.LoanStatus;
import com.Banking.Model.Enum.LoanType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Loan extends CommonBase {

    @Column(nullable = false, unique = true)
    private String loanNumber;

    @Column(nullable = false)
    private BigDecimal loanAmount;

    @Column(nullable = false)
    private BigDecimal amountPaid;

    @Column(nullable = false)
    private Double interestRate;

    @Column(nullable = false)
    private Integer tenureInMonths;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "loan")
    private List<LoanPayment> loanPayments;

    public Loan(LoanDto loanDto, Account account){
        this.loanNumber = loanDto.getLoanNumber();
        this.loanAmount = loanDto.getLoanAmount();
        this.interestRate = loanDto.getInterestRate();
        this.amountPaid = loanDto.getAmountPaid();
        this.tenureInMonths = loanDto.getTenureInMonths();
        this.startDate = LocalDate.now();
        this.endDate = calculateEndDate(startDate, tenureInMonths);
        this.loanType = loanDto.getLoanType();
        this.status = loanDto.getStatus();
        this.account = account;
    }

    private LocalDate calculateEndDate(LocalDate startDate, Integer tenureInMonths){
        return startDate.plusMonths(tenureInMonths);
    }
}

