package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.Section;
@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>{
	public Section findBySectionno(String sectionno);

}
