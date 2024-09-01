package com.Banking.Controller;

import com.Banking.Dto.Response.TransactionResponseDto;
import com.Banking.Dto.Request.TransactionDto;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.ServiceImpl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@PreAuthorize("hasRole('USER')")
@Tag(name = "Transaction API", description = "Operations related to Transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> doTransaction(@Valid @RequestBody TransactionDto transactionDto){
        return ResponseEntity.ok(transactionService.createTransaction(transactionDto));
    }

    @GetMapping("/getById")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@RequestParam Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Page<TransactionResponseDto>> getAllTransaction(@RequestParam(defaultValue = "0") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(transactionService.getAllTransaction(pageNumber, pageSize));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateTransactionById(@RequestParam Long id,
                                                        @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.updateTransactionById(id, transactionDto));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse> deleteTransactionByTransactionNumber(@RequestParam Long id) {
        return ResponseEntity.ok(transactionService.deleteTransactionById(id));
    }
}
