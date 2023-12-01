package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
public class TrackDataAi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trackDataAiId;
    private String trainingGoals;
    private String activityLevel;
    private String trainingHistory;
    private Integer age;
    private String gender;
    private Double bmi;
    private Boolean effective;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    public Object getAttributeValue(String attributeName) {
        switch (attributeName) {
            case "track_data_ai_id":
                return getTrackDataAiId();
            case "activity_level":
                return getActivityLevel();
            case "age":
                return getAge();
            case "gender":
                return getGender();
            case "bmi":
                return getBmi();
            case "training_goals":
                return getTrainingGoals();
            case "training_history":
                return getTrainingHistory();
            case "course_id":
                return getCourse();
            case "effective":
                return getEffective();
            default:
                System.out.println("Invalid attribute: " + attributeName);
                return null; // Hoặc bạn có thể ném một ngoại lệ
        }
    }
}
