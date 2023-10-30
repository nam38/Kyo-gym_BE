package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exerciseId;
    private String exerciseName;
    private String exerciseDescription;
    private Double calorieConsumption;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_type_id")
    private ExerciseType exerciseType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutritional_exercise_management_id")
    private NutritionalExerciseManagement nutritionalExerciseManagement;
}
