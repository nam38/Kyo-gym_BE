package com.capstone.wellnessnavigatorgym.service.impl;

import com.capstone.wellnessnavigatorgym.dto.course.CourseDayDetailDto;
import com.capstone.wellnessnavigatorgym.dto.course.CourseTypeDto;
import com.capstone.wellnessnavigatorgym.dto.day.DayDto;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.CourseDays;
import com.capstone.wellnessnavigatorgym.entity.CourseType;
import com.capstone.wellnessnavigatorgym.repository.ICourseDayRepository;
import com.capstone.wellnessnavigatorgym.service.ICourseDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseDayServiceImpl implements ICourseDayService {

    @Autowired
    private ICourseDayRepository courseDayRepository;

    @Override
    public List<CourseDays> findAll() {
        return courseDayRepository.findAll();
    }

    @Override
    public List<CourseDayDetailDto> findAllCoursesWithDays() {
        List<CourseDays> courseDaysList = courseDayRepository.findAll();
        return courseDaysList.stream()
                .collect(Collectors.groupingBy(CourseDays::getCourse))
                .entrySet().stream()
                .map(entry -> convertCourseToDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private CourseDayDetailDto convertCourseToDTO(Course course, List<CourseDays> courseDays) {
        CourseDayDetailDto courseDayDetailDto = new CourseDayDetailDto();
        courseDayDetailDto.setCourseId(course.getCourseId());
        courseDayDetailDto.setCourseName(course.getCourseName());
        courseDayDetailDto.setDescription(course.getDescription());
        courseDayDetailDto.setDuration(course.getDuration());
        courseDayDetailDto.setImage(course.getImage());
        courseDayDetailDto.setStatus(course.getStatus());
        courseDayDetailDto.setCourseType(convertCourseTypeToDTO(course.getCourseType()));
        courseDayDetailDto.setDays(courseDays.stream()
                .map(this::convertDayToDTO)
                .collect(Collectors.toList()));
        return courseDayDetailDto;
    }

    private CourseTypeDto convertCourseTypeToDTO(CourseType courseType) {
        CourseTypeDto courseTypeDto = new CourseTypeDto();
        courseTypeDto.setCourseTypeId(courseType.getCourseTypeId());
        courseTypeDto.setCourseTypeName(courseType.getCourseTypeName());
        return courseTypeDto;
    }

    private DayDto convertDayToDTO(CourseDays courseDay) {
        DayDto dayDto = new DayDto();
        dayDto.setDayId(courseDay.getDay().getDayId());
        dayDto.setDayName(courseDay.getDay().getDayName());
        return dayDto;
    }
}
