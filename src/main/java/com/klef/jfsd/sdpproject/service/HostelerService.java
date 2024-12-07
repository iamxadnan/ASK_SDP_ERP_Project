package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Hosteler;

public interface HostelerService {
    Hosteler createHosteler(Hosteler hosteler);
    public boolean existsById(int userid) ;
    
}
