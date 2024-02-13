package com.fiveguys.robocar.service;

import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarpoolRequestService {
    private final CarpoolRequestRepository carpoolRequestRepository;

    @Autowired
    public CarpoolRequestService(CarpoolRequestRepository carpoolRequestRepository){
        this.carpoolRequestRepository = carpoolRequestRepository;
    }

    public void saveCarpoolRequest(CarpoolRequest carpoolRequest){
        carpoolRequestRepository.save(carpoolRequest);
    }

    public CarpoolRequest findCarpoolRequestById(String id){
        return carpoolRequestRepository.findById(id).orElse(null);
    }

}
