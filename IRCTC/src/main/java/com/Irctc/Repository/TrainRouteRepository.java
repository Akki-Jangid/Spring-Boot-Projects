package com.Irctc.Repository;

import com.Irctc.Model.TrainModels.TrainRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRouteRepository extends JpaRepository<TrainRoute, Long> {

    @Query(value = "SELECT * FROM train_route " +
            "WHERE (?1 IS NULL  OR ?1='' OR starting_point ILIKE %?1%) " +
            "AND (?2 IS NULL OR destination ILIKE %?2%) " +
            "AND (?3 IS NULL OR train_date = TO_DATE(?3, 'YYYY-MM-DD'))",
            nativeQuery = true)
    Page<TrainRoute> findBySearchAndFilter(String startingPoint, String destination, String date, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM train_route tr WHERE tr.date = ?1", nativeQuery = true)
    void deleteByDate(String date);
}
