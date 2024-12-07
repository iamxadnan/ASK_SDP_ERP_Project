package com.klef.jfsd.sdpproject.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.repository.NotificationRepository;

@Service
public class NotificationScheduler {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;
  
    @Scheduled(fixedRate = 35000) 
    public void sendScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0); // Truncate seconds and milliseconds
        System.out.println("Checking notifications at " + now);

        // Fetch notifications that are due and not yet sent
        List<Notification> dueNotifications = notificationRepository.findByScheduledTimeBeforeAndPostedTimeIsNull(now);
        System.out.println("Found " + dueNotifications.size() + " notifications due for sending");

        for (Notification notification : dueNotifications) {
            // Print the notification message to the console
            notificationService.sendNotification(notification);

            // Format the current time and set it as posted time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            notification.setPostedTime(now.format(formatter)); // Save the formatted time as a String

            // Save the updated notification
            notificationRepository.save(notification);
            //notificationService.getNotificationsByRole(Role1 role);

            System.out.println("Notification sent and updated in database: " + notification.getMesg());
        }
    }

}
