package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Transaction.Transaction;
import com.Banking.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtil {

    @Autowired
    private TransactionRepository transactionRepository;
    
    public Transaction findById(Long id){
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Transaction found with this id : "+id));
    }

    public void deleteByTransactionById(Long id){
        transactionRepository.deleteById(id);
    }
}
