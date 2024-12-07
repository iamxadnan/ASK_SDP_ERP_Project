package com.klef.jfsd.sdpproject.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_table")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "leave_id")
    private int leaveId;

    @Column(name = "femail", nullable = false)
    private String femail; 

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "leave_type", nullable = false, length = 50)
    private String leaveType;

    @Column(name = "reason", nullable = false, length = 200)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveStatus status = LeaveStatus.PENDING; // Default status is PENDING

    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    public String getFemail() {
		return femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	// Getters and Setters
    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }
}
