package com.Irctc.Repository;

import com.Irctc.Model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(value = "SELECT DISTINCT b.* FROM booking b " +
            "JOIN train_route tr ON b.train_route_id = tr.id " +
            "JOIN train t ON tr.train_id = t.id "+
            "WHERE ?1 IS NULL OR t.id=?1",
            countQuery = "SELECT DISTINCT b.* FROM booking b " +
                    "JOIN train_route tr ON b.train_route_id = tr.id " +
                    "JOIN train t ON tr.train_id = t.id "+
                    "WHERE ?1 IS NULL OR t.id=?1",
            nativeQuery = true
    )
    public Page<Booking> findByTrain(long id, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM booking USING app_user where booking.user_id = app_user.id WHERE app_user.email = ?1", nativeQuery = true)
    void deleteByUserEmail(String email);

}
