package com.klef.jfsd.sdpproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.*;
@Repository
public interface CourseInternalsRepository extends JpaRepository<CourseInternals, Integer> {
	@Query("select s from CourseInternals s where s.student.studentId=?1 and s.course.courseCode=?2")
	public CourseInternals findByStudentAndCourse(String studentId, String courseCode);
	@Query("select s from CourseInternals s where s.student.studentId=?1")
	public List<CourseInternals> findbyStudentId(String studentId);

}
