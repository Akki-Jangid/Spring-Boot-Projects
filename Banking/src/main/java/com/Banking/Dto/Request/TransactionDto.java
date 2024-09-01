package com.Banking.Dto.Request;

import com.Banking.Model.Account.Account;
import com.Banking.Model.Transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @Positive(message = "Amount should be positive")
    private BigDecimal amount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date timeStamp;

    @NotBlank(message = "Description is mandatory")
    private String description;

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull(message = "Sender's AccountId is mandatory")
//    private Long senderAccountId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Receiver's AccountId is mandatory")
    private Long receiverAccountId;

    public TransactionDto(Transaction transaction){
        this.amount = transaction.getAmount();
        this.timeStamp = new Date();
        this.description = transaction.getDescription();
    }
}
