package com.klef.jfsd.sdpproject.service;

import java.util.List;

import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;

public interface StudentService {
   public String registerStudent(Student student);
  public String riseTicketIssue(TicketIssues ticketIssues);
  public List<TicketIssues> viewRisedTickets(int uid);
   public Student getStudentByEmail(String email); 
   public String addSectionStudent(String studentId, String facultyId, String sectionNo, String courseCode);
}
