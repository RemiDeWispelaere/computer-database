package dao;

import model.Computer;

public interface ComputerDao {

	void add(Computer computer) throws DAOException;
	
	Computer findByName( String name ) throws DAOException;
}
