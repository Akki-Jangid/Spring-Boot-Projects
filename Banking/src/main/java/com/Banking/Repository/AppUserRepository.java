package com.Banking.Repository;

import com.Banking.Model.AppUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    Optional<Boolean> existsByEmail(String email);

    @Transactional
    Optional<Integer> deleteByEmail(String email);
}
