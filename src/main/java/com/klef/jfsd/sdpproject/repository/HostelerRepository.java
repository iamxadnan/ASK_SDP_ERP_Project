package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.klef.jfsd.sdpproject.models.Hosteler;

import java.util.List;

@Repository
public interface HostelerRepository extends JpaRepository<Hosteler, Integer> {
    boolean existsById(int userid);
    
}
