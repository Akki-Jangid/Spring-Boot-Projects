package com.Irctc.Repository;

import com.Irctc.Model.TrainModels.Train;
import com.Irctc.Model.TrainModels.TrainRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    Optional<Boolean> existsByTrainNumber(int trainNumber);

    @Modifying
    @Query(value = "DELETE FROM train t WHERE t.train_number=?1", nativeQuery = true)
    void deleteByTrainNumber(int trainNumber);
}
