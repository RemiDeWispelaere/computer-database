package com.rdewispelaere.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	public enum UserRole {
		ROLE_ADMIN,
		ROLE_USER
	}

	/////////////CONSTRUCTOR///////////////

	public Role() {}

	public Role(String nUsername, UserRole nRole) {
		this.username = nUsername;
		this.role = nRole;
	}
	
	//////////////SETTER | GETTER ///////////////////
	
	//ID
	public void setId(Integer nId) {
		this.id = nId;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	//USERNAME
	public void setUsername(String nUsername) {
		this.username = nUsername;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	//ROLE
	public void setRole(UserRole nRole) {
		this.role = nRole;
	}
	
	public UserRole getRole() {
		return this.role;
	}
}
