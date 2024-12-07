package com.klef.jfsd.sdpproject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_table")
public class TicketIssues 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tid;
	
	@Column(name = "typeofissue", nullable = false, length = 250)
	private String typeofissue;
	
	@Column(name = "issue_msg", nullable = false)
	private String issuemsg;
	
	@Column(name = "uid",nullable=false)
	private int userid;

	@Override
	public String toString() {
		return "TicketIssues [tid=" + tid + ", typeofissue=" + typeofissue + ", issuemsg=" + issuemsg + ", userid="
				+ userid + "]";
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTypeofissue() {
		return typeofissue;
	}

	public void setTypeofissue(String typeofissue) {
		this.typeofissue = typeofissue;
	}

	public String getIssuemsg() {
		return issuemsg;
	}

	public void setIssuemsg(String issuemsg) {
		this.issuemsg = issuemsg;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
	
	