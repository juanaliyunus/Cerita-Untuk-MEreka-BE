package com.example.cum.repository;

import com.example.cum.entity.Orphanages;
import com.example.cum.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findByBookNameAndOrphanages(String name, Orphanages orphanages);

    Page<Stock> findAllByOrphanages(Orphanages orphanages, Pageable pageable);
}