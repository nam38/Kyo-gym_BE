package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.service.IDecisionTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/decision-tree")
@CrossOrigin(origins = "http://localhost:3000")
public class DecisionTreeController {

    @Autowired
    private IDecisionTreeService decisionTreeService;

//    @PostMapping("/recommend-courses")
//    public ResponseEntity<List<Course>> recommendCourses(@Valid @RequestBody UserDataDTO userData) {
//        try {
//            List<Course> recommendedCourses = decisionTreeService.getRecommendation(userData);
//            return new ResponseEntity<>(recommendedCourses, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
