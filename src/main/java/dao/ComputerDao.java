package main.java.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import main.java.model.Computer;

@Repository("computerDao")
@Scope("singleton")
public class ComputerDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ?";
	private static final String SQL_FIND_BY_COMPANY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ?";
	private static final String SQL_FIND_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_SEARCH_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (:name, :companyId, :introducedDate, :discontinuedDate)";
	private static final String SQL_UPDATE = "UPDATE computer SET name = :name, company_id = :companyId, introduced = :introducedDate, discontinued = :discontinuedDate WHERE id = :id";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = :id";

	private static final Logger logger = Logger.getLogger(ComputerDao.class);

	@Autowired
	private DataSource dataSource;

	//////// QUERIES////////

	public void add(Computer computer) throws DAOException {

		if (computer == null) {
			logger.warn("Failure to create the new computer, no line added");
			throw new DAOException("Failure to create the new computer, no line added");
		}

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", computer.getName());
			params.addValue("companyId", computer.getCompanyId());
			params.addValue("introducedDate", computer.getIntroducedDate().orElse(null));
			params.addValue("discontinuedDate", computer.getDiscontinuedDate().orElse(null));

			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate.update(SQL_INSERT, params);

			logger.info("Access to the data base : " + SQL_INSERT + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

		} catch (DataAccessException e) {
			logger.warn("Failure of the query : " + SQL_INSERT + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

			throw new DAOException("Failure of the query : " + SQL_INSERT + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");
		}
	}

	public List<Computer> findAll() throws DAOException {

		List<Computer> computers;

		try {					
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			computers = jdbcTemplate.query(SQL_FIND_ALL, new MapComputer());

			logger.info("Access to the data base : " + SQL_FIND_ALL);

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_ALL);
			throw new DAOException(e);
		}

		return computers;
	}

	public Optional<Computer> findById(int id) throws DAOException {

		Computer computer = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			computer = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new MapComputer(), id);

			logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");

		} catch(EmptyResultDataAccessException e) {
			logger.warn("No computer with the id " + computer.getId() + " found");

		}catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_ID + " (" + id + ")");
			throw new DAOException(e);
		}

		return Optional.ofNullable(computer);
	}

	public Optional<Computer> findByName(String name) throws DAOException {

		Computer computer = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			computer = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, new MapComputer(), name);

			logger.info("Access to the data base : " + SQL_FIND_BY_NAME + " (" + name + ")");

		} catch(EmptyResultDataAccessException e) {
			logger.warn("No computer with the name " + name + " found");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_NAME + " (" + name + ")");
			throw new DAOException(e);
		}

		return Optional.ofNullable(computer);
	}

	public List<Computer> searchByName(String search) throws DAOException {

		List<Computer> computers;
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			computers = jdbcTemplate.query(SQL_SEARCH_BY_NAME, new MapComputer(), "%"+search+"%");

			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME + " (" + search + ")");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException(e);
		}

		return computers;
	}

	public List<Computer> findByCompany(String search) throws DAOException {

		List<Computer> computers;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			computers = jdbcTemplate.query(SQL_FIND_BY_COMPANY, new MapComputer(), search);

			logger.info("Access to the data base : " + SQL_FIND_BY_COMPANY + " (" + search + ")");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_COMPANY+ " (" + search + ")");
			throw new DAOException(e);
		}

		return computers;
	}

	public void update(Computer computer) throws DAOException {

		if (computer == null) {
			logger.warn("Failure to update the computer");
			throw new DAOException("Failure to update the computer");
		}

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", computer.getId());
			params.addValue("name", computer.getName());
			params.addValue("companyId", computer.getCompanyId());
			params.addValue("introducedDate", computer.getIntroducedDate().orElse(null));
			params.addValue("discontinuedDate", computer.getDiscontinuedDate().orElse(null));

			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate.update(SQL_UPDATE, params);

			logger.info("Access to the data base : " + SQL_UPDATE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");


		} catch(EmptyResultDataAccessException e) {
			logger.warn("No computer with the id " + computer.getId() + " found");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_UPDATE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

			throw new DAOException("Query failure : " + SQL_UPDATE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");
		}
	}

	public void delete(Computer computer) throws DAOException {

		if (computer == null) {
			logger.warn("Failure to remove the computer");
			throw new DAOException("Failure to remove the computer");
		}

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", computer.getId());

			NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			jdbcTemplate.update(SQL_DELETE, params);


			logger.info("Access to the data base : " + SQL_DELETE + "(" + computer.getId() + ")");
		} catch(EmptyResultDataAccessException e) {
			logger.warn("No computer with the id " + computer.getId() + " found");

		}catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_DELETE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

			throw new DAOException("Query failure : " + SQL_DELETE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

		}
	}

	///////// MAPPING////////

	private class MapComputer implements RowMapper<Computer>{

		@Override
		public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			Computer computer = new Computer.ComputerBuilder().build();

			computer.setId(resultSet.getInt("id"));
			computer.setName(resultSet.getString("name"));
			computer.setCompanyId((Long) resultSet.getObject("company_id"));
			computer.setIntroducedDate(resultSet.getDate("introduced"));
			computer.setDiscontinuedDate(resultSet.getDate("discontinued"));

			return computer;
		}
	}

}
