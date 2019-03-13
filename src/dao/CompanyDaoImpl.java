package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Company;

public class CompanyDaoImpl implements CompanyDao {

	private static final String SQL_FIND_ALL = "SELECT * FROM company";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT * FROM company LIMIT ?";
	private DAOFactory daoFactory;

	////////CONSTRUCTOR//////

	public CompanyDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
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
