package com.rdewispelaere.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "username")
	private User user;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	public enum UserRole {
		ROLE_ADMIN,
		ROLE_USER
	}

	/////////////CONSTRUCTOR///////////////

	public Role() {}

	public Role(User nUsername, UserRole nRole) {
		this.user = nUsername;
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
	public void setUser(User nUser) {
		this.user = nUser;
	}
	
	public User getUser() {
		return this.user;
	}
	
	//ROLE
	public void setRole(UserRole nRole) {
		this.role = nRole;
	}
	
	public String getRole() {
		return this.role.name();
	}
}
