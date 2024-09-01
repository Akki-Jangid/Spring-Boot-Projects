package com.Banking.Repository;

import com.Banking.Model.Loan.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {


    Optional<Integer> deleteByPaymentDate(LocalDate paymentDate);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM loan_payment WHERE loan_id=?1", nativeQuery = true)
    Optional<Integer> deleteByLoanId(Long loan_id);

    @Query(value = "DELETE FROM loan_payment " +
            "WHERE loan_id IN ( " +
            "SELECT loan_id FROM loan_payment lp " +
            "JOIN loan l ON lp.loan_id = l.id " +
            "JOIN account a ON l.account_id = a.user_id " +
            "WHERE a.user_id = ?1 ) ", nativeQuery = true)
    Optional<Long> deleteByLoanAccountUserId(Long userId);
}
