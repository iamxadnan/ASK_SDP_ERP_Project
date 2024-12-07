package com.klef.jfsd.sdpproject.service;

import java.util.List;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.TicketIssues;

public interface FacultyService {
    public String FacultyRegistration(Faculty faculty);
    public String Update(Faculty faculty);
    public String Delete(int id);
    public String riseTicketIssue(TicketIssues ticketIssues);
    public List<TicketIssues> viewRisedTickets(int uid);
    public Faculty getFacultyByEmail(String email); // New method
    public Faculty findByFacultyId(String facultyId);
    public String applyleave(Leave leave);
    public List<Leave> allLeaves(String email);
    public void clearOtp(String email);
    public int getOtpByEmail(String email);
    public void storeOtp(String email, int otp);
}
