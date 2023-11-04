package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.Exercise;
import com.capstone.wellnessnavigatorgym.repository.IExerciseRepository;
import com.capstone.wellnessnavigatorgym.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements IExerciseService {

    @Autowired
    private IExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }
}
