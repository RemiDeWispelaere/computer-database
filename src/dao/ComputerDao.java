package dao;

import java.util.List;

import model.Computer;

public interface ComputerDao {

	void add(Computer computer) throws DAOException;
	List<Computer> findAll() throws DAOException;
	List<Computer> findAllWithLimit(int limit) throws DAOException;
	Computer findByName( String name ) throws DAOException;
}
