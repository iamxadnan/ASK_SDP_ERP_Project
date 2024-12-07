package com.klef.jfsd.sdpproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Section;
import com.klef.jfsd.sdpproject.models.SectionStudent;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.repository.CourseFacultyRepository;
import com.klef.jfsd.sdpproject.repository.CourseRepository;
import com.klef.jfsd.sdpproject.repository.FacultyRepository;
import com.klef.jfsd.sdpproject.repository.NotificationRepository;
import com.klef.jfsd.sdpproject.repository.SectionRepository;
import com.klef.jfsd.sdpproject.repository.SectionStudentRepository;
import com.klef.jfsd.sdpproject.repository.StudentRepository;
import com.klef.jfsd.sdpproject.repository.TicketIssueRepository;
import com.klef.jfsd.sdpproject.repository.UserRepository;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TicketIssueRepository issueRepository;

     @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
	private FacultyRepository facultyRepository;
    @Autowired
    private CourseFacultyRepository courseFacultyRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private SectionStudentRepository sectionstudentRepository;
    @Override
    public String registerStudent(Student student) {
        studentRepository.save(student);
        return "Student registered successfully!";
    }
    @Override
	public String riseTicketIssue(TicketIssues ticketIssues) {
		issueRepository.save(ticketIssues);
		return "Ticket Rised Successfully";
	}

	@Override
	public List<TicketIssues> viewRisedTickets(int uid) {
		return issueRepository.findRisedTicketsByUser(uid);
	}
    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
        
    }
    @Override
    public String addSectionStudent(String studentId, String facultyId, String sectionNo, String courseCode) {
         Student student = studentRepository.findByStudentId(studentId);
	    Course course = courseRepository.findByCourseCode(courseCode);
	    Section section = sectionRepository.findBySectionno(sectionNo);
	    Faculty faculty=courseFacultyRepository.getfacultybysection(section);
	    
	    // Debugging log
	    System.out.println("Student: " + student);  // Check if student is null
	    System.out.println("Course: " + course);    // Check if course is null
	    System.out.println("Faculty: " + faculty);  // Check if faculty is null
	    System.out.println("Section: " + section);  // Check if section is null

	    // Check if any entity is null
	    if (student == null) {
	        return "Student not found for ID: " + studentId;
	    }
	    if (course == null) {
	        return "Course not found for code: " + courseCode;
	    }
	    if (faculty == null) {
	        return "Faculty not found for ID: " + facultyId;
	    }
	    if (section == null) {
	        return "Section not found for number: " + sectionNo;
	    }

	    // Create a new SectionStudent entity and set its fields
	    SectionStudent secstud = new SectionStudent();
	    secstud.setStudent(student);
	    secstud.setCourse(course);
	    secstud.setFaculty(faculty);
	    secstud.setSection(section);

	    // Check if the student has already taken the course
	    long n = sectionstudentRepository.checkfcoursemapping(student, course, section);
	    long n2=sectionstudentRepository.checkstduentCoursemapping(student, course);
	    System.out.println("Mapping check result: " + n);

	    if (n > 0 || n2 > 0) {
	        return "Student already taken the course";
	    }

	    // Save the new section student record
	    sectionstudentRepository.save(secstud);

	    return "Added successfully";
        
    }

}
