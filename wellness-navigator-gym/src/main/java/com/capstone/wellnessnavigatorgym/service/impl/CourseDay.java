package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetail;
import com.capstone.wellnessnavigatorgym.dto.day.DayDTO;
import com.capstone.wellnessnavigatorgym.service.ICourseDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseDay implements ICourseDay {

    @Autowired
    private com.capstone.wellnessnavigatorgym.repository.ICourseDay courseDay;


    @Transactional(readOnly = true)
    public List<CourseDetail> findAllCourseDetails() {
        List<com.capstone.wellnessnavigatorgym.entity.CourseDay> courseDays = courseDay.findAllWithDetails();

        Map<Integer, List<DayDTO>> daysByCourse = new HashMap<>();
        Map<Integer, com.capstone.wellnessnavigatorgym.entity.Course> courses = new HashMap<>();

        for (com.capstone.wellnessnavigatorgym.entity.CourseDay cd : courseDays) {
            Integer courseId = cd.getCourse().getCourseId();
            if (!daysByCourse.containsKey(courseId)) {
                daysByCourse.put(courseId, new ArrayList<>());
            }
            daysByCourse.get(courseId).add(new DayDTO(cd.getDay().getDayId(), cd.getDay().getDayName()));
        }
        return daysByCourse.entrySet().stream()
                .map(entry -> new CourseDetail(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
//        return courses.entrySet().stream()
//                .map(entry -> {
//                    com.capstone.wellnessnavigatorgym.entity.Course course = entry.getValue();
//                    List<DayDTO> dayList = daysByCourse.get(entry.getKey());
//                    return new CourseDetail(
//                            course.getCourseId(),
//                            course.getCourseName(),
//                            course.getDescription(),
//                            course.getDuration(),
//                            course.getImage(),
//                            course.getStatus(),
//                            dayList
//                    );
//                })
//                .collect(Collectors.toList());
    }


    @Override
    public List<com.capstone.wellnessnavigatorgym.entity.CourseDay> findAll() {
        return courseDay.findAll();
    }
}
