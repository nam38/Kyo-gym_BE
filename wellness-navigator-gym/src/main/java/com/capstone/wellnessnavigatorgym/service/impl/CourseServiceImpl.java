package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetailsDto;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.error.NotFoundById;
import com.capstone.wellnessnavigatorgym.repository.ICourseRepository;
import com.capstone.wellnessnavigatorgym.service.ICourseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Course findCourseById(Integer id) {
        Optional<Course> course = courseRepository.findCourseById(id);
        if (course.isPresent()) {
            return course.get();
        }
        throw new NotFoundById("Could not find any courses with code: " + id);
    }

    @Override
    public List<CourseDetailsDto> getCourseDetailsByDayAndCourse(Integer courseId, Integer dayId) {
        List<Tuple> tupleList = courseRepository.getCourseDetailsByDayAndCourse(courseId, dayId);
        return tupleList.stream()
                .map(this::tupleToCourseDetailsDto)
                .collect(Collectors.toList());
    }

    public CourseDetailsDto tupleToCourseDetailsDto(Tuple tuple) {
        return new CourseDetailsDto(
                convertToInteger(tuple.get("course_id")),
                tuple.get("course_name", String.class),
                tuple.get("description", String.class),
                tuple.get("duration", String.class),
                tuple.get("image", String.class),
                convertToInteger(tuple.get("day_id")),
                tuple.get("day_name", String.class),
                convertToInteger(tuple.get("exercise_id")),
                tuple.get("body_part", String.class),
                tuple.get("equipment", String.class),
                tuple.get("exercise_description", String.class),
                tuple.get("exercise_name", String.class),
                tuple.get("instructions", String.class),
                tuple.get("target", String.class),
                tuple.get("video_url", String.class)
        );
    }

    private Integer convertToInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                // Handle the case where the conversion fails
                return null; // or throw an exception, depending on your requirements
            }
        } else {
            return null; // or handle other data types as needed
        }
    }
}
