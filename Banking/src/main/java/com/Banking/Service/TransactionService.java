package com.Banking.Service;

import com.Banking.Dto.Response.TransactionResponseDto;
import com.Banking.Dto.Request.TransactionDto;
import com.Banking.Model.Common.ApiResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    ApiResponse createTransaction(TransactionDto TransactionDto);

    TransactionResponseDto getTransactionById(Long id);

    Page<TransactionResponseDto> getAllTransaction(int pageNumber, int pageSize);

    ApiResponse updateTransactionById(Long id, TransactionDto TransactionDto);

    ApiResponse deleteTransactionById(Long id);
}
