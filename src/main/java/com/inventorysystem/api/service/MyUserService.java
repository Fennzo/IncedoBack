package com.inventorysystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inventorysystem.api.model.User;
import com.inventorysystem.api.repository.UserRepository;

@Service
public class MyUserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getByUsername(username);
		
		return user;
	}
	public User post(User user) {
		 
		return userRepository.save(user);
	}

    public boolean verifyPin(User user, String safetyPin) {
		if(user.getSafetyPin().equals(safetyPin))
			return true;
		return false;
    }

	public void updatePassword(String username, String password) {
		User user = (User)loadUserByUsername(username);
		user.setPassword(password);
		userRepository.save(user);
	}
}
