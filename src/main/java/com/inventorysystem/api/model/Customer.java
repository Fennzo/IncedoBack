package com.inventorysystem.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

 
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String contact; 
	
	@ManyToOne
	private Address address;

	@OneToOne
	private User user; 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", name='" + name + '\'' +
				", contact='" + contact + '\'' +
				", address=" + address +
				", user=" + user +
				'}';
	}
	
	/*
	@ManyToMany
	@JoinTable(name = "customer_product", 
	joinColumns = @JoinColumn(name ="customer_id") ,
	inverseJoinColumns = @JoinColumn(name ="product_id")
	)
	private List<Product> products;
	*/
	
}
