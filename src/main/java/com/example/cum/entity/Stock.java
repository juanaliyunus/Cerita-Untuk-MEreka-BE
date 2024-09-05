package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "book_name", nullable = false)
    private String bookName;
    private Integer quantity;
    private Long createdAt;
    private Long updatedAt;
    @ManyToOne
    @JoinColumn(name = "orphanages_id")
    private Orphanages orphanages;
}
