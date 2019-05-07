package com.rdewispelaere.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "name", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Role> role = new HashSet<Role>();

	/////////////CONSTRUCTOR///////////////

	public User() {}
	
	public User(String nName, String nPass) {
		this.username = nName;
		this.password = nPass;
	}
	
	public User(String nName, String nPass, Set<Role> nRole) {
		this.username = nName;
		this.password = nPass;
		this.role = nRole;
	}
	
	//////////////SETTER | GETTER ///////////////////

	//NAME
	public void setUsername(String nName) {
		this.username = nName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	//PASSWORD
	public void setPassword(String nPassword) {
		this.password = nPassword;
	}

	public String getPassword() {
		return this.password;
	}
	
	//ROLE
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	public Set<Role> getRole(){
		return this.role;
	}
}
