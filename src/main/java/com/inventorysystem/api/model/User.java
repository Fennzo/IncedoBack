package com.inventorysystem.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.inventorysystem.api.enums.RoleType;

@Entity
@Table(name = "user_details")
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String username;
	private String password;

	private String safetyPin;
	@Enumerated(EnumType.STRING)
	private RoleType role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}

	public String getSafetyPin() {
		return safetyPin;
	}

	public void setSafetyPin(String safetyPin) {
		this.safetyPin = safetyPin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//convert the role into GrantedAuthority 
		//Step 1: convert role into SimpleGrantedAuthority
		SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role.toString());
		//Step 2: Add sga to List of type GrantedAuthority
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(sga);
		return list;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", safetyPin='" + safetyPin + '\'' +
				", role=" + role +
				'}';
	}
}
