package com.klef.jfsd.sdpproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Notification.Role1;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
   // List<Notification> findByScheduledTimeBeforeAndPostedTimeIsNull(LocalDateTime scheduledTime);
  //@Query("select a from Notification a where a.scheduledTime =?1")
  //public List<Notification> findByScheduledTimeBeforeAndPostedTimeIsNull(LocalDateTime scheduledTime);

   @Query("SELECT a FROM Notification a WHERE a.scheduledTime = ?1")
   public List<Notification> findByScheduledTimeBeforeAndPostedTimeIsNull(LocalDateTime scheduledTime);
   
   //public List<Notification> findByRoleAndPostedTimeIsNotNull(Role1 role);
   
   List<Notification> findByRoleAndPostedTimeIsNotNullAndScheduledTimeBefore(Role1 role, LocalDateTime currentTime);
}