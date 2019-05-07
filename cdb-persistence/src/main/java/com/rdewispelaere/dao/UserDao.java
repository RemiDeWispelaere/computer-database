package com.rdewispelaere.dao;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.rdewispelaere.model.Role;
import com.rdewispelaere.model.User;

@Repository("userDao")
@Scope("singleton")
public class UserDao {
	
	private static final String SQL_INSERT_USER = "INSERT INTO User (name, password) VALUES (:name, :password)";
	private static final String SQL_INSERT_ROLE = "INSERT INTO Role (username, role) VALUES (:username, :role)";
	private static final String SQL_FIND_BY_NAME = "FROM User WHERE name = :name";
	
	private static final Logger logger = Logger.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	//////// QUERIES////////

	public void add(User user, Role role) throws DAOException {
		
		if(user == null || role == null) {
			logger.warn("Failure to create the new user, no line added");
			throw new DAOException("Failure to create the new user, no line added");
		}
		
		try {
			Session session = sessionFactory.openSession();
			session.save(user);
			session.save(role);
			
		} catch(DataAccessException e) {
			logger.warn("Failure to save the new user, no line added");
			throw new DAOException("Failure to save the new user, no line added");
		
		}
	}
	public Optional<User> findByUserName(String name) {
		
		User user = null;
		
		try{ 
			Session session = sessionFactory.openSession();
			user = (User) session.createQuery(SQL_FIND_BY_NAME)
					.setParameter("name", name)
					.uniqueResult();
			
		} catch(EmptyResultDataAccessException e) {
			logger.warn("No user with the name " + name + " found");
			
		} catch(DataAccessException e) {
			logger.warn("QUERY FAILURE : " + SQL_FIND_BY_NAME + " (" + name + ")");
			throw new DAOException(e);
		}

		return Optional.ofNullable(user);
	}

}
;