package com.Banking.ServiceImpl;

import com.Banking.Dto.Request.LoanPaymentDto;
import com.Banking.Exception.Classes.ResourceNotFoundException;
import com.Banking.Model.Common.ApiResponse;
import com.Banking.Model.Loan.Loan;
import com.Banking.Model.Loan.LoanPayment;
import com.Banking.Repository.LoanPaymentRepository;
import com.Banking.Service.LoanPaymentService;
import com.Banking.Util.LoanPaymentUtil;
import com.Banking.Util.LoanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {

    @Autowired
    private LoanPaymentRepository loanPaymentRepository;

    @Autowired
    private LoanPaymentUtil loanPaymentUtil;

    @Autowired
    private LoanUtil loanUtil;

    @Override
    public ApiResponse payLoan(LoanPaymentDto loanPaymentDto) {
        Loan loan = adjustLoanDetailsAfterPayment(loanPaymentDto);
        LoanPayment loanPayment = new LoanPayment(loanPaymentDto, loan);
        loanPaymentRepository.save(loanPayment);
        return new ApiResponse(true, "Loan Payment details saved Successfully");
    }

    private Loan adjustLoanDetailsAfterPayment(LoanPaymentDto loanPaymentDto) {
        Loan loan  = loanUtil.findById(loanPaymentDto.getLoanId());
        BigDecimal loanRemainingAmount = loan.getLoanAmount().subtract(loan.getAmountPaid());
        checkForCompletelyPaidOrNot(loan, loanPaymentDto, loanRemainingAmount);
        BigDecimal interestAmount =
                (loanRemainingAmount.multiply(BigDecimal.valueOf(loan.getInterestRate()))).divide(BigDecimal.valueOf(100));
        if(loanPaymentDto.getAmountPaid().compareTo(interestAmount)<0)
            throw new ResourceNotFoundException("You Cannot pay lesser than the Interest Amount : INR "+interestAmount);
        BigDecimal principalAmount = (loanPaymentDto.getAmountPaid().subtract(interestAmount));
        BigDecimal remainingAmount = loanRemainingAmount.subtract(principalAmount);
        loanPaymentDto.setPrincipalComponent(principalAmount);
        loanPaymentDto.setInterestComponent(interestAmount);
        loanPaymentDto.setRemainingAmount(remainingAmount);
        loan.setAmountPaid(loan.getAmountPaid().add(principalAmount));
        return loan;
    }

    private void checkForCompletelyPaidOrNot(Loan loan, LoanPaymentDto loanPaymentDto, BigDecimal loanRemainingAmount) {
        int compare = loanRemainingAmount.compareTo(loanPaymentDto.getAmountPaid());
        if(compare<0 || compare==0){
            loanPaymentUtil.deleteByLoanId(loan.getId());
            loanUtil.deleteByLoanNumber(loan.getLoanNumber());
            BigDecimal extraAmountPaid = loanPaymentDto.getAmountPaid().subtract(loanRemainingAmount);
            loan.setAmountPaid(loan.getAmountPaid().add(loanRemainingAmount));
            throw new ResourceNotFoundException("Only INR "+loanRemainingAmount+" " +
                    "to be Paid. Take your INR "+extraAmountPaid+" back. Congratulations your have Completely paid your loan...!");
        }
    }

    @Override
    public LoanPaymentDto getLoanPaymentDetailsById(Long id) {
        return new LoanPaymentDto(loanPaymentUtil.findById(id));
    }

    @Override
    public Page<LoanPaymentDto> getAllLoanPaymentDetails(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<LoanPayment> loanPaymentPage = loanPaymentRepository.findAll(pageable);
        List<LoanPaymentDto> loanPaymentDtoList = loanPaymentPage.stream().map(LoanPaymentDto::new).toList();
        return new PageImpl<>(loanPaymentDtoList, pageable, loanPaymentPage.getTotalElements());
    }

    @Override
    public ApiResponse updateLoanPaymentDetailsById(Long id, LoanPaymentDto loanPaymentDto) {
        LoanPayment loanPayment = loanPaymentUtil.findById(id);
        updateLoanPaymentDetails(loanPayment, loanPaymentDto);
        loanPaymentRepository.save(loanPayment);
        return new ApiResponse(true, "Loan Payment Details Updated Successfully");
    }

    private void updateLoanPaymentDetails(LoanPayment loanPayment, LoanPaymentDto loanPaymentDto) {
        if(loanPaymentDto.getPaymentDate()!=null) loanPayment.setPaymentDate(loanPaymentDto.getPaymentDate());
        if(loanPaymentDto.getAmountPaid()!=null) loanPayment.setAmountPaid(loanPaymentDto.getAmountPaid());
        if(loanPaymentDto.getPrincipalComponent()!=null) loanPayment.setPrincipalComponent(loanPaymentDto.getPrincipalComponent());
        if(loanPaymentDto.getInterestComponent()!=null) loanPayment.setInterestComponent(loanPaymentDto.getInterestComponent());
        if(loanPaymentDto.getLoanId()!=null) {
            Loan loan = loanUtil.findById(loanPaymentDto.getLoanId());
            loanPayment.setLoan(loan);
        }
    }

    @Override
    public ApiResponse deleteLoanPaymentByPaymentDate(String paymentDate) {
        loanPaymentUtil.deleteByPaymentDate(LocalDate.parse(paymentDate));
        return new ApiResponse(true, "Loan Payment Details deleted Successfully");
    }
}
