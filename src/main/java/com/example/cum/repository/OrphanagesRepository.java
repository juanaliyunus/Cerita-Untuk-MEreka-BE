package com.example.cum.repository;

import com.example.cum.entity.Orphanages;
import com.example.cum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrphanagesRepository extends JpaRepository<Orphanages, String> {
    Optional<Orphanages> findByName(String name);
    Optional<Orphanages> findByUser(User user);

    Page<Orphanages> findAllByStatus(String status,Pageable pageable);
}