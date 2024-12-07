package com.klef.jfsd.sdpproject.repository; 
 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
 
import com.klef.jfsd.sdpproject.models.Section; 
import com.klef.jfsd.sdpproject.models.TimetableEntry; 
 
import java.util.List; 
 
@Repository 
public interface TimetableRepository extends JpaRepository<TimetableEntry, Integer>{ 
    List<TimetableEntry> findBySectionIn(List<Section> sections); 
 
}