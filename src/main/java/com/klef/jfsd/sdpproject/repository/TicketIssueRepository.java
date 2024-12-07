package com.klef.jfsd.sdpproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.klef.jfsd.sdpproject.models.TicketIssues;

public interface TicketIssueRepository extends JpaRepository<TicketIssues, Integer>
{
    @Query("SELECT t FROM TicketIssues t WHERE t.userid = ?1")
	public List<TicketIssues> findRisedTicketsByUser(int uid);
	
    void deleteByTid(int tid);
}
