package com.example.cum.repository;

import com.example.cum.entity.OrphanagesNeed;
import com.example.cum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrphanagesNeedRepository extends JpaRepository<OrphanagesNeed, String> {

    Page<OrphanagesNeed> findAllByStatus(String requested, Pageable pageable);

    @Transactional
    @Query("SELECT o FROM OrphanagesNeed o WHERE o.status = :status AND o.orphanages.id = :orphanagesId AND o.bookName = :bookName")
    Optional<OrphanagesNeed> getByStatusAndOrphanagesAndBookName(String status, String orphanagesId, String bookName);

    @Transactional
    @Query("SELECT o FROM OrphanagesNeed o WHERE o.status = :status AND o.orphanages.id = :orphanagesId")
    List<OrphanagesNeed> getByStatusAndOrphanages(String status, String orphanagesId);

}