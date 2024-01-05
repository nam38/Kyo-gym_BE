package com.capstone.wellnessnavigatorgym.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetailsOfExerciseDto {
    private Integer courseId;
    private String courseName;
    private String description;
    private String duration;
    private String image;
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Boolean customerGender;
    private Date dateOfBirth;
    private String idCard;
    private String customerAddress;
    private String customerImg;
    private String username;
    private String email;
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
