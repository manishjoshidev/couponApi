package com.example.couponapi.service;

import com.example.couponapi.binding.Course;
import java.util.List;

public interface CourseService {
    String upsert(Course course);
    Course getById(Long id);
    List<Course> getAllCourses();
    String deleteById(Long id);

}
