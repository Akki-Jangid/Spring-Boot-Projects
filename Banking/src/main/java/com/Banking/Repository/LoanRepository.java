package com.Banking.Repository;

import com.Banking.Model.Loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM loan WHERE loan_number=?1", nativeQuery = true)
    Optional<Integer> deleteByLoanNumber(String loanNumber);

    @Transactional
    Optional<Loan> findByAccountId(Long accountId);

    @Query(value = "DELETE FROM loan " +
            "WHERE account_id IN ( " +
            "SELECT account_id FROM loan l " +
            "JOIN account a ON l.account_id = a.user_id " +
            "WHERE a.user_id = ?1 ) ", nativeQuery = true)
    Optional<Long> deleteByAccountUserId(Long userId);
}
