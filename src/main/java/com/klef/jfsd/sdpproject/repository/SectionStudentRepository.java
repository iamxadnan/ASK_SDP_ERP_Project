package com.klef.jfsd.sdpproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.Section;
import com.klef.jfsd.sdpproject.models.SectionStudent;
import com.klef.jfsd.sdpproject.models.Student;

@Repository
public interface SectionStudentRepository extends JpaRepository<SectionStudent, Integer>{
	@Query("SELECT COUNT(fcm) FROM SectionStudent fcm where fcm.student = ?1 and fcm.course = ?2 AND fcm.section = ?3") 
   public long checkfcoursemapping(Student student,Course course,Section section); 
 @Query("SELECT COUNT(fcm) FROM SectionStudent fcm where fcm.student = ?1 and fcm.course = ?2 ") 
   public long checkstduentCoursemapping(Student student,Course course); 
 @Query("select s from SectionStudent s where s.student.studentId=?1") 
 public List<SectionStudent> findstudent(String studentID); 
 @Query("select s from SectionStudent s where s.faculty.facultyId=?1") 
 public List<SectionStudent> findstudentsbyfaculty(String facultyid); 
 @Query("select s.course from SectionStudent s where s.faculty.facultyId=?1") 
 public List<Course> findCoursesByFacultyId(String facultyId); 
 @Query("select s from SectionStudent s where s.faculty.facultyId=?1 and s.course.courseCode=?2") 
 public List<SectionStudent> findStudentsByFacultyAndCourse(String facultyId, String courseCode); 
 @Query("select s.section from SectionStudent s where s.student.studentId=?1") 
 public List<Section> findSectionByStudent(String studentId);
 @Query("SELECT cf.course FROM SectionStudent cf WHERE cf.student.studentId = :studentId") 
 
 List<Course> findCoursesByStudentId(@Param("studentId")   String studentId);
}
