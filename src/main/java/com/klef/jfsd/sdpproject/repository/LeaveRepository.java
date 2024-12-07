package com.klef.jfsd.sdpproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.klef.jfsd.sdpproject.models.Leave;
import com.klef.jfsd.sdpproject.models.Leave.LeaveStatus;

public interface LeaveRepository extends JpaRepository<Leave, Integer>
{
	public List<Leave> findByFemail(String femail);
	public List<Leave> findByStatus(LeaveStatus status);
}
