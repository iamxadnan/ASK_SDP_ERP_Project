package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.klef.jfsd.sdpproject.models.Student;



@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>
{
	@Modifying
	@Transactional
	@Query("delete from User f where f.email = ?1")
	public int deletestudentbyEmail(String email);
	public Student findByEmail(String email);
	public Student findByStudentId(String studentId);

}