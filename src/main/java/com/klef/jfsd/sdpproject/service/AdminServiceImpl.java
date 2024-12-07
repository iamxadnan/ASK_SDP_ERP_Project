package com.klef.jfsd.sdpproject.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.CourseFaculty;
import com.klef.jfsd.sdpproject.models.Faculty;
import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.Leave.LeaveStatus;
import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Section;
import com.klef.jfsd.sdpproject.models.Student;
import com.klef.jfsd.sdpproject.models.TicketIssues;
import com.klef.jfsd.sdpproject.models.User;
import com.klef.jfsd.sdpproject.repository.CourseFacultyRepository;
import com.klef.jfsd.sdpproject.repository.FacultyRepository;
import com.klef.jfsd.sdpproject.repository.LeaveRepository;
import com.klef.jfsd.sdpproject.repository.NotificationRepository;
import com.klef.jfsd.sdpproject.repository.SectionRepository;
import com.klef.jfsd.sdpproject.repository.StudentRepository;
import com.klef.jfsd.sdpproject.repository.TicketIssueRepository;
import com.klef.jfsd.sdpproject.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
    

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
	private FacultyRepository facultyRepository;
	@Autowired
	private StudentRepository studentRepository;
    @Autowired
    private CourseFacultyRepository courseFacultyRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private TicketIssueRepository issueRepository;

    @Override
    public String addFaculty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addStudent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String checkLeaveApplication() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String postDuties() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeFaculty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeStudent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> viewregistrationRequests() {
       return userRepository.findAll();
        
    }
    
    @Override
	public List<Faculty> viewallfaculty() {
		return  facultyRepository.findAll();
	}

	@Override
	public List<Student> viewallstudents() {
		return studentRepository.findAll();
	}

	@Override
	public String deletefacultybyEmail(String femail) {
		facultyRepository.deletefacultybyEmail(femail);
		return "Faculty Moved to Old Staff Records Successfully";
	}
	@Override
	public String deletestudentbyEmail(String femail) {
		studentRepository.deletestudentbyEmail(femail);
		return "Student Moved to Old Student Records Successfully";
	}
	
    
    @Override
   public String postNotification(Notification.Role1 role, String message, LocalDateTime scheduledTime) { 
          Notification notification = new Notification();
       notification.setRole(role);
       notification.setMesg(message);
       notification.setScheduledTime(scheduledTime);
       notificationRepository.save(notification);
          
          return "Notification Scheduled Successfully";
      }

    @Override
    public List<Section> findAllSections() {
        // TODO Auto-generated method stub
        return sectionRepository.findAll();
    }

    @Override
    public Section findBySectionNo(String sectionno) {
        return sectionRepository.findBySectionno(sectionno);
    }

    @Override
    public String addCourseFaculty(CourseFaculty cf) {
        courseFacultyRepository.save(cf);
		return "success";
    }

	@Override
	public long countfaculty() {
		return facultyRepository.count();
	}

	@Override
	public long countstudents() {
		return studentRepository.count();
	}

	@Override
	public List<Leave> getleaveRequest() {
		return leaveRepository.findByStatus(LeaveStatus.PENDING);
	}
	@Override
	  public List<TicketIssues> viewallticketissues() {
	    return issueRepository.findAll();
	  }

	  @Override
	  public String deleteticket(int tid) {
	    issueRepository.deleteByTid(tid);
	    return "Deleted Successfully";
	  }
}
