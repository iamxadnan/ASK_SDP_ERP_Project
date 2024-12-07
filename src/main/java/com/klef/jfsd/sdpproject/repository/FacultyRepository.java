package com.klef.jfsd.sdpproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.jfsd.sdpproject.models.Faculty;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Integer>{
	@Modifying
	@Transactional
	@Query("delete from User f where f.email = ?1")
	public int deletefacultybyEmail(String email);
	public Faculty findByEmail(String email);
	public Faculty findByFacultyId(String facultyId);
	
}
