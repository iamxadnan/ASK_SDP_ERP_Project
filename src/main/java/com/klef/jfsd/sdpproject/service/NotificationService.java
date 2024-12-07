package com.klef.jfsd.sdpproject.service;

import java.util.List;

import com.klef.jfsd.sdpproject.models.Notification;
import com.klef.jfsd.sdpproject.models.Notification.Role1;

public interface NotificationService {
    void sendNotification(Notification notification);
    //public List<Notification> getNotificationsByRole(Role role);
  List<Notification> getNotificationsByRole(Role1 role);
}