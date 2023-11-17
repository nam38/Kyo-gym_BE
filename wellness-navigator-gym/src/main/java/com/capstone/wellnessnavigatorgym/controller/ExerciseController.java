package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.entity.Exercise;
import com.capstone.wellnessnavigatorgym.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise")
@CrossOrigin(origins = "http://localhost:3000")
public class ExerciseController {

    @Autowired
    private IExerciseService exerciseService;

    @GetMapping("")
    public ResponseEntity<List<Exercise>> getAllExercise() {
        List<Exercise> exerciseList = this.exerciseService.findAll();
        if (exerciseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(exerciseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Integer id) {
        return new ResponseEntity<>(exerciseService.findExerciseById(id), HttpStatus.OK);
    }
}
