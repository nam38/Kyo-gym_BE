package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.Course;

import java.util.List;

public interface ICourseService {
    List<Course> findAll();
}
