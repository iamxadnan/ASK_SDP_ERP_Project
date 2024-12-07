package com.klef.jfsd.sdpproject.controller;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.sdpproject.models.Booking;
import com.klef.jfsd.sdpproject.models.Classroom;
import com.klef.jfsd.sdpproject.models.Course;
import com.klef.jfsd.sdpproject.models.CourseFaculty;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.Leave.LeaveStatus;
import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Period;
import com.klef.jfsd.sdpproject.models.Room;
import com.klef.jfsd.sdpproject.models.Section;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.models.TimetableEntry;
import com.klef.jfsd.sdpproject.models.User;
import com.klef.jfsd.sdpproject.repository.ClassroomRepository;
import com.klef.jfsd.sdpproject.repository.CourseFacultyRepository;
import com.klef.jfsd.sdpproject.repository.CourseRepository;
import com.klef.jfsd.sdpproject.repository.FacultyRepository;
import com.klef.jfsd.sdpproject.repository.LeaveRepository;
import com.klef.jfsd.sdpproject.repository.PeriodRepository;
import com.klef.jfsd.sdpproject.repository.SectionRepository;
import com.klef.jfsd.sdpproject.repository.StudentRepository;
import com.klef.jfsd.sdpproject.repository.TimetableRepository;
import com.klef.jfsd.sdpproject.repository.UserRepository;
import com.klef.jfsd.sdpproject.service.AdminService;
import com.klef.jfsd.sdpproject.service.BookingService;
import com.klef.jfsd.sdpproject.service.FacultyService;
import com.klef.jfsd.sdpproject.service.HostelerService;
import com.klef.jfsd.sdpproject.service.RoomService;

@RestController
@RequestMapping("/admin")
public class AdminController {@Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseFacultyRepository coursefacultyRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private HostelerService hostelerService;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired 
    private ClassroomRepository classroomRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;
    @Autowired 
    private TimetableRepository timetableRepository;
    
    @GetMapping("/getleaverequests")
    public List<Leave> getleaveRequest()
    {
    	return adminService.getleaveRequest();
    }
    
    @GetMapping("/updateLeaveStatus")
    public ResponseEntity<String> updateLeaveStatus(
            @RequestParam int leaveId,
            @RequestParam LeaveStatus status) {

        // Find leave application by ID
        Optional<Leave> optionalLeave = leaveRepository.findById(leaveId);
        
        if (!optionalLeave.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Application not found");
        }

        Leave leave = optionalLeave.get();
        // Update registration status
        leave.setStatus(status);
        leaveRepository.save(leave);

        return ResponseEntity.ok("Leave status updated successfully");
    }
    

    @GetMapping("/getRegistrationRequests")
    public List<User> registrationRequests() {
        return userRepository.findAllByRegstatus("waiting");
    }

    @GetMapping("/updateRegistrationStatus")
    public ResponseEntity<String> updateRegistrationStatus(
            @RequestParam String email, 
            @RequestParam String regstatus) {

        // Find user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Update registration status
        user.setRegstatus(regstatus);
        userRepository.save(user);

        return ResponseEntity.ok("Registration status updated successfully");
    }

