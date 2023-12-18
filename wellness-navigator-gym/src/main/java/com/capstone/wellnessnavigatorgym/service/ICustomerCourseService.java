package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.CustomerCourse;

import java.util.List;

public interface ICustomerCourseService {

    void save(CustomerCourse customerCourse);

    void saveRecommendedCourses(List<Course> courses, Integer customerId);
}
