package com.klef.jfsd.sdpproject.controller;

import java.io.InputStream;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.CourseInternals;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Notification.Role1;
import com.klef.jfsd.sdpproject.models.SectionStudent;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.models.User.Role;
import com.klef.jfsd.sdpproject.repository.CourseInternalsRepository;
import com.klef.jfsd.sdpproject.repository.CourseRepository;
import com.klef.jfsd.sdpproject.repository.SectionStudentRepository;
import com.klef.jfsd.sdpproject.repository.StudentRepository;
import com.klef.jfsd.sdpproject.service.AdminService;
import com.klef.jfsd.sdpproject.service.FacultyService;
import com.klef.jfsd.sdpproject.service.NotificationService;
import com.klef.jfsd.sdpproject.service.StudentService;

import jakarta.servlet.http.HttpSession;


@Controller
@ResponseBody
@RequestMapping("/faculty")
public class FacultyController {
   
    @Autowired
    private NotificationService notificationService;
    @Autowired 
 private AdminService adminService; 
 @Autowired 
 private FacultyService facultyService; 
 @Autowired 
 private StudentService studentService; 
 @Autowired 
 private SectionStudentRepository sectionStudentRepository; 
 @Autowired 
 private CourseInternalsRepository courseInternalsRepository; 
 @Autowired 
 private CourseRepository courseRepository; 
 @Autowired 
 private StudentRepository studentRepository;
    @PostMapping("/register")
    public String registerFaculty(
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
        
        @RequestParam("designation") String designation,
        @RequestParam("regstatus") String regstatus
    ) throws Exception {

     
        byte[] bytes = file.getBytes();
        Blob imageBlob = new javax.sql.rowset.serial.SerialBlob(bytes);

        
        Faculty faculty = new Faculty();
        faculty.setFirstname(firstname);
        faculty.setLastname(lastname);
        faculty.setEmail(email);
        faculty.setPassword(password);
        faculty.setGender(gender);
        faculty.setContact(contact);
        faculty.setDateOfBirth(dateOfBirth);
        faculty.setDepartment(department);
        faculty.setRole(role);
        faculty.setImage(imageBlob);
        faculty.setDesignation(designation);
        faculty.setRegstatus(regstatus);
        

       
        facultyService.FacultyRegistration(faculty);

        return "Faculty registered successfully";
    }
    @PutMapping("/update")
    public String updatefaculty(@RequestBody Faculty fac) {
       return facultyService.Update(fac);
    }
    @GetMapping("/getnotifications")
   public List<Notification> getFacultyNotifications() {
       
       List<Notification> notifications = notificationService.getNotificationsByRole((Role1.FACULTY));

       return notifications;
   }
 

