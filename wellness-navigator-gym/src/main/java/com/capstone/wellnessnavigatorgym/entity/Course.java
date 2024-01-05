package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;
    @Column(name = "description", length = 2000)
    private String description;
    private String duration;
    @Column(name = "image", length = 2000)
    private String image;
    private Boolean status = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_type_id")
    private CourseType courseType;

    public Course(Integer courseId) {
        this.courseId = courseId;
    }
}


