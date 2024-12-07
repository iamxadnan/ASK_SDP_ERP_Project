package com.klef.jfsd.sdpproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.repository.FacultyRepository;
import com.klef.jfsd.sdpproject.repository.LeaveRepository;
import com.klef.jfsd.sdpproject.repository.TicketIssueRepository;
@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private TicketIssueRepository issueRepository;
    @Autowired
    private LeaveRepository leaveRepository;

    private Map<String, Integer> otpStore = new HashMap<>();
    
    @Override
    public String FacultyRegistration(Faculty faculty) {
        facultyRepository.save(faculty);
        return "Faculty registered successfully";
    }

    @Override
    public String Update(Faculty faculty) {
        
       Optional<Faculty> obj=facultyRepository.findById(faculty.getUserid());
       String msg=null;
       if(obj.isPresent()){
        Faculty f=obj.get();
        f.setFirstname(faculty.getFirstname());
        f.setLastname(faculty.getLastname());
        f.setDateOfBirth(faculty.getDateOfBirth());
        f.setGender(faculty.getGender());
        f.setPassword(faculty.getPassword());
        facultyRepository.save(f);
        msg="Faculty updated successfully";
       }
       else{
        msg="Faculty not found";
       }
       return msg;
       
    }

    @Override
    public String Delete(int id) {
        
        return null;
        
    }  
    @Override
	public String riseTicketIssue(TicketIssues ticketIssues) {
		issueRepository.save(ticketIssues);
		return "Ticket Raised Successfully";
	}

	@Override
	public List<TicketIssues> viewRisedTickets(int uid) {
		return issueRepository.findRisedTicketsByUser(uid);
	}

    @Override
    public Faculty getFacultyByEmail(String email) {
        return facultyRepository.findByEmail(email);
    }

    @Override
    public Faculty findByFacultyId(String facultyId) {
        return facultyRepository.findByFacultyId(facultyId);
    }

	@Override
	public String applyleave(Leave leave) {
		leaveRepository.save(leave);
		return "Leave Applied Successfully";
	}

	@Override
	public List<Leave> allLeaves(String email) {
		return leaveRepository.findByFemail(email);
	}
	
	

	@Override
	    public void storeOtp(String email, int otp) {
	        otpStore.put(email, otp);  // Store OTP against the email
	    }

	    @Override
	    public int getOtpByEmail(String email) {
	        return otpStore.getOrDefault(email, -1);  // Return -1 if OTP is not found for email
	    }

	    @Override
	    public void clearOtp(String email) {
	        otpStore.remove(email);  // Remove the OTP from the store after validation
	    }

}
