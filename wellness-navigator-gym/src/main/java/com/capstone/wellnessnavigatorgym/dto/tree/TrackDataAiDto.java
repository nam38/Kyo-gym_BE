/*
package com.capstone.wellnessnavigatorgym.dto.tree;

import com.capstone.wellnessnavigatorgym.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackDataAiDto {
    private Integer trackDataAiId;
    private String trainingGoals;
    private String activityLevel;
    private String trainingHistory;
    private Integer age;
    private String gender;
    private Double bmi;
    private String suggestedExercises;
    private Boolean effective;
    private Customer customer;

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
            case "suggested_exercises":
                return getSuggestedExercises();
            case "training_goals":
                return getTrainingGoals();
            case "training_history":
                return getTrainingHistory();
            case "customer_id":
                return getCustomer();
            case "effective":
                return getEffective();
            default:
                System.out.println("Invalid attribute: " + attributeName);
                return null; // Hoặc bạn có thể ném một ngoại lệ
        }
    }
}
*/
