package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Nutritional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nutritionalId;
    private String nutritionalName;
    private String nutritionalType;
    private String nutritionalDescription;
    private Double calories;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutritional_exercise_management_id")
    private NutritionalExerciseManagement nutritionalExerciseManagement;
}
