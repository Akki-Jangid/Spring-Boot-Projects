package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.LoanDto;
import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Dto.Response.LoanResponseDto;
import com.Banking.Model.Account.Account;
import com.Banking.Model.AppUser.AppUser;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Model.Loan.Loan;
import com.Banking.Repository.LoanRepository;
import com.Banking.Service.LoanService;
import com.Banking.Util.AccountUtil;
import com.Banking.Util.AppUserUtil;
import com.Banking.Util.LoanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanUtil loanUtil;

    @Autowired
    private AccountUtil accountUtil;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public ApiResponse applyForLoan(LoanDto loanDto) {
        Account account = accountUtil.findById(loanDto.getAccountId());
        Loan loan = new Loan(loanDto, account);
        loan.setAmountPaid(new BigDecimal(0));
        loanRepository.save(loan);
        return new ApiResponse(true, "Loan Details saved Successfully");
    }

    @Override
    public LoanResponseDto getLoanDetailsById(Long id) {
        Loan loan = loanUtil.findById(id);
        List<LoanPaymentDto> loanPaymentDtoList = findListOfLoanPayments(loan);
        return new LoanResponseDto(loan, loanPaymentDtoList);
    }

    private List<LoanPaymentDto> findListOfLoanPayments(Loan loan) {
        return loan.getLoanPayments().stream()
                .map(LoanPaymentDto::new).toList();
    }

    @Override
    public Page<LoanResponseDto> getAllLoanDetailsWithLoanPayments(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Loan> loanPage = loanRepository.findAll(pageable);
        List<LoanResponseDto> loanResponseDtoList = loanPage.stream()
                .map(loan -> new LoanResponseDto(loan, findListOfLoanPayments(loan))).toList();
        return new PageImpl<>(loanResponseDtoList, pageable, loanPage.getTotalElements());
    }

    @Override
    public Page<LoanDto> getAllLoanDetails(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Loan> loanPage = loanRepository.findAll(pageable);
        List<LoanDto> loanDtoList = loanPage.stream().map(LoanDto::new).toList();
        return new PageImpl<>(loanDtoList, pageable, loanPage.getTotalElements());
    }

    @Override
    public ApiResponse updateLoanDetailsById(Long id, LoanDto loanDto) {
        Loan loan = loanUtil.findById(id);
        updateLoanDetails(loan, loanDto);
        loanRepository.save(loan);
        return new ApiResponse(true, "Loan Details updated Successfully");
    }

    private void updateLoanDetails(Loan loan, LoanDto loanDto) {
        if(!loanDto.getLoanNumber().isEmpty()) loan.setLoanNumber(loanDto.getLoanNumber());
        if(loanDto.getLoanType()!=null) loan.setLoanType(loanDto.getLoanType());
        if(loanDto.getLoanAmount()!=null) loan.setLoanAmount(loanDto.getLoanAmount());
        if(loanDto.getStatus()!=null) loan.setStatus(loanDto.getStatus());
        if(loanDto.getInterestRate()!=null) loan.setInterestRate(loanDto.getInterestRate());
        if(loanDto.getTenureInMonths()!=null) loan.setTenureInMonths(loanDto.getTenureInMonths());
        if(loanDto.getStartDate()!=null) loan.setStartDate(loanDto.getStartDate());
        if(loanDto.getEndDate()!=null) loan.setEndDate(loanDto.getEndDate());
        if(loanDto.getAccountId()!=null) {
            Account account = accountUtil.findById(loanDto.getAccountId());
            loan.setAccount(account);
        }
    }

    @Override
    public ApiResponse deleteLoanByLoanNumber(String loanNumber) {
        loanUtil.deleteByLoanNumber(loanNumber);
        return new ApiResponse(true, "Loan Details Deleted Successfully");
    }
}
