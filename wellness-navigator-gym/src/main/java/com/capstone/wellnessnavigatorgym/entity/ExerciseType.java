package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exerciseTypeId;
    private String exerciseTypeName;
    private String bodyPart;
    private String equipment;
    private String target;
    private String secondaryMuscles;
    private String instructions;
    private String exerciseTypeImg;
}
