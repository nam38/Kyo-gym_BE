package com.capstone.wellnessnavigatorgym.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsDto {
    private Integer courseId;
    private String courseName;
    private String description;
    private String duration;
    private String image;
    private Integer dayId;
    private String dayName;
    private Integer exerciseId;
    private String bodyPart;
    private String equipment;
    private String exerciseDescription;
    private String exerciseName;
    private String instructions;
    private String target;
    private String videoUrl;
}
