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
@Table(name = "course_internals")
public class CourseInternals {

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

    @Column(name = "assignments", nullable = false)
    private float assignments;  // max 10

    @Column(name = "quizzes", nullable = false)
    private float quizzes;  // max 15

    @Column(name = "attendance", nullable = false)
    private float attendance;  // max 5

    @Column(name = "moocs", nullable = false)
    private float moocs;  // max 5

    @Column(name = "insem_theory", nullable = false)
    private float insemTheory;  // max 15

    @Column(name = "insem_lab", nullable = false)
    private float insemLab;  // max 10

    // Max internal marks (constant value of 60)
    @Column(name = "max_internal_marks", nullable = false)
    private final int maxInternalMarks = 60;
  

    // Getters and Setters for all the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   
    public float getAssignments() {
        return assignments;
    }

    public void setAssignments(float assignments) {
        if (assignments > 10) {
            throw new IllegalArgumentException("Assignments marks cannot be more than 10.");
        }
        this.assignments = assignments;
    }

    public float getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(float quizzes) {
        if (quizzes > 15) {
            throw new IllegalArgumentException("Quizzes marks cannot be more than 15.");
        }
        this.quizzes = quizzes;
    }

    public float getAttendance() {
        return attendance;
    }

    public void setAttendance(float attendance) {
        if (attendance > 5) {
            throw new IllegalArgumentException("Attendance marks cannot be more than 5.");
        }
        this.attendance = attendance;
    }

    public float getMoocs() {
        return moocs;
    }

    public void setMoocs(float moocs) {
        if (moocs > 5) {
            throw new IllegalArgumentException("MOOCs marks cannot be more than 5.");
        }
        this.moocs = moocs;
    }

    public float getInsemTheory() {
        return insemTheory;
    }

    public void setInsemTheory(float insemTheory) {
        if (insemTheory > 15) {
            throw new IllegalArgumentException("In-semester theory marks cannot be more than 15.");
        }
        this.insemTheory = insemTheory;
    }

    public float getInsemLab() {
        return insemLab;
    }

    public void setInsemLab(float insemLab) {
        if (insemLab > 10) {
            throw new IllegalArgumentException("In-semester lab marks cannot be more than 10.");
        }
        this.insemLab = insemLab;
    }

    public int getMaxInternalMarks() {
        return maxInternalMarks;
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
