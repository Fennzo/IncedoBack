package com.inventorysystem.api.service;

import com.inventorysystem.api.model.Outward;
import com.inventorysystem.api.repository.OutwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutwardService {

    @Autowired
    private OutwardRepository outwardRepository;

    public Outward post(Outward outward){
        return outwardRepository.save(outward);
    }
}
