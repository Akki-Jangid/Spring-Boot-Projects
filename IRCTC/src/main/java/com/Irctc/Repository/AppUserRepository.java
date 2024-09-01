package com.Irctc.Repository;

import com.Irctc.Model.User.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Boolean existsByEmail(String email);
    Optional<AppUser> findByEmail(String email);

    @Modifying
    @Query(value = "DELETE FROM app_user WHERE email = ?1", nativeQuery = true)
    void deleteByEmail(String someValue);

}
