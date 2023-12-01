package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ITrackDataAiRepository extends JpaRepository<TrackDataAi, Integer> {

    @Modifying
    @Query(value = "INSERT INTO track_data_ai (activity_level, age, gender, bmi, training_goals, training_history, " +
            "course_id, effective) " +
            "VALUES (:activity_level, :age, :gender, :bmi, :training_goals, :training_history, :course_id, :effective)",
            nativeQuery = true)
    void insertTrackDataAi(@Param("activity_level") String activity_level,
                           @Param("age") Integer age,
                           @Param("gender") String gender,
                           @Param("bmi") Double bmi,
                           @Param("training_goals") String training_goals,
                           @Param("training_history") String training_history,
                           @Param("course_id") String course_id,
                           @Param("effective") Boolean effective);
}
