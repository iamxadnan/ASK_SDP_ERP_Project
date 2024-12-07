package com.klef.jfsd.sdpproject.models; // Adjust the package name as per your structure

import org.hibernate.annotations.GeneratedColumn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EmailRequest {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int eid;
    private String name;
    private String email;
    private String subject;
    private String message;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}