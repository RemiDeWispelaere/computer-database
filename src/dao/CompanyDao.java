package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import model.Company;
import model.Computer;

public class CompanyDao {

	private static final String SQL_FIND_ALL = "SELECT id, name FROM company";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT id, name FROM company LIMIT ?";
	private static final String SQL_FIND_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM company WHERE name = ?";
	private static final String SQL_SEARCH_BY_NAME = "SELECT id, name FROM company WHERE name LIKE ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM company WHERE id = ?";
	private static final String SQL_DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";
	
	private static final Logger logger  = Logger.getLogger(CompanyDao.class);
	private DAOFactory daoFactory;
	

	////////CONSTRUCTOR//////

	public CompanyDao(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	////////QUERIES////////

	public List<Company> findAll() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_ALL, false);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				companies.add(map(resultSet));
			}
			
			connexion.commit();
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}

		return companies;
	}

	public List<Company> findAllWithLimit(int limit) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_ALL_WITH_LIMIT, false, limit);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				companies.add(map(resultSet));
			}
			
			connexion.commit();
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}

		return companies;
	}
	
	public Optional<Company> findById(Long id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_BY_ID, false, id);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				company = map(resultSet);
				
			} else
				System.out.println("\n This company does not exist");

			connexion.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
		}

		return Optional.ofNullable(company);
	}
	
	public Optional<Company> findByName(String name) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_BY_NAME, false, name);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				company = map(resultSet);
				
			} else
				System.out.println("\n This company does not exist");

			connexion.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
		}

		return Optional.ofNullable(company);
	}
	
	public List<Company> searchByName(String search) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Company> companies = new ArrayList<Company>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SEARCH_BY_NAME, false, "%" + search + "%");
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				companies.add(map(resultSet));
			}
			
			connexion.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return companies;
	}

	public void deleteCompanyt(Company company) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatementCompany = null;
		PreparedStatement preparedStatementComputers = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatementCompany = initialisationRequetePreparee(connexion, SQL_DELETE_BY_ID, false, company.getId());
			preparedStatementComputers = initialisationRequetePreparee(connexion, SQL_DELETE_COMPUTERS, false, company.getId());
			
			int statut = preparedStatementComputers.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Delete failed - Computers related to the company, no line deleted");
			}else {
				
			}
			
			statut = preparedStatementCompany.executeUpdate();
			if(statut == 0) {
				throw new DAOException("Delete failed - Company, no line deleted");
			}
			
			connexion.commit();
		}catch(SQLException e){
			throw new DAOException(e);
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
