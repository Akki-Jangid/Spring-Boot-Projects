package com.Banking.Model.Transaction;

import com.Banking.Dto.Request.TransactionDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.Common.CommonBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends CommonBase {


    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date timeStamp;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Account senderAccount;

    @ManyToOne
    private Account receiverAccount;

    public Transaction(TransactionDto transactionDto, Account senderAccount, Account receiverAccount){
        this.amount = transactionDto.getAmount();
        this.timeStamp = new Date();
        this.description = transactionDto.getDescription();
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
    }
}
