package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "orphanages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orphanages {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String address;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "web_url")
    private String webUrl;
    @Column(name = "status")
//    pending', 'approved', 'rejected
    private String status;

    private String avatar;

    private Long createdAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "orphanages")
    private List<OrphanagesNeed> orphanagesNeeds;
    @OneToMany(mappedBy = "orphanages")
    private List<Donation> donations;
    @OneToMany(mappedBy = "orphanages")
    private List<Stock> stocks;
    @OneToMany(mappedBy = "orphanages")
    private List<Feedback> feedbacks;
}
