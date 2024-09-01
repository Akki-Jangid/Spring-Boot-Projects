package com.HospitalApp.Repository;

import com.HospitalApp.Model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Query(value = "SELECT DISTINCT p.* FROM patient p " +
            "JOIN app_user au_p ON p.app_user_id = au_p.id " +
            "JOIN staff s ON p.staff_id = s.id " +
            "JOIN app_user au_s ON s.app_user_id = au_s.id "+
            "WHERE (au_p.username ILIKE %?1% OR au_s.username ILIKE %?1%) " +
            "AND ((?2) IS NULL OR s.id IN (?2)) " +
            "AND ((?3) IS NULL OR p.hospital_id IN (?3))"
            , nativeQuery = true)
    public Page<Patient> findByPatientOrDoctorName( String searchValue, List<Long> doctorId,
                                                    List<Long> hospitalId, Pageable pageable);
}
