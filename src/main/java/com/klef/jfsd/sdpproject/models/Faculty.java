package com.klef.jfsd.sdpproject.models;
import java.sql.Blob;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty_table")
@PrimaryKeyJoinColumn(name = "user_fac_id")
@JsonIgnoreProperties({"image"})
public class Faculty extends User {
	
	@Column(name="user_fac_id",insertable=false,updatable = false)
    private int userfacId;
	
	@Column(name="faculty_id",nullable=false,length=20)
	private String facultyId;
    @Column(name = "designation", nullable = false, length = 100)
    private String designation;

    @Column(name = "faculty_salary", nullable = false)
    private double salary;

    
 
      @Column(name="profile_picture",nullable = false)
  		private Blob image; 

	public Blob getImage() {
		return this.image;
	}

	public void setImage(Blob image) {
		this.image = image;
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
        name = "course_faculty",
        joinColumns = @JoinColumn(name = "user_fac_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

    @ManyToMany
    @JoinTable(
        name = "section_faculty",
        joinColumns = @JoinColumn(name = "user_fac_id"),
        inverseJoinColumns = @JoinColumn(name = "section_id")
    )
    private Set<Section> sections;

    

    public String generateFacultyId() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public Faculty() {
        // Custom faculty ID generation logic
        this.setFacultyId(generateFacultyId());  // Override the ID from User
    }

  

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

	public String getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(String facultyId) {
		this.facultyId = facultyId;
	}

	public int getUserfacId() {
		return userfacId;
	}

	public void setUserfacId(int userfacId) {
		this.userfacId = userfacId;
	}



	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}

	
}
