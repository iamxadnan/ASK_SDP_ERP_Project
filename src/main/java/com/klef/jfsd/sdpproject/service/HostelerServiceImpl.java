package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Hosteler;
import com.klef.jfsd.sdpproject.repository.HostelerRepository;
import com.klef.jfsd.sdpproject.service.HostelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostelerServiceImpl implements HostelerService {
    @Autowired
    private HostelerRepository hostelerRepository;

    @Override
    public Hosteler createHosteler(Hosteler hosteler) {
        return hostelerRepository.save(hosteler);
    }

    public boolean existsById(int userid) {
        return hostelerRepository.existsById(userid);
    }
}
