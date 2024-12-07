package com.klef.jfsd.sdpproject.models;
import jakarta.persistence.*;

@Entity
@Table(name = "course_faculty")
//This join table manages the many-to-many relationship between courses and sections.
public class CourseFaculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_faculty_id")
    private int courseFacultyId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_fac_id", nullable = false)
    private Faculty faculty; // Ensure this is set

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

	public int getCourseFacultyId() {
		return courseFacultyId;
	}

	public void setCourseFacultyId(int courseFacultyId) {
		this.courseFacultyId = courseFacultyId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

    // Getters and Setters
}
