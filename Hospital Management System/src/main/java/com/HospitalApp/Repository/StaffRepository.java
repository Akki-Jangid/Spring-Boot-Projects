package com.HospitalApp.Repository;

import com.HospitalApp.Model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>{

    @Query(value = "SELECT DISTINCT s.* FROM staff s " +
            "JOIN app_user au ON s.app_user_id=au.id "+
            "JOIN hospital h ON s.hospital_id = h.id " +
            "WHERE (?1 IS NULL OR h.name ILIKE %?1% OR au.username ILIKE %?1%) " +
            "AND ((?2) IS NULL OR s.hospital_id IN (?2)) " +
            "AND (?3 IS NULL OR au.role IN (?3))",
            nativeQuery = true)
    public Page<Staff> findByHospitalNameOrStaffNameAndFilter(String searchValue, List<Long> hospitalIds,
                                                              List<String> staffRole, Pageable pageable);

}
