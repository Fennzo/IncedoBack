package com.inventorysystem.api.service;

import com.inventorysystem.api.dto.CustomerOrdersDTO;
import com.inventorysystem.api.model.Address;
import com.inventorysystem.api.model.CustomerProduct;
import com.inventorysystem.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.Customer;
import com.inventorysystem.api.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	public Customer post(Customer customer) {

		return customerRepository.save(customer);
	}

	public Customer getByUsername(String username) {

		return customerRepository.getByUsername(username);
	}

	public Customer getById(int cid) {

		return customerRepository.findById(cid).get();
	}


    public Optional<Address> getAddressByCustomerId(int cid) {
		Customer customer = customerRepository.getById(cid);
		return Optional.ofNullable(addressRepository.findById(customer.getAddress().getId()));
    }

	public List<CustomerOrdersDTO> getCustomerProductsByCustomerId(int customerId) {
		List<Object[]> queryResult = customerRepository.getCustomerProductsByCustomerId(customerId);
		List<CustomerOrdersDTO> dtoList = new ArrayList<>();

		for (Object[] result : queryResult) {
			CustomerOrdersDTO dto = new CustomerOrdersDTO();
			dto.setId((int) result[0]);
			dto.setDateOfPurchase((LocalDate) result[1]);
			dto.setTotalAmount((int) result[2]);
			dto.setQuantity((int) result[3]);
			dto.setStatus((String) result[4]);
			dtoList.add(dto);
		}

		return dtoList;
	}
}







