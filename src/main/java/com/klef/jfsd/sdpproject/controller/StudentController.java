package com.klef.jfsd.sdpproject.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.klef.jfsd.sdpproject.models.Notification.Role1;
import com.klef.jfsd.sdpproject.models.Section;
import com.klef.jfsd.sdpproject.models.SectionStudent;
import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.CourseFaculty;
import com.klef.jfsd.sdpproject.models.CourseInternals;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TimetableEntry;
import com.klef.jfsd.sdpproject.models.User.Role;

import com.klef.jfsd.sdpproject.repository.CourseFacultyRepository;
import com.klef.jfsd.sdpproject.repository.CourseRepository;
import com.klef.jfsd.sdpproject.repository.SectionStudentRepository;
import com.klef.jfsd.sdpproject.repository.*;
import com.klef.jfsd.sdpproject.service.AdminService;
import com.klef.jfsd.sdpproject.service.FacultyService;
import com.klef.jfsd.sdpproject.service.NotificationService;
import com.klef.jfsd.sdpproject.service.StudentService;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CourseFacultyRepository coursefacultyRepository;
    @Autowired
    private CourseInternalsRepository courseInternalsRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SectionStudentRepository sectionStudentRepository;
    @Autowired
    private TimetableRepository timetableRepository;
    


    @PostMapping("/register")
    public String registerStudent(
        @RequestParam("firstname") String firstname,
        @RequestParam("lastname") String lastname,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("gender") String gender,
        @RequestParam("contact") String contact,
        @RequestParam("dateOfBirth") String dateOfBirth,
        @RequestParam("department") String department,
        @RequestParam("role") Role role,
        @RequestParam("image") MultipartFile file,
        @RequestParam("joinyear") int joinyear,
        @RequestParam("parentName") String parentname,
        @RequestParam("regstatus") String regstatus
    ) throws Exception {

        
        byte[] bytes = file.getBytes();
        Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);

       
        Student student = new Student();
        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setEmail(email);
        student.setPassword(password);
        student.setGender(gender);
        student.setContact(contact);
        student.setDateOfBirth(dateOfBirth);
        student.setDepartment(department);
        student.setRole(role);
        student.setImage(imageBlob);
        student.setJoinyear(joinyear);
        student.setParentName(parentname);
        student.setRegstatus(regstatus);
        student.setStudentId(student.generateStudentId(department, joinyear));

        

        
        studentService.registerStudent(student);

        return "Student registered successfully";
    }
@PostMapping("/getnotifications")
   @ResponseBody
   public List<Notification> getStudentNotifications() {
      
       List<Notification> notifications = notificationService.getNotificationsByRole((Role1.STUDENT));

       return notifications;

   }
    @GetMapping("/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@RequestParam String email) throws Exception {
      
        Student student = studentService.getStudentByEmail(email);

        if (student != null && student.getImage() != null) {
            Blob imageBlob = student.getImage();
            InputStream in = imageBlob.getBinaryStream();
            byte[] imageBytes = in.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "image/jpeg"); 

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/viewallcourses") 
@ResponseBody 
public Map<String, Object> viewAllCoursesAssigned(@RequestParam(value = "studentId", required = false) String studentId) { 
    Map<String, Object> response = new HashMap<>(); 
     
    if (studentId != null && !studentId.isEmpty()) { 
        // Fetch assigned courses 
        List<SectionStudent> assignedCourses = sectionStudentRepository.findstudent(studentId); 
        response.put("stucoufaclist", assignedCourses); 
 
        // Fetch internals for the student 
        List<CourseInternals> internalsList = courseInternalsRepository.findbyStudentId(studentId); 
        if (internalsList != null && !internalsList.isEmpty()) { 
            float totalMarks = (float) internalsList.stream() 
                .mapToDouble(internal -> internal.getAssignments() + internal.getQuizzes() + 
                                         internal.getAttendance() + internal.getMoocs() + 
                                         internal.getInsemTheory() + internal.getInsemLab()) 
                .sum(); 
            response.put("internalsList", internalsList); 
            response.put("totalMarks", totalMarks); 
        } 
    } 
    return response; 
}
    @GetMapping("/courses/{studentId}") 
    public List<Course> Setcourses(@PathVariable("studentId") String studentId) { 
     System.out.println(studentId); 
       return sectionStudentRepository.findCoursesByStudentId(studentId); 
    }

    @GetMapping("/studentandsection")
public ResponseEntity<?> getStudentAndSectionData() {
    try {
        List<CourseFaculty> facultyAndSections = coursefacultyRepository.findAll();
        return ResponseEntity.ok(facultyAndSections); // No manual conversion needed
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching data");
    }
}
@PostMapping("/viewtimetable") 
public ResponseEntity<?> viewTimetable(@RequestParam("studentId") String studentId) { 
    try { 
        // Fetch sections the student is enrolled in 
        List<Section> sections = sectionStudentRepository.findSectionByStudent(studentId); 
 
        // Fetch timetable entries for the sections 
        List<TimetableEntry> timetableEntries = timetableRepository.findBySectionIn(sections); 
 
        // Return timetable entries as JSON 
        return ResponseEntity.ok(timetableEntries); 
    } catch (Exception e) { 
        // Handle errors (e.g., student not found, etc.) 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) 
                             .body("An error occurred while fetching the timetable."); 
    } 
}
@PostMapping("/addsectionstudent") 
@ResponseBody 
public ResponseEntity<String> addSectionStudents(@RequestBody Map<String, Object> data) { 
    System.out.println("Received payload: " + data); 
 
    // Extracting studentId and courses from the payload 
    String studentId = (String) data.get("studentId"); 
    List<Map<String, String>> courses = (List<Map<String, String>>) data.get("courses"); 
 
    // Validate the input data 
    if (studentId == null || courses == null ||  courses.isEmpty()) { 
        return ResponseEntity.badRequest().body("Invalid input data"); 
    } 
 
    // List to collect errors for all courses 
    List<String> errors = new ArrayList<>(); 
 
    // Iterate through the courses and process each one 
    for (Map<String, String> course : courses) { 
        String courseCode = course.get("courseCode"); 
        String sectionNo = course.get("sectionNo"); 
        String facultyId = course.get("facultyId"); 
 
        // Validate course data 
        if (courseCode == null || sectionNo == null  || facultyId == null) { 
            errors.add("Invalid course data for course: " + courseCode); 
            continue; // Skip to the next course 
        } 
 
        
        // Call the service to handle the database logic (or do it directly here) 
        String result = studentService.addSectionStudent(studentId, facultyId, sectionNo, courseCode); 
 
        // Handle failure cases 
        if (!"Success".equals(result)) { 
            errors.add("Added Successfully" + courseCode); 
        } 
    } 
 
    // If there are any errors, return them 
    
    // Success response after processing all courses 
    return ResponseEntity.ok("Sections assigned successfully."); 
}
}
