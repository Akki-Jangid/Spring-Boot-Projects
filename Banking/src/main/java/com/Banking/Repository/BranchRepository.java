package com.Banking.Repository;

import com.Banking.Model.Branch.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Integer> deleteByIfscCode(String code);
}
