package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.Course;
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer>{
	public Course findByCourseCode(String courseCode);


}
