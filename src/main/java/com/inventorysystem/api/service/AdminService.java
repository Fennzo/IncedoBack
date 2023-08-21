package com.inventorysystem.api.service;

import com.inventorysystem.api.model.Admin;
import com.inventorysystem.api.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin getByUsername(String username){
        return adminRepository.getByUsername(username);
    }
}
