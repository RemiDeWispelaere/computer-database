package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Computer;
import static dao.DAOUtilitaire.*;

public class ComputerDaoImpl implements ComputerDao {

	private static final String SQL_FIND_ALL = "SELECT * FROM computer";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT * FROM computer LIMIT ?";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name, company_id FROM computer WHERE name = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?))";
	private DAOFactory daoFactory;
	
	////////CONSTRUCTOR//////
	
	public ComputerDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	////////QUERIES////////
	@Override
	public void add(Computer computer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeurAutoGenerees = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, 
					computer.getName(), computer.getManufacturerId(), computer.getDateIntroduced(), computer.getDateDiscontinued());
			int statut = preparedStatement.executeUpdate();
			
			if(statut == 0) {
				throw new DAOException("Echec de la creation de -computer-, aucune ligne ajoutee dans la table");
			}
			
			valeurAutoGenerees = preparedStatement.getGeneratedKeys();
			if(valeurAutoGenerees.next()) {
				computer.setId(valeurAutoGenerees.getInt(1));
			}else {
				throw new DAOException("Echec de la creation de -computer-, aucune ligne ajoutee dans la table");
			}
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(valeurAutoGenerees, preparedStatement, connexion);
		}

	}

	@Override
	public List<Computer> findAll() throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_ALL, false);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				computers.add(map(resultSet));
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}
		
		return computers;
	}
	
	@Override
	public List<Computer> findAllWithLimit(int limit) throws DAOException{
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_ALL_WITH_LIMIT, false, limit);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				computers.add(map(resultSet));
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}
		
		return computers;
	}
	
	@Override
	public Computer findByName(String name) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_BY_NAME, false, name);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = map(resultSet);
			}
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}
		
		return computer;
	}

	/////////MAPPING////////
	
	private static Computer map( ResultSet resultSet ) throws SQLException {
	    Computer computer = new Computer();
	    computer.setId( resultSet.getInt( "id" ) );
	    computer.setName( resultSet.getString( "name" ) );
	    computer.setManufacturerId(resultSet.getInt( "company_id"));
//TODO Ajouter mapping des dates
	    return computer;
	}
}
