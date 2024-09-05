package com.example.cum.repository;

import com.example.cum.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByName (String name);
}