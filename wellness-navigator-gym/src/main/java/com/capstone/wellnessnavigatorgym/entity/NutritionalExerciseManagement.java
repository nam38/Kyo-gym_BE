package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class NutritionalExerciseManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nutritionalExerciseManagementId;
    private String nutritionalExerciseManagementName;
    private String description;
    private String duration;
    private String image;
    private String nutritionalExerciseManagementType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
