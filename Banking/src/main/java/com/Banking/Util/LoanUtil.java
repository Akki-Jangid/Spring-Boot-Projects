package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Loan.Loan;
import com.Banking.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanUtil {

    @Autowired
    private LoanRepository loanRepository;
    
    public Loan findById(Long id){
        return loanRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Loan found with this id : "+id));
    }

    public Loan findByAccountId(Long id){
        return loanRepository.findByAccountId(id).orElseThrow(
                () -> new ResourceNotFoundException("No Loan found with this AccountId : "+id));
    }

    public void deleteByLoanNumber(String loanNumber){
        loanRepository.deleteByLoanNumber(loanNumber).orElseThrow(
                () -> new ResourceNotFoundException("No Loan found with this Loan Number : "+loanNumber));;
    }

    public void deleteByUserId(Long userId){
        loanRepository.deleteByAccountUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("No Loan is associated with this UserId : "+userId));;
    }
}