   @GetMapping("/profile-picture")
   public ResponseEntity<byte[]> getProfilePicture(@RequestParam String email) {
       try {
           if (email == null || email.isBlank()) {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
   
           Faculty faculty = facultyService.getFacultyByEmail(email);
   
           if (faculty != null && faculty.getImage() != null) {
               Blob imageBlob = faculty.getImage();
               
               try (InputStream in = imageBlob.getBinaryStream()) {
                   byte[] imageBytes = in.readAllBytes();
                   HttpHeaders headers = new HttpHeaders();
                   headers.setContentType(MediaType.IMAGE_JPEG); 
                   return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
               }
           } else {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }
       } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

      @PostMapping("/applyleave")
   public String applyLeave(@RequestBody Leave leave) {
       return facultyService.applyleave(leave);
   }
   @PostMapping("/viewAssignedCourses")
    public ResponseEntity<?> viewAssignedCourses(@RequestBody Map<String, String> request) {
        String facultyId = request.get("facultyId");
        List<Course> assignedCourses = sectionStudentRepository.findCoursesByFacultyId(facultyId);

        if (assignedCourses.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No courses assigned to this faculty."));
        }
        return ResponseEntity.ok(Map.of("courses", assignedCourses));
    }

    @PostMapping("/viewAssignedStudents")
    public ResponseEntity<?> viewAssignedStudents(@RequestBody Map<String, String> request) {
        String facultyId = request.get("facultyId");
        String courseCode = request.get("courseCode");
        List<SectionStudent> assignedStudents = sectionStudentRepository.findStudentsByFacultyAndCourse(facultyId, courseCode);

        if (assignedStudents.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No students assigned to this course for this faculty."));
        }

        List<Map<String, Object>> studentMarks = new ArrayList<>();
        for (SectionStudent student : assignedStudents) {
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("id", student.getStudent().getStudentId());
            studentData.put("name", student.getStudent().getLastname());

            CourseInternals internals = courseInternalsRepository.findByStudentAndCourse(student.getStudent().getStudentId(), courseCode);
            if (internals != null) {
                studentData.put("assignments", internals.getAssignments());
                studentData.put("quizzes", internals.getQuizzes());
                studentData.put("attendance", internals.getAttendance());
                studentData.put("moocs", internals.getMoocs());
                studentData.put("insemTheory", internals.getInsemTheory());
                studentData.put("insemLab", internals.getInsemLab());
            } else {
                studentData.put("assignments", 0);
                studentData.put("quizzes", 0);
                studentData.put("attendance", 0);
                studentData.put("moocs", 0);
                studentData.put("insemTheory", 0);
                studentData.put("insemLab", 0);
            }

            studentMarks.add(studentData);
        }

        return ResponseEntity.ok(Map.of("students", studentMarks));
    }
    @PostMapping("/assignOrUpdateInternals")
    public ResponseEntity<?> assignOrUpdateInternals(@RequestBody Map<String, Object> requestData) {
        try {
            // Extracting the students array from requestData
            List<Map<String, Object>> students = (List<Map<String, Object>>) requestData.get("students");
            
            if (students == null || students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No student data provided.");
            }

            // Assuming the first student object needs to be processed
            Map<String, Object> studentData = students.get(0);

            // Extract individual fields
            String studentId = (String) studentData.get("studentId");
            String courseCode = (String) studentData.get("courseCode");
            System.out.println("Received Request: " + requestData);
            System.out.println("Processing Student ID: " + studentId);

            // Validate and parse numerical fields
            float assignments = parseFloatSafely(studentData, "assignments", 0.0f);
            float quizzes = parseFloatSafely(studentData, "quizzes", 0.0f);
            float attendance = parseFloatSafely(studentData, "attendance", 0.0f);
            float moocs = parseFloatSafely(studentData, "moocs", 0.0f);
            float insemTheory = parseFloatSafely(studentData, "insemTheory", 0.0f);
            float insemLab = parseFloatSafely(studentData, "insemLab", 0.0f);

            // Fetch the student and course from the database
            Student student = studentRepository.findByStudentId(studentId);
            Course course = courseRepository.findByCourseCode(courseCode);

            if (student == null || course == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid Student ID or Course Code.");
            }

            // Fetch existing CourseInternals for this student and course
            CourseInternals existingInternals = courseInternalsRepository
                    .findByStudentAndCourse(student.getStudentId(), course.getCourseCode());

            if (existingInternals != null) {
                // Update existing records
                updateCourseInternals(existingInternals, assignments, quizzes, attendance, moocs, insemTheory, insemLab);
                courseInternalsRepository.save(existingInternals);
                return ResponseEntity.ok("Internals updated successfully.");
            } else {
                // Create new records if none exist
                CourseInternals newInternals = new CourseInternals();
                newInternals.setStudent(student);
                newInternals.setCourse(course);
                updateCourseInternals(newInternals, assignments, quizzes, attendance, moocs, insemTheory, insemLab);
                courseInternalsRepository.save(newInternals);
                return ResponseEntity.ok("Internals added successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing request: " + e.getMessage());
        }
    }


    // Helper method to parse floating-point values safely
    private float parseFloatSafely(Map<String, Object> data, String key, float defaultValue) {
        try {
            return Float.parseFloat(data.getOrDefault(key, defaultValue).toString());
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    // Helper method to update CourseInternals
    private void updateCourseInternals(CourseInternals internals, float assignments, float quizzes,
                                       float attendance, float moocs, float insemTheory, float insemLab) {
        internals.setAssignments(assignments);
        internals.setQuizzes(quizzes);
        internals.setAttendance(attendance);
        internals.setMoocs(moocs);
        internals.setInsemTheory(insemTheory);
        internals.setInsemLab(insemLab);
    }

    @GetMapping("/ViewAppliedLeaves")
    public List<Leave> appliedleaves(@RequestParam String email)
    {
      return facultyService.allLeaves(email);
    }
    @PostMapping("/riseticket")
    public String riseTicketIssue(@RequestBody TicketIssues ticketIssue)
    {
     return facultyService.riseTicketIssue(ticketIssue);
    }
    
    @GetMapping("/viewtickets")
    public List<TicketIssues> viewalltickets(@RequestParam("uid") int uid)
    {
      return facultyService.viewRisedTickets(uid);
    }
    
 // Endpoint to validate OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        int otp = Integer.parseInt(request.get("otp"));

        // Get the stored OTP from the service
        int storedOtp = facultyService.getOtpByEmail(email);

        // Check if the OTP matches the stored OTP
        if (storedOtp == otp) {
            facultyService.clearOtp(email); // Clear the OTP after successful validation
            return ResponseEntity.ok("OTP validated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }
    }
    @GetMapping("/courses/{facultyId}") 
    public List<Course> Setcourses(@PathVariable("facultyId") String facultyId) { 
     System.out.println(facultyId); 
       return sectionStudentRepository.findCoursesByFacultyId(facultyId); 
    }



        // Endpoint to update profile
        @PutMapping("/update-profile")
        public String updateProfile(
            @RequestBody Faculty faculty,
            HttpSession session
        ) {
      
            // Fetch current faculty details
            String email = faculty.getEmail();
            Optional<Faculty> existingFaculty = Optional.of(facultyService.getFacultyByEmail(email));
            if (existingFaculty.isEmpty()) {
                return "Faculty not found.";
            }

            Faculty currentFaculty = existingFaculty.get();

            // Update only allowed fields
            currentFaculty.setFirstname(faculty.getFirstname());
            currentFaculty.setLastname(faculty.getLastname());
            currentFaculty.setDateOfBirth(faculty.getDateOfBirth());
            currentFaculty.setGender(faculty.getGender());
            currentFaculty.setPassword(faculty.getPassword());

            // Save updated details
            facultyService.Update(currentFaculty);

            // Clear session
            session.removeAttribute("validatedEmail");

            return "Profile updated successfully.";
        }
}


    

