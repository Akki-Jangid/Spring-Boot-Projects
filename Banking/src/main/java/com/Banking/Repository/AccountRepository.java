package com.Banking.Repository;

import com.Banking.Model.Account.Account;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Transactional
    Optional<Integer> deleteByAccountNumber(String accountNumber);


    Optional<List<Account>> findByBranchId(Long branchId);

    @Query(value = "SELECT a.* FROM account a " +
            "JOIN app_user au ON a.user_id = au.id " +
            "WHERE (?1 IS NULL OR au.username ILIKE %?1%) " +
            "AND (?2 IS NULL OR ?2 = '' OR a.create_date =  TO_DATE(?2, 'YYYY-MM-DD'))", nativeQuery = true)
    Page<Account> findBySearchAndFilter(String username, String date, Pageable pageable);

    @Transactional
    Optional<Account> findByUserId(Long userId);

    @Transactional
    Optional<Boolean> existsByUserId(Long userId);

    @Transactional
    Optional<Integer> deleteByUserId(Long userId);
}
