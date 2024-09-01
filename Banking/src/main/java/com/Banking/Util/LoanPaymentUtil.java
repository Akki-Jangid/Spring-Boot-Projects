package com.Banking.Util;

import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Loan.LoanPayment;
import com.Banking.Repository.LoanPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class LoanPaymentUtil {

    @Autowired
    private LoanPaymentRepository loanPaymentRepository;

    public LoanPayment findById(Long id){
        return loanPaymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No Loan Payment found with this id : "+id));
    }

    public void deleteByPaymentDate(LocalDate paymentDate){
        loanPaymentRepository.deleteByPaymentDate(paymentDate).orElseThrow(
                () -> new ResourceNotFoundException("No Loan Payment found with this Payment Date : "+paymentDate));;
    }

    public void deleteByLoanId(Long id){
        loanPaymentRepository.deleteByLoanId(id).orElseThrow(
                () -> new ResourceNotFoundException("No Loan Payment found with this LoanId : "+id));;
    }

    public void deleteByAccountUserId(Long userId){
        loanPaymentRepository.deleteByLoanAccountUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("No Loan Payment found with this UserId : "+userId));;
    }
}
