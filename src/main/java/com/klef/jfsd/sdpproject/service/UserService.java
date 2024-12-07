package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.User;

public interface UserService {
    public User checkuserlogin(String email,String password);
}
