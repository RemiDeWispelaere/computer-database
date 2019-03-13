package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Computer;
import static dao.DAOUtilitaire.*;

public class ComputerDaoImpl implements ComputerDao {

	private static final String SQL_FIND_BY_NAME = "SELECT id, name, company_id FROM computer WHERE name = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?))";
	private DAOFactory daoFactory;
	
	////////CONSTRUCTOR//////
	
	public ComputerDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
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
