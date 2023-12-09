package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class CourseDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseDayId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Day day;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;
}
