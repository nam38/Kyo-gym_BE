package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.entity.NutritionalExerciseManagement;
import com.capstone.wellnessnavigatorgym.service.INutritionalExerciseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nutritionalExerciseManagement")
@CrossOrigin(origins = "http://localhost:3000")
public class NutritionalExerciseManagementController {

    @Autowired
    private INutritionalExerciseManagementService nutritionalExerciseManagementService;

    @GetMapping("")
    public ResponseEntity<List<NutritionalExerciseManagement>> getAllNutritionalExerciseManagement() {
        List<NutritionalExerciseManagement> nutritionalExerciseManagementList = this.nutritionalExerciseManagementService.findAll();
        if (nutritionalExerciseManagementList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(nutritionalExerciseManagementList, HttpStatus.OK);
    }
}
