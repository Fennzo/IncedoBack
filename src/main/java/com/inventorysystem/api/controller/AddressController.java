package com.inventorysystem.api.controller;

import com.inventorysystem.api.model.Address;
import com.inventorysystem.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
    private AddressService addressService;

    @PutMapping("/update")
    public Address updateAddress(@RequestBody Address address){
       return addressService.post(address);
    }
}


