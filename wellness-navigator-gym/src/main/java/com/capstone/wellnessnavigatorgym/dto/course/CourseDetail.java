package com.capstone.wellnessnavigatorgym.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetail {
    Integer courseId;
    String courseName;
    String description;
    String duration;
    String image;
    String courseTypeName;
}
