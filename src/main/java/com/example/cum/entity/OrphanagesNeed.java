package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orphanages_needs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrphanagesNeed {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "book_name")
    private String bookName;
    private Integer quantity = 0;
    private String status; //Requested Fulfilled Cancelled
    private Integer targetQuantity = 0; //Requested Fulfilled
    @ManyToOne
    @JoinColumn(name = "orphanages_id")
    private Orphanages orphanages;
    private Long createdAt;
    private Long updatedAt;
}