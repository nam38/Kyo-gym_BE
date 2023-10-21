package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IExerciseRepository extends JpaRepository<Exercise, Integer> {
}
