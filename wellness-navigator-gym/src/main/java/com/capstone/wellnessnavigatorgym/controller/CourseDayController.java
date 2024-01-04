package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDayDetailDto;
import com.capstone.wellnessnavigatorgym.entity.CourseDays;
import com.capstone.wellnessnavigatorgym.service.ICourseDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-day")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseDayController {

    @Autowired
    private ICourseDayService courseDayService;

    @GetMapping("")
    public ResponseEntity<List<CourseDays>> getAllCourseDays() {
        List<CourseDays> courseDaysList = this.courseDayService.findAll();
        if (courseDaysList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseDaysList, HttpStatus.OK);
    }

    @GetMapping("/courses-with-days")
    public ResponseEntity<List<CourseDayDetailDto>> getAllCoursesWithDays() {
        List<CourseDayDetailDto> coursesWithDays = courseDayService.findAllCoursesWithDays();

        if (coursesWithDays.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(coursesWithDays, HttpStatus.OK);
    }

}
