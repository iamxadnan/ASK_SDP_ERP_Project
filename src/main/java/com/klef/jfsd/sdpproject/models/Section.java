package com.klef.jfsd.sdpproject.models;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private int sectionId;
    
    @Column(name="section_no",nullable=false,unique=true)
    private String sectionno;

    @Column(name = "section_capacity", nullable = false)
    private int sectionCapacity = 20;
    
//    @OneToMany(mappedBy = "section")
//    private Set<SectionStudent> sectionStudents;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToMany(mappedBy = "sections")
    private Set<Faculty> faculty;

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	public int getSectionCapacity() {
		return sectionCapacity;
	}

	public void setSectionCapacity(int sectionCapacity) {
		this.sectionCapacity = sectionCapacity;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Set<Faculty> getFaculty() {
		return faculty;
	}

	public void setFaculty(Set<Faculty> faculty) {
		this.faculty = faculty;
	}

//	public Set<SectionStudent> getSectionStudents() {
//		return sectionStudents;
//	}
//
//	public void setSectionStudents(Set<SectionStudent> sectionStudents) {
//		this.sectionStudents = sectionStudents;
//	}

	public String getSectionno() {
		return sectionno;
	}

	public void setSectionno(String sectionno) {
		this.sectionno = sectionno;
	}

	

   
}
