package com.Banking.Dto.Response;

import com.Banking.Model.Transaction.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class TransactionResponseDto {

    private BigDecimal amount;
    private Date timeStamp;
    private String description;
    private AccountResponseDto senderAccount;
    private AccountResponseDto receiverAccount;

    public TransactionResponseDto(Transaction transaction){
        this.senderAccount = new AccountResponseDto(transaction.getSenderAccount());
        this.receiverAccount = new AccountResponseDto(transaction.getReceiverAccount());
        this.amount = transaction.getAmount();
        this.timeStamp = transaction.getTimeStamp();
        this.description = transaction.getDescription();
    }
}
