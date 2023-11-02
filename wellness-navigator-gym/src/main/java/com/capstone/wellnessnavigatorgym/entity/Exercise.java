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
    private Double calorieConsumption;
    private String bodyPart;
    private String equipment;
    private String videoUrl;
    private String target;
    private String exerciseDescription;
    private String instructions;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Day day;
}
