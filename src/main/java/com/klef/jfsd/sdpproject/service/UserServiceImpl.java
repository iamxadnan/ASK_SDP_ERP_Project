package com.klef.jfsd.sdpproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.jfsd.sdpproject.models.User;
import com.klef.jfsd.sdpproject.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkuserlogin(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
        
    }
    
}