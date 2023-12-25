package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.CourseDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseDay extends JpaRepository<CourseDay, Integer> {
    @Query("SELECT cd FROM CourseDay cd " +
            "JOIN FETCH cd.course c " +
            "LEFT JOIN FETCH cd.day d")
    List<CourseDay> findAllWithDetails();
}
