package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "donations")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "quantity_donated")
    private Integer quantityDonated;
    @Column(name = "donation_status")
    private String donationStatus; //'pending', 'rejected', 'delivered'
    private Long createdAt;
    private Long updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "orphanages_id")
    private Orphanages orphanages;
    @Column(name = "book_name", nullable = false)
    private String bookName;
}
