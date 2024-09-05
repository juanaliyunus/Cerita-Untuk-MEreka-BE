package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedbacks")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "feedback_text",columnDefinition = "TEXT")
    private String feedbackText;

    @Column(name = "created_at")
    private Long createdAt;
    @Column(name = "updated_at")
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;
    @ManyToOne
    @JoinColumn(name = "orphanages_id")
    private Orphanages orphanages;
}
