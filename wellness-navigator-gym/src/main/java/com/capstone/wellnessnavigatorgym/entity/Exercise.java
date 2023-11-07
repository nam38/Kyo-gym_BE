package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exerciseId;
    private String exerciseName;
    private String bodyPart;
    private String equipment;
    private String videoUrl;
    private String target;
    @Column(name = "exercise_description", length = 2000)
    private String exerciseDescription;
    @Column(name = "instructions", length = 2000)
    private String instructions;

    @ManyToMany
    @JoinTable(name = "exercise_days",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id"))
    private Set<Day> days = new LinkedHashSet<>();
}
