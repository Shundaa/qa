package com.qa.app.repository;

import com.qa.app.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCoursesByNameContainingIgnoreCase(String name);

    List<Course> findCoursesByOwner(Long owner);

    List<Course> findCoursesByIdAndOwner(Long id, Long owner);
}
