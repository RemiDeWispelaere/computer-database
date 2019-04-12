package dao;

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
import org.springframework.stereotype.Repository;

import model.Company;

@Repository("companyDao")
@Scope("singleton")
public class CompanyDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "SELECT id, name FROM company";
	private static final String SQL_FIND_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
	private static final String SQL_SEARCH_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM company WHERE id = ?";
	private static final String SQL_DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";

	private static final Logger logger  = Logger.getLogger(CompanyDao.class);
	
	@Autowired
	private DataSource dataSource;

	////////QUERIES////////

	public List<Company> findAll() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connexion = dataSource.getConnection();
			preparedStatement = initPreparedStatement(connexion,SQL_FIND_ALL, false);

			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_ALL);

			while(resultSet.next()) {
				companies.add(map(resultSet));
			}

		}catch(SQLException e){
			logger.warn("Query failure : " + SQL_FIND_ALL);
			throw new DAOException("Query failure : " + e);
		}finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return companies;
	}

	public Optional<Company> findById(Long id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connexion = dataSource.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_BY_ID, false, id);

			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");

			if (resultSet.next()) {
				company = map(resultSet);

			} else {
				logger.info("No company with the id " + id + " found");
			}

		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_ID+ " (" + id + ")");
			throw new DAOException("Query failure : " + e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return Optional.ofNullable(company);
	}

	public Optional<Company> findByName(String name) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connexion = dataSource.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_BY_NAME, false, name);

			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_BY_NAME+ " (" + name + ")");

			if (resultSet.next()) {
				company = map(resultSet);

			} else{
				logger.info("No company named " + name + " found");
			}

		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_NAME+ " (" + name + ")");
			throw new DAOException("Query failure : " + e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return Optional.ofNullable(company);
	}

	public List<Company> searchByName(String search) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connexion = dataSource.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_SEARCH_BY_NAME, false, "%" + search + "%");

			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");

			while (resultSet.next()) {
				companies.add(map(resultSet));
			}

		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException("Query failure : " + e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return companies;
	}

	public void deleteCompany(Company company) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatementCompany = null;
		PreparedStatement preparedStatementComputers = null;
		try {
			connexion = dataSource.getConnection();
			connexion.setAutoCommit(false);
			preparedStatementComputers = initPreparedStatement(connexion, SQL_DELETE_COMPUTERS, false, company.getId());
			preparedStatementCompany = initPreparedStatement(connexion, SQL_DELETE_BY_ID, false, company.getId());

			int statut = preparedStatementComputers.executeUpdate();
			
			logger.info("Access to the data base : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");
			if(statut == 0) {
				throw new DAOException("Delete Computers related to the company failed - no line deleted");
			}

			statut = preparedStatementCompany.executeUpdate();
			logger.info("Access to the data base : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");
			
			if(statut == 0) {
				throw new DAOException("Delete company failed - no line deleted");
			}

			connexion.commit();

		}catch(SQLException e){
			logger.warn("Query failure : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");
			logger.warn("Query failure : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");
			throw new DAOException("Query failure : " + e);
		}
	}

	/////////MAPPING////////

	private static Company map( ResultSet resultSet ) throws SQLException {
		Company company = new Company();
		company.setId( resultSet.getInt( "id" ) );
		company.setName( resultSet.getString( "name" ) );
		return company;
	}
}
