package com.klef.jfsd.sdpproject.models;
import jakarta.persistence.*;

@Entity
@Table(name = "timetable_entries")
public class TimetableEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int entryId;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek; // Example: "Monday", "Tuesday"

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    // Constructors
    public TimetableEntry() {}

    public TimetableEntry(String dayOfWeek, Period period, Classroom room, Course course, Faculty faculty, Section section) {
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.classroom = room;
        this.course = course;
        this.faculty = faculty;
        this.section = section;
    }

    // Getters and Setters
    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    

    public Course getCourse() {
        return course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
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
}
