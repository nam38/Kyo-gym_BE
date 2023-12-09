package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface IExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Query(value = "SELECT * FROM exercise WHERE exercise_id = :id", nativeQuery = true)
    Optional<Exercise> findExerciseById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO exercise (body_part, equipment, exercise_description, exercise_name, instructions, " +
            "is_enable, target, video_url) " +
            "VALUES (:body_part, :equipment, :exercise_description, :exercise_name, :instructions, :is_enable, " +
            ":target, :video_url)", nativeQuery = true)
    void insertExercise(@Param("exercise_name") String exercise_name,
                        @Param("body_part") String body_part,
                        @Param("equipment") String equipment,
                        @Param("video_url") String video_url,
                        @Param("target") String target,
                        @Param("exercise_description") String exercise_description,
                        @Param("instructions") String instructions,
                        @Param("is_enable") Boolean is_enable);

    @Query(value = "SELECT * FROM exercise WHERE exercise_id = ?1", nativeQuery = true)
    Exercise getExerciseById(Integer id);
}
