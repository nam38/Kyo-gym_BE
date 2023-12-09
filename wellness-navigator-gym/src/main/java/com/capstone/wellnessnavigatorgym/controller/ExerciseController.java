package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.exercise.ExerciseInfo;
import com.capstone.wellnessnavigatorgym.dto.response.MessageResponse;
import com.capstone.wellnessnavigatorgym.entity.Day;
import com.capstone.wellnessnavigatorgym.entity.Exercise;
import com.capstone.wellnessnavigatorgym.service.IDayService;
import com.capstone.wellnessnavigatorgym.service.IExerciseDayService;
import com.capstone.wellnessnavigatorgym.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/exercise")
@CrossOrigin(origins = "http://localhost:3000")
public class ExerciseController {

    @Autowired
    private IExerciseService exerciseService;

    @Autowired
    private IExerciseDayService exerciseDayService;

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

    @PostMapping("/create")
    public ResponseEntity<?> createExercise(@Valid @RequestBody ExerciseInfo exerciseInfo, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        } else {
            exerciseService.saveExercise(exerciseInfo);
            if (exerciseInfo.getDays() != null) {
                exerciseDayService.addExerciseToDay(exerciseInfo.getDays(), exerciseInfo.getExerciseId());
            }
        }
        return new ResponseEntity<>(new MessageResponse("New exercise successfully created!"), HttpStatus.CREATED);
    }


}