    @GetMapping("viewallstudents")
    @ResponseBody
    public ResponseEntity<Object> viewAllStudents(){
        try {
            List<Student> students = adminService.viewallstudents();

            List<Student> processedStudents = students.stream().map(student -> {
                try {
                    processImage(student);
                } catch (Exception e) {
                   
                    e.printStackTrace();
                }
                return student;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(processedStudents);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving students: " + e.getMessage());
        }
    }

    @GetMapping("viewallfaculty")
    @ResponseBody
    public ResponseEntity<Object> viewAllFaculty() {
        try {
            List<Faculty> facultyList = adminService.viewallfaculty();

            List<Faculty> processedFaculty = facultyList.stream().map(faculty -> {
                try {
                    processImage(faculty);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                return faculty;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(processedFaculty);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving faculty: " + e.getMessage());
        }
    }
    @GetMapping("studentcount")
    public long studentcount()
    {
      return adminService.countstudents();
    }
    @GetMapping("facultycount")
    public long facultycount()
    {
      return adminService.countfaculty();
    }

    private void processImage(Student student) throws Exception{
        Blob imageBlob = student.getImage();
        if (imageBlob != null) {
            try (InputStream inputStream = imageBlob.getBinaryStream()) {
                byte[] fileBytes = inputStream.readAllBytes();
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                student.setBase64Image(base64Image);
            } catch (IOException e) {
                // Log error or handle accordingly
            }
        }
    }

    private void processImage(Faculty faculty) throws Exception {
        Blob imageBlob = faculty.getImage();
        if (imageBlob != null) {
            try (InputStream inputStream = imageBlob.getBinaryStream()) {
                byte[] fileBytes = inputStream.readAllBytes();
                String base64Image = Base64.getEncoder().encodeToString(fileBytes);
                faculty.setBase64Image(base64Image);
            } catch (IOException e) {
                // Log error or handle accordingly
            }
        }
    }
	 @GetMapping("deletefaculty")
	 public String deletefacultybyId(@RequestParam("email") String femail)
	 {
		 return adminService.deletefacultybyEmail(femail);
	 }
	 @GetMapping("deletestudent")
	 public String deletestudentbyId(@RequestParam("email") String femail)
	 {
		 return adminService.deletestudentbyEmail(femail);
	 }

    @PostMapping("notification")
   @ResponseBody
   public String postNotification(@RequestBody Notification notification) {
       String result = adminService.postNotification(notification.getRole(), notification.getMesg(), notification.getScheduledTime());
       return result;
   }

    @PostMapping("/addcourses")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        try {
            courseRepository.save(course);
            return ResponseEntity.ok("Course added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/courses")
    public List<Course> getCourses() {
    	 List<Course> courses = courseRepository.findAll(); // Ensure courses is declared here
         return courses; 
           
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
    	 List<Student> courses = studentRepository.findAll();// Ensure courses is declared here
         return courses; 
           
    }
    @GetMapping("/sections")
    public List<Section> getSections(){
    	return adminService.findAllSections();
    }
    @GetMapping("/faculties")
    public List<Faculty> getFaculties(){
    	return facultyRepository.findAll();
    }

    @PostMapping("/addcoursefaculty") 
    public String addCourseFaculty(@RequestBody Map<String, String> data) { 
        String som = ""; 
 
        String courseCode = data.get("courseCode"); 
        String facultyId = data.get("facultyId"); 
        String sectionNo = data.get("sectionNo"); 
 
        System.out.println("Received parameters: courseCode=" + courseCode + ", facultyId=" + facultyId + ", sectionNo=" + sectionNo); 
 
        if (courseCode == null||  facultyId == null || sectionNo == null) { 
            return "Error: Missing parameters"; 
        } 
 
        Course c = courseRepository.findByCourseCode(courseCode); 
        Faculty f = facultyService.findByFacultyId(facultyId); 
        Section s = adminService.findBySectionNo(sectionNo); 
 
        if (c == null || f == null || s == null) { 
            return "Error: Invalid course, faculty, or section"; 
        } 
 
        boolean exists = coursefacultyRepository.existsByCourseAndFaculty(c, f); 
        if (!exists) { 
            CourseFaculty cf = new CourseFaculty(); 
            cf.setFaculty(f); 
            cf.setCourse(c); 
            cf.setSection(s); 
            adminService.addCourseFaculty(cf); 
 
            som = "Added successfully"; 
        } else { 
            som = "Add Failed: Faculty already assigned to course"; 
        } 
 
        return som; 
    }

    @PostMapping("/addsections")
    public ResponseEntity<?> addSection(@RequestBody Map<String, Object> payload) {
        try {
            // Retrieve parameters from the payload
            String sectionNo = (String) payload.get("sectionNo");
            String courseCode = (String) payload.get("courseCode");
            int sectionCapacity = (int) payload.get("sectionCapacity");

            // Validate input fields
            if (sectionNo == null || sectionNo.isEmpty() || 
                courseCode == null || courseCode.isEmpty() || 
                sectionCapacity <= 0) {
                return ResponseEntity.badRequest().body("Invalid input data.");
            }

            // Fetch the Course from the database
            Course course = courseRepository.findByCourseCode(courseCode);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }

            // Create and save the Section
            Section section = new Section();
            section.setSectionno(sectionNo);
            section.setCourse(course);
            section.setSectionCapacity(sectionCapacity);
            sectionRepository.save(section);

            return ResponseEntity.status(HttpStatus.CREATED).body("Section added successfully.");
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().body("Invalid section capacity.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
@GetMapping("/days-of-week") 
    public List<String> getDaysOfWeek() { 
        return List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"); 
    } 
 
    @GetMapping("/periods") 
    public List<Period> getPeriods() { 
        return periodRepository.findAll(); 
    } 
 
    @GetMapping("/rooms") 
    public List<Classroom> getRooms() { 
        return classroomRepository.findAll(); 
    } 
 
     
 
    @GetMapping("/sectionfaculty") 
    public List<CourseFaculty> getSectionfaculty() { 
        return coursefacultyRepository.findAll(); 
    } 
 
    @PostMapping("/savetimetable") 
    public ResponseEntity<?> saveTimetableEntry(@RequestBody Map<String, Object> payload) { 
        try { 
            String dayOfWeek = (String) payload.get("dayOfWeek"); 
            int periodId = (Integer) payload.get("periodId"); 
            int roomId = (Integer) payload.get("roomId"); 
            int courseId = (Integer) payload.get("courseId"); 
 
            // Parsing sectionFaculty 
            String sectionFaculty = (String) payload.get("sectionFaculty"); 
            String[] parts = sectionFaculty.split(","); 
            int sectionId = Integer.parseInt(parts[0]); 
            String facultyId = parts[1]; 
 
            // Create objects based on IDs 
            Period period = periodRepository.findById(periodId).orElseThrow(() -> new RuntimeException("Invalid Period ID")); 
            Classroom room = classroomRepository.findById(roomId).get(); 
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Invalid Course ID")); 
            Faculty faculty = facultyRepository.findByFacultyId(facultyId); 
            Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("Invalid Section ID")); 
 
            // Create and save the TimetableEntry 
            TimetableEntry timetableEntry = new TimetableEntry(dayOfWeek, period, room, course, faculty, section); 
            timetableRepository.save(timetableEntry); 
 
            return ResponseEntity.ok("Timetable entry saved successfully"); 
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage()); 
        } 
    }

     // Get all hostelers
   

    // Get all bookings
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get bookings by status
    @GetMapping("/bookings/status/{status}")
    public List<Booking> getBookingsByStatus(@PathVariable String status) {
        return bookingService.getBookingsByStatus(status);
    }

    // Get all available rooms
    @GetMapping("/rooms/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    // Add a new room
    @PostMapping("/addroom")
    public Room addRoom(@RequestBody Room room) {
        return roomService.createRoom(room);
    }

    // Update room details
    @PutMapping("/rooms/{id}")
    public Room updateRoom(@PathVariable int id, @RequestBody Room room) {
        return roomService.updateRoomById(id, room);
    }

    // Delete a room by ID
    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable int id) {
        roomService.deleteRoom(id);
    }

@GetMapping("/allcourses") 
    public ResponseEntity<List<Course>> getAllCourses() { 
        List<Course> courses = courseRepository.findAll(); 
        return ResponseEntity.ok(courses); 
    } 
 
    @GetMapping("/course/{courseCode}/sections") 
    public ResponseEntity<List<Section>> getSectionsByCourse(@PathVariable String courseCode) { 
        List<Section> sections = coursefacultyRepository.findSectionsByCourseCode(courseCode); 
        return ResponseEntity.ok(sections); 
    }

    // Approve or reject a booking
    @PutMapping("/bookings/{id}/status")
    public Booking updateBookingStatus(@PathVariable int id, @RequestParam String status) {
        return bookingService.updateBookingStatus(id, status);
    }
    
    @GetMapping("/viewalltickets")
    public List<TicketIssues> viewalltickets()
    {
      return adminService.viewallticketissues();
    }
    @DeleteMapping("/deleteticket/{tid}")
    public String deleteticketbyid(@PathVariable("tid") int tid) {
      return adminService.deleteticket(tid);
    }

}