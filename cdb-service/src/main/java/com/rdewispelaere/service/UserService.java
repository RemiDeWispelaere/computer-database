package com.rdewispelaere.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rdewispelaere.dao.UserDao;
import com.rdewispelaere.model.Role;
import com.rdewispelaere.model.User;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	//	@Autowired
	//	public UserService(UserDao userDao) {
	//		this.userDao = userDao;
	//	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

		if(name == null) {
			throw new IllegalArgumentException("User name can't be null");
		}
		else {
			User user = userDao.findByUserName(name).orElseThrow(IllegalArgumentException::new);
			List<GrantedAuthority> roles = buildUserRoles(user.getRole());

			return buildUserForAuthentication(user, roles);
		}
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> roles) {
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true, true, true, roles);
	}

	private List<GrantedAuthority> buildUserRoles(Set<Role> roles){

		Set<GrantedAuthority> setRoles = new HashSet<GrantedAuthority>();

		for(Role role : roles) {
			setRoles.add(new SimpleGrantedAuthority(role.getRole().name()));
		}
		
		return new ArrayList<GrantedAuthority>(setRoles);
	}
}
