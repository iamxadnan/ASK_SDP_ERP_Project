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
@Table(name = "course_externals")
public class CourseExternals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relationship with SectionStudent to indicate which student the marks belong to
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @ManyToOne
    @JoinColumn(name = "user_stu_id", nullable = false)
    private Student student;


    // Marks for end-semester theory exam (max 25 marks)
    @Column(name = "endsem_theory", nullable = false)
    private float endsemTheory;  // max 25

    // Marks for end-semester lab exam (max 15 marks)
    @Column(name = "endsem_lab", nullable = false)
    private float endsemLab;  // max 15

    // Max external marks (constant value of 40)
    @Column(name = "max_external_marks", nullable = false)
    private final int maxExternalMarks = 40;
   

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public float getEndsemTheory() {
        return endsemTheory;
    }

    public void setEndsemTheory(float endsemTheory) {
        if (endsemTheory > 25) {
            throw new IllegalArgumentException("End-semester theory marks cannot be more than 25.");
        }
        this.endsemTheory = endsemTheory;
    }

    public float getEndsemLab() {
        return endsemLab;
    }

    public void setEndsemLab(float endsemLab) {
        if (endsemLab > 15) {
            throw new IllegalArgumentException("End-semester lab marks cannot be more than 15.");
        }
        this.endsemLab = endsemLab;
    }

    public int getMaxExternalMarks() {
        return maxExternalMarks;
    }

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}

