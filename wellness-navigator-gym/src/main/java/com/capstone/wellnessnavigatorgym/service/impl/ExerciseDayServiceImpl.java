package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.Day;
import com.capstone.wellnessnavigatorgym.entity.Exercise;
import com.capstone.wellnessnavigatorgym.repository.IDayRepository;
import com.capstone.wellnessnavigatorgym.repository.IExerciseDayRepository;
import com.capstone.wellnessnavigatorgym.repository.IExerciseRepository;
import com.capstone.wellnessnavigatorgym.service.IExerciseDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseDayServiceImpl implements IExerciseDayService {

    @Autowired
    private IExerciseRepository exerciseRepository;

    @Autowired
    private IDayRepository dayRepository;

    @Autowired
    private IExerciseDayRepository exerciseDayRepository;

    @Override
    public void addExerciseToDay(Integer dayId, Integer exerciseId) {
        if (dayRepository.existsById(dayId) && exerciseRepository.existsById(exerciseId)) {
            Day day = dayRepository.getDayById(dayId);
            Exercise exercise = exerciseRepository.getExerciseById(exerciseId);
            exerciseDayRepository.addExerciseToDay(day.getDayId(), exercise.getExerciseId());
        }
    }
}
