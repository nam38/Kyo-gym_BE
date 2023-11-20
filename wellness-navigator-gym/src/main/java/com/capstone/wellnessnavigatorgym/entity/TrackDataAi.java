package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class TrackDataAi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trackDataAiId;
    private String trainingGoals;
    private String activityLevel;
    private String trainingHistory;
    private Integer age;
    private String gender;
    private Double bmi;
    private String suggestedExercises;
    private Boolean effective;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
