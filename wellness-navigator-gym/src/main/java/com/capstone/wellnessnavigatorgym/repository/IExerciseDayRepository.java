package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.ExerciseDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IExerciseDayRepository extends JpaRepository<ExerciseDay, Integer> {

    @Modifying
    @Query(value = "INSERT INTO exercise_day (day_id, exercise_id) VALUES (:dayId, :exerciseId)", nativeQuery = true)
    void addExerciseToDay(@Param("dayId") Integer dayId,
                          @Param("exerciseId") Integer exerciseId);

    @Query(value = "SELECT COUNT(e.exercise_id) " +
            "FROM exercise_day ed " +
            "JOIN exercise e ON ed.exercise_id = e.exercise_id " +
            "WHERE ed.day_id = :dayId AND (e.is_video_finished = false OR e.is_video_finished IS NULL)", nativeQuery = true)
    Integer countUnwatchedVideosForDay(@Param("dayId") Integer dayId);
}
