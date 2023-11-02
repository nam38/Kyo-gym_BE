package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dayId;
    private String dayName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutritional_exercise_management_id")
    private NutritionalExerciseManagement nutritionalExerciseManagement;
}
