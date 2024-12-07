package com.klef.jfsd.sdpproject.models;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_table")
@PrimaryKeyJoinColumn(name = "user_stu_id")
@JsonIgnoreProperties({"image","sectionStudents", "courses"})
public class Student extends User {

    private static Map<String, String> branchCodeMap = new HashMap<>();
    private static final AtomicInteger sequenceCounter = new AtomicInteger(1); // Sequential ID
    @Column(name="user_stu_id",insertable=false,updatable = false)
    private int userStuId;
  
    @Column(name = "student_id",unique = true)
    private String studentId;

    // Initialize branch codes
    static {
        branchCodeMap.put("CSE", "003");
        branchCodeMap.put("AIDS", "008");
        branchCodeMap.put("CSIT", "009");
        branchCodeMap.put("ECE", "005");
        branchCodeMap.put("CIVIL", "004");
        branchCodeMap.put("EE", "007");
    }

    @Column(name = "batch_id", nullable = false)
    private int batchId;

    @Column(name = "cgpa", nullable = false)
    private double cgpa;

    @Column(name = "fee_status", nullable = false)
    private boolean feeStatus;

    @Column(name = "parent_name", nullable = false, length = 50)
    private String parentName;

    @Column(name="joining_year",nullable=false)
    private int joinyear;

  
      @Column(name="profile_picture",nullable = false)
  		private Blob image; 

	public Blob getImage() {
		return this.image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

    public boolean getFeeStatus() {
        return this.feeStatus;
    }

    
    private String base64Image; 

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    
    @ManyToMany
    @JoinTable(
        name = "enrollment",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;
    @OneToMany(mappedBy = "student")
    private Set<SectionStudent> sectionStudents;



    // Generate the Student ID dynamically using branch and year
    public String generateStudentId(String branch, int joiningYear) {
        String yearPrefix;
        
        // Ensure joiningYear is at least 1000 (4 digits)
        if (String.valueOf(joiningYear).length() >= 4) {
            yearPrefix = String.valueOf(joiningYear).substring(2); // Get last 2 digits of the year
        } else {
            throw new IllegalArgumentException("Joining year must be a 4-digit number");
            

        }
        
        // Ensure the branch exists in the branchCodeMap
        String branchCode = branchCodeMap.get(branch.toUpperCase());
        if (branchCode == null) {
            throw new IllegalArgumentException("Invalid branch: " + branch);
        }
        
        // Generate sequence number
        String sequenceNumber = String.format("%04d", sequenceCounter.getAndIncrement());
        return yearPrefix + branchCode + sequenceNumber; // Return generated Student ID
    }


    // Getters and Setters

   

    public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getJoinyear() {
		return joinyear;
	}

	public void setJoinyear(int joinyear) {
		this.joinyear = joinyear;
	}

	public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public boolean isFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(boolean feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

   

	public int getUserStuId() {
		return userStuId;
	}

	public void setUserStuId(int userStuId) {
		this.userStuId = userStuId;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<SectionStudent> getSectionStudents() {
		return sectionStudents;
	}

	public void setSectionStudents(Set<SectionStudent> sectionStudents) {
		this.sectionStudents = sectionStudents;
	}

	
}
