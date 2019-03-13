package dao;

import java.util.List;

import model.Company;

public interface CompanyDao {
	
	List<Company> findAll() throws DAOException;
	List<Company> findAllWithLimit(int limit) throws DAOException;
}
