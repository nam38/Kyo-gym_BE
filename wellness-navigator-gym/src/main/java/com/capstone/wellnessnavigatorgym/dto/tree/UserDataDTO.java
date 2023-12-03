package com.capstone.wellnessnavigatorgym.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
    private String activityLevel;
    private Integer age;
    private String gender;
    private Double bmi;
    private String trainingGoals;
    private String trainingHistory;

    @Override
    public String toString() {
        return "UserDataDTO{" +
                ", activityLevel='" + activityLevel + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", bmi=" + bmi +
                ", trainingGoals='" + trainingGoals + '\'' +
                ", trainingHistory='" + trainingHistory + '\'' +
                '}';
    }
}
