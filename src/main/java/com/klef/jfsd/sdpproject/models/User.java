package com.klef.jfsd.sdpproject.models;
import java.sql.Blob;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User 
{
	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;

	@Column(name = "firstname",length = 50,nullable = false)
	private String firstname;
	
	@Column(name = "laastname",length = 50,nullable = false)
	private String lastname;
	
	@Column(name="email",nullable=false,unique = true,length = 50)
	private String email;
	
	@Column(name="password",nullable=false,length = 50)
	private String password;
	
	@Column(name="contact",nullable=false,unique = true,length = 20)
	private String contact;
	
	@Column(name="gender",nullable=false,length = 20)
	private String gender;
	
	@Column(name="dob",nullable=false,length = 20) 
	private String dateOfBirth;
	
	 @Column(name = "department", nullable = false, length = 100)
	 private String department;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	 private Role role;

	 public enum Role {
	        FACULTY,
	        STUDENT,
			ADMIN
	  }
      @Column(name = "reg_status", nullable = false)
    private String regstatus;

	

	public String getRegstatus() {
		return this.regstatus;
	}

	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}
	 

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
}
