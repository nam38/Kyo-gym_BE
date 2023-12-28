package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.entity.Day;
import com.capstone.wellnessnavigatorgym.error.NotFoundById;
import com.capstone.wellnessnavigatorgym.error.ResourceNotFoundException;
import com.capstone.wellnessnavigatorgym.error.SomeCustomException;
import com.capstone.wellnessnavigatorgym.repository.IDayRepository;
import com.capstone.wellnessnavigatorgym.repository.IExerciseDayRepository;
import com.capstone.wellnessnavigatorgym.service.IDayService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DayServiceImpl implements IDayService {

    @Autowired
    private IDayRepository dayRepository;

    @Autowired
    private IExerciseDayRepository exerciseDayRepository;

    @Override
    public List<Day> findAll() {
        return dayRepository.findAll();
    }

    @SneakyThrows
    @Override
    public Day findDayById(Integer id) {
        Optional<Day> day = dayRepository.findById(id);
        if (day.isPresent()) {
            return day.get();
        }
        throw new NotFoundById("Could not find any days with code: " + id);
    }


/*
    @Override
    public void markDayAsCompleted(Integer dayId) {
        Integer unwatchedVideos = exerciseDayRepository.countUnwatchedVideosForDay(dayId);
        if (unwatchedVideos == 0) {
            Day day = dayRepository.findById(dayId)
                    .orElseThrow(() -> new ResourceNotFoundException("Day not found"));
            day.setIsCompleted(true);
            dayRepository.save(day);
//            scheduleUnlockNextDay(day.getDayId());

        } else {
            throw new SomeCustomException("Not all videos in the day have been watched.");
        }
    }
*/

/*    private void scheduleUnlockNextDay(Integer currentDayId) {
        scheduledExecutorService.schedule(() -> {
            unlockNextDay(currentDayId + 1);
        }, 24, TimeUnit.HOURS);
    }*/

/*    private void unlockNextDay(Integer dayId) {
        Day nextDay = dayRepository.findById(dayId).orElse(null);
        if (nextDay != null && (nextDay.getStatus() == null || !nextDay.getStatus())) {
            nextDay.setStatus(true);
            dayRepository.save(nextDay);
        }
    }*/
}
