package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.model.Company;

@Repository("companyDao")
@Scope("singleton")
public class CompanyDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "SELECT id, name FROM company";
	private static final String SQL_FIND_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
	private static final String SQL_SEARCH_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM company WHERE id = :companyId";
	private static final String SQL_DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = :companyId";

	private static final Logger logger  = Logger.getLogger(CompanyDao.class);

	@Autowired
	private DataSource dataSource;

	////////QUERIES////////

	public List<Company> findAll() throws DAOException {

		List<Company> companies = new ArrayList<Company>();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			companies = jdbcTemplate.query(SQL_FIND_ALL, new MapCompany());
			logger.info("Access to the data base : " + SQL_FIND_ALL);

		}catch(DataAccessException e){
			logger.warn("Query failure : " + SQL_FIND_ALL);
			throw new DAOException("Query failure : " + e);
		}

		return companies;
	}

	public Optional<Company> findById(Long id) throws DAOException {

		Company company = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			company = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new MapCompany(), id);

			logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");

		} catch(EmptyResultDataAccessException e) {
			logger.warn("No company with the id " + id + " found");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_ID+ " (" + id + ")");
			throw new DAOException("Query failure : " + e);
		}

		return Optional.ofNullable(company);
	}

	public Optional<Company> findByName(String name) throws DAOException {

		Company company = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			company = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, new MapCompany(), name);

			logger.info("Access to the data base : " + SQL_FIND_BY_NAME+ " (" + name + ")");

		} catch (EmptyResultDataAccessException e) {
			logger.warn("No company named " + name + " found");
		}
		catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_NAME+ " (" + name + ")");
			throw new DAOException("Query failure : " + e);
		}

		return Optional.ofNullable(company);
	}

	public List<Company> searchByName(String search) throws DAOException {

		List<Company> companies = new ArrayList<Company>();

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			companies = jdbcTemplate.query(SQL_SEARCH_BY_NAME, new MapCompany(), "%"+search+"%");

			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException("Query failure : " + e);
		}

		return companies;
	}

	@Transactional(rollbackFor = DAOException.class)
	public void deleteCompany(Company company) throws DAOException{
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("companyId", company.getId());
			
			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate.update(SQL_DELETE_COMPUTERS, params);

			logger.info("Access to the data base : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");

			jdbcTemplate.update(SQL_DELETE_BY_ID, params);
			
			logger.info("Access to the data base : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");

		} catch (EmptyResultDataAccessException e) {
			logger.warn("Delete company failed - no line deleted");
			
		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");
			logger.warn("Query failure : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");
			throw new DAOException("Query failure : " + e);
		}
	}

	/////////MAPPING////////

	private class MapCompany implements RowMapper<Company>{

		@Override
		public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			Company company = new Company();

			company.setId( resultSet.getInt( "id" ) );
			company.setName( resultSet.getString( "name" ) );

			return company;
		}
	}

}
