package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
