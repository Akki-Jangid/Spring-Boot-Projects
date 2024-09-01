package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Account.Account;
import com.Banking.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountUtil {

    @Autowired
    private AccountRepository accountRepository;
    
    public Account findById(Long id){
        return accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Account found with this id : "+id));
    }

    public Boolean existsByUserId(Long userId){
        return accountRepository.existsByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("No Account found with this UserId : "+userId));
    }

    public Account findByUserId(Long userId){
        return accountRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("No Account found with this UserId : "+userId));
    }

    public List<Account> findByBranchId(Long branch_id){
        return accountRepository.findByBranchId(branch_id).orElseThrow(
                () -> new ResourceNotFoundException("No Account found with this Branch Id : "+branch_id));
    }

    public void deleteByAccountNumber(String accountNumber){
        accountRepository.deleteByAccountNumber(accountNumber).orElseThrow(
                ()-> new ResourceNotFoundException("No Account found with this Account Number : "+accountNumber));
    }

    public void deleteByUserId(Long userId){
        accountRepository.deleteByUserId(userId).orElseThrow(
                ()-> new ResourceNotFoundException("No Account found with this UserId : "+userId));;
    }
}
