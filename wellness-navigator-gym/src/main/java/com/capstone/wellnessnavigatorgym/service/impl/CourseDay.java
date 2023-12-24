package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.service.ICourseDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseDay implements ICourseDay {

    @Autowired
    private com.capstone.wellnessnavigatorgym.repository.ICourseDay courseDay;

    @Override
    public List<com.capstone.wellnessnavigatorgym.entity.CourseDay> findAll() {
        return courseDay.findAll();
    }
}
