package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.CourseFaculty;
import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Section;
import java.util.*;

@Repository
public interface CourseFacultyRepository extends JpaRepository<CourseFaculty, Integer>{
	boolean existsByCourseAndFaculty(Course course, Faculty faculty);
	   @Query("select s.faculty from CourseFaculty s where s.section=?1")
	   public Faculty getfacultybysection(Section section);
	   @Query("SELECT cf.section FROM CourseFaculty cf WHERE cf.course.courseCode = :courseCode") 
     List<Section> findSectionsByCourseCode(@Param("courseCode") String courseCode); 

}
