package com.klef.jfsd.sdpproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.User;
import com.klef.jfsd.sdpproject.service.AdminService;
import com.klef.jfsd.sdpproject.service.UserService;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @PostMapping("checkuserlogin")
    @ResponseBody
    public ResponseEntity<Object> checkUserLogin(@RequestParam String email, @RequestParam String password) throws Exception {
        User user = userService.checkuserlogin(email, password);

        if (user != null) {
            if ("approved".equals(user.getRegstatus())) {
                if (user instanceof Student) {
                    processImage((Student) user);
                } else if (user instanceof Faculty) {
                    processImage((Faculty) user);
                }
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(403).body("User not approved by admin");
            }
        } else {
            return ResponseEntity.status(401).body("Login failed");
        }
    }

    @GetMapping("viewallstudentss")
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

    @GetMapping("viewallfacultys")
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
                System.out.println(e);
            }
        }
    }


}
