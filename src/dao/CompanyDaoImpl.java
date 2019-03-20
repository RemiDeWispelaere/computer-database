package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import model.Company;

public class CompanyDaoImpl implements CompanyDao {

	private static final String SQL_FIND_ALL = "SELECT * FROM company";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT * FROM company LIMIT ?";
	private DAOFactory daoFactory;
	private Logger logger;

	////////CONSTRUCTOR//////

	public CompanyDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.logger = Logger.getLogger(CompanyDaoImpl.class);
		//this.logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
	}

	////////QUERIES////////

	@Override
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
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}

		return companies;
	}

	@Override
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
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}

		return companies;
	}

	/////////MAPPING////////

	private static Company map( ResultSet resultSet ) throws SQLException {
		Company company = new Company();
		company.setId( resultSet.getInt( "id" ) );
		company.setName( resultSet.getString( "name" ) );
		return company;
	}
}
