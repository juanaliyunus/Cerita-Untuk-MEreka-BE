package com.example.cum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "donors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "avatar")
    private String avatar;
    private String address;
    private Long createdAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "donor")
    private List<Feedback> feedbacks;

    @Override
    public String toString() {
        return "Donor{" +
                "createdAt=" + createdAt +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
