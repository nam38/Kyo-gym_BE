package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.NutritionalExerciseManagement;
import com.capstone.wellnessnavigatorgym.repository.INutritionalExerciseManagementRepository;
import com.capstone.wellnessnavigatorgym.service.INutritionalExerciseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionalExerciseManagementServiceImpl implements INutritionalExerciseManagementService {

    @Autowired
    private INutritionalExerciseManagementRepository nutritionalExerciseManagementRepository;

    @Override
    public List<NutritionalExerciseManagement> findAll() {
        return nutritionalExerciseManagementRepository.findAll();
    }
}
