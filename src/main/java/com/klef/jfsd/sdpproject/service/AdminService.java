package com.klef.jfsd.sdpproject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.klef.jfsd.sdpproject.models.User;
import com.klef.jfsd.sdpproject.models.CourseFaculty;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Section;

public interface AdminService {

    

    // Operations related to faculty handled by admin
    public String addFaculty();
    public String removeFaculty();
    public String checkLeaveApplication();
    public String postDuties();
    
    public List<Faculty> viewallfaculty();
    public long countfaculty();
  public String deletefacultybyEmail(String femail);
  public List<Leave> getleaveRequest();

    // operations related to students handled by admin
    public String addStudent();
    public String removeStudent();
  public String deletestudentbyEmail(String semail);
    public List<Student> viewallstudents();
    public long countstudents();

    
 
    
    // operations related to college handled by admin
   // public String postNews();
    public List<User> viewregistrationRequests();
    public String postNotification(Notification.Role1 role, String message, LocalDateTime scheduledTime);

     public List<Section> findAllSections();
    public Section findBySectionNo(String sectionno);
    
    public List<TicketIssues> viewallticketissues();
    public String deleteticket(int tid);
    
    //operations on courseFaculty
    public String addCourseFaculty(CourseFaculty cf);
    
    

}