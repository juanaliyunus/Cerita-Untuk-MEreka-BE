package com.example.cum.repository;

import com.example.cum.entity.Donation;
import com.example.cum.entity.Orphanages;
import com.example.cum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, String> {
    Page<Donation> findAllByUser (User user,
                                  Pageable pageable);
    Page<Donation> findAllByOrphanages (Orphanages orphanages,
                                  Pageable pageable);
    Page<Donation> findAllByDonationStatus (String status,
                                  Pageable pageable);
}