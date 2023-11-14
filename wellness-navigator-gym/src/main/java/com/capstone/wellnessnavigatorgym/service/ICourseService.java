package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetailsDto;
import com.capstone.wellnessnavigatorgym.entity.Course;

import java.util.List;

public interface ICourseService {
    List<Course> findAll();

    Course findCourseById(Integer id);

    List<CourseDetailsDto> getCourseDetailsByDayAndCourse(Integer courseId, Integer dayId);
}
