package com.capstone.wellnessnavigatorgym.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dayId;
    private String dayName;

    @JsonBackReference
    @ManyToMany(mappedBy = "days")
    private Set<Course> courses = new LinkedHashSet<>();
}
