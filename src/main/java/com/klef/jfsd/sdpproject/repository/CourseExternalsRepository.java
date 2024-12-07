package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.*;
@Repository
public interface CourseExternalsRepository extends JpaRepository<CourseExternals, Integer>{

}
