package com.capstone.wellnessnavigatorgym.dto.course;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {
    Integer courseId;
    Boolean recommendedStatus;
    String courseName;
    String description;
    String duration;
    String image;
    Boolean status;
    String courseTypeName;
}
