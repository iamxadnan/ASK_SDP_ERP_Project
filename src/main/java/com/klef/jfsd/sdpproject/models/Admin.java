package com.klef.jfsd.sdpproject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_table")
@PrimaryKeyJoinColumn(name = "user_admin_id")  // Foreign key to User table
public class Admin extends User {

    // Admin-specific fields, if any
    @Column(name = "admin_position", nullable = false, length = 50)
    private String adminPosition;


	public String getAdminPosition() {
		return this.adminPosition;
	}

	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}


    
    
}
