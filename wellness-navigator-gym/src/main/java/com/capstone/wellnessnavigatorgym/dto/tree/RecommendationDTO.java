package com.capstone.wellnessnavigatorgym.dto.tree;

import com.capstone.wellnessnavigatorgym.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationDTO {
    private List<Course> recommendedCourses;
    private String message;

}
