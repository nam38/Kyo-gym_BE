package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetailsOfExerciseDto;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourse() {
        List<Course> courseList = this.courseService.findAll();
        if (courseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        return new ResponseEntity<>(courseService.findCourseById(id), HttpStatus.OK);
    }

    @GetMapping("/{courseId}/day/{dayId}")
    public ResponseEntity<List<CourseDetailsOfExerciseDto>> getCourseDetailsByDayAndCourseOfExercise(@PathVariable Integer courseId, @PathVariable Integer dayId) {
        List<CourseDetailsOfExerciseDto> courseDetailsOfExerciseDtoList = this.courseService.getCourseDetailsByDayAndCourseOfExercise(courseId, dayId);
        if (courseDetailsOfExerciseDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseDetailsOfExerciseDtoList, HttpStatus.OK);
    }
}
