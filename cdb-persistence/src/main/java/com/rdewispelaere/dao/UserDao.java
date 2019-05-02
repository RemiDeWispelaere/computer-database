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

import com.rdewispelaere.model.User;

@Repository("userDao")
@Scope("singleton")
public class UserDao {
	
	private static final String SQL_FIND_BY_NAME = "SELECT name, password, role FROM user WHERE name = :name";
	
	private static final Logger logger = Logger.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public Optional<User> findByUserName(String name) {
		
		Session session = sessionFactory.openSession();
		User user = null;
		
		try{ 
			user = session.get(User.class, name);
			
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