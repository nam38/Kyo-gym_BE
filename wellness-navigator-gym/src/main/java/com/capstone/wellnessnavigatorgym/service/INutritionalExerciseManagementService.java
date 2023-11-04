package com.capstone.wellnessnavigatorgym.service;

import com.capstone.wellnessnavigatorgym.entity.NutritionalExerciseManagement;

import java.util.List;

public interface INutritionalExerciseManagementService {
    List<NutritionalExerciseManagement> findAll();
}
