package com.example.cum.repository;

import com.example.cum.entity.Donor;
import com.example.cum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, String> {
    Optional<Donor> findByUser(User user);
    Optional<Donor> findByEmail(String email);
//    Page<Donor> findByFullName(Pageable pageable);
@Modifying
@Transactional
@Query("DELETE FROM User u WHERE u.id = :id")
void deleteById(String id);
}