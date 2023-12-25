package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetail;
import com.capstone.wellnessnavigatorgym.entity.CourseDay;

import java.util.List;

public interface ICourseDay {
    List<CourseDetail> findAllCourseDetails();
    List<CourseDay> findAll();
}
