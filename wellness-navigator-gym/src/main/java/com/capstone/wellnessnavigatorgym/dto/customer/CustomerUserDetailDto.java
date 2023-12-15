package com.capstone.wellnessnavigatorgym.dto.customer;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDetail;
import com.capstone.wellnessnavigatorgym.utils.ConvertToInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDetailDto {
    Integer customerId;
    String customerCode;
    String customerName;
    String customerPhone;
    Boolean customerGender;
    Date dateOfBirth;
    String idCard;
    String customerAddress;
    String customerImg;
    String customerTypeName;
    String username;
    String accountEmail;
    List<CourseDetail> recommendedCourses;

    public static CustomerUserDetailDto TupleToCustomerDto(List<Tuple> tuples) {
        if (tuples != null && !tuples.isEmpty()) {
            Tuple firstTuple = tuples.get(0);

            CustomerUserDetailDto customerUserDetailDto = new CustomerUserDetailDto(
                    ConvertToInteger.convertToInteger(firstTuple.get("customer_id")),
                    firstTuple.get("customer_code", String.class),
                    firstTuple.get("customer_name", String.class),
                    firstTuple.get("customer_phone", String.class),
                    firstTuple.get("customer_gender", Boolean.class),
                    firstTuple.get("date_of_birth", Date.class),
                    firstTuple.get("id_card", String.class),
                    firstTuple.get("customer_address", String.class),
                    firstTuple.get("customer_img", String.class),
                    firstTuple.get("customer_type_name", String.class),
                    firstTuple.get("user_name", String.class),
                    firstTuple.get("email", String.class),
                    new ArrayList<>()
            );

            List<CourseDetail> recommendedCourses = tuples.stream()
                    .filter(tuple -> {
                        try {
                            return tuple.get("recommend", Boolean.class);
                        } catch (IllegalArgumentException e) {
                            // Handle the case where "recommend" is not found or not a Boolean
                            return false;
                        }
                    })
                    .map(tuple -> new CourseDetail(
                            ConvertToInteger.convertToInteger(tuple.get("course_id")),
                            tuple.get("course_name", String.class),
                            tuple.get("description", String.class),
                            tuple.get("duration", String.class),
                            tuple.get("image", String.class),
                            tuple.get("course_type_name", String.class)
                    ))
                    .collect(Collectors.toList());

            customerUserDetailDto.setRecommendedCourses(recommendedCourses);

            return customerUserDetailDto;
        }

        return null;
    }
}
