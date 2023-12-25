package com.capstone.wellnessnavigatorgym.dto.course;

import com.capstone.wellnessnavigatorgym.dto.day.DayDTO;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {
    Integer courseId;
    Boolean recommendedStatus;
    String courseName;
    String description;
    String duration;
    String image;
    Boolean status;
    String courseTypeName;
    private List<DayDTO> days;


    public <X> CourseDetail(Integer courseId, X recommendedStatus, X courseName, X description, X duration, X image, X status, X courseTypeName) {
    }


    public CourseDetail(Integer courseId, String courseName, String description, String duration, String image, Boolean status, List<DayDTO> days, boolean b, String aDefault) {
        this.courseId = courseId;
        this.recommendedStatus = false; // Giả sử giá trị mặc định là false nếu không có thông tin
        this.courseName = courseName;
        this.description = description;
        this.duration = duration;
        this.image = image;
        this.status = status;
        this.courseTypeName = "Default"; // Giả sử giá trị mặc định nếu không có thông tin
        this.days = days;
    }
}
