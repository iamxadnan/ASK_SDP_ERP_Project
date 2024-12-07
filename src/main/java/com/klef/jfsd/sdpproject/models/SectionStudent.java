package com.klef.jfsd.sdpproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "section_student")
public class SectionStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_stu_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
    
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    

    @ManyToOne
    @JoinColumn(name = "user_fac_id")
    private Faculty faculty;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

    // Getters and Setters
}
