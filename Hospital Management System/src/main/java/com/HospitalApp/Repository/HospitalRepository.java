package com.HospitalApp.Repository;

import com.HospitalApp.Model.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query(value = "SELECT * FROM hospital where name ILIKE %?1% OR address ILIKE %?1%",
            countQuery = "SELECT count(*) FROM hospital WHERE name ILIKE %?1% OR address ILIKE %?1%",
            nativeQuery = true)
    public Page<Hospital> findByNameAndAddess(String searchValue, Pageable pageable);

}
