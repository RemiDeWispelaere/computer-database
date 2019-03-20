package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import model.Computer;
import static dao.DAOUtilitaire.*;

public class ComputerDaoImpl implements ComputerDao {

	private static final String SQL_FIND_ALL = "SELECT * FROM computer";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT * FROM computer LIMIT ?";
	private static final String SQL_FIND_BY_NAME = "SELECT * FROM computer WHERE name = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM computer WHERE id = ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name=?, company_id=?, introduced=?, discontinued=? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";
	private DAOFactory daoFactory;
	private Logger logger = Logger.getLogger(ComputerDaoImpl.class);
	
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
					computer.getName(), 
					computer.getManufacturerId(), 
					computer.getDateIntroduced(), 
					computer.getDateDiscontinued());
			logger.info("accès à la base de données : " + preparedStatement);
			int statut = preparedStatement.executeUpdate();
			
			if(statut == 0) {
				throw new DAOException("Echec de la creation de -computer-, aucune ligne ajoutee dans la table");
			}
			
			valeurAutoGenerees = preparedStatement.getGeneratedKeys();
			if(valeurAutoGenerees.next()) {
				computer.setId(valeurAutoGenerees.getInt(1));
				System.out.println("\nNEW COMPUTER\n" + computer); 
			}else {
				logger.error("ECHEC de la requete (" + SQL_INSERT + ")");
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
			logger.info("accès à la base de données : " + preparedStatement);
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
			logger.info("accès à la base de données : " + preparedStatement);
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
	public Computer findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_FIND_BY_ID, false, id);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = map(resultSet);
			}
			else
				System.out.println("\n This computer does not exist");
			
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}
		
		return computer;
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
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = map(resultSet);
			}
			else
				System.out.println("\n This computer does not exist");
			
		}catch(SQLException e){
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);;
		}
		
		return computer;
	}
	
	@Override
	public boolean update(Computer cpu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, 
					cpu.getName(),
					cpu.getManufacturerId(),
					cpu.getDateIntroduced(),
					cpu.getDateDiscontinued(),
					cpu.getId());
			logger.info("accès à la base de données : " + preparedStatement);
			statut = preparedStatement.executeUpdate();
			
			if(statut == 0) {
				System.out.println("\n This computer does not exist");
				return false;
			}
			
			return true;
		}catch(SQLException e) {
			throw new DAOException(e);
		}finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}
	
	@Override
	public boolean delete(Computer cpu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;
		
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion,SQL_DELETE, true, cpu.getId());
			logger.info("accès à la base de données : " + preparedStatement);
			statut = preparedStatement.executeUpdate();
			
			if(statut == 0) {
				System.out.println("\n This computer does not exist");
				return false;
			}
			
			return true;
			
		}catch(SQLException e){
			throw new DAOException(e);
			
		}finally {
			fermeturesSilencieuses(preparedStatement, connexion);;
		}
	}

	/////////MAPPING////////
	
	private static Computer map( ResultSet resultSet ) throws SQLException {
	    Computer computer = new Computer();
	    
	    computer.setId( resultSet.getInt( "id" ) );
	    computer.setName( resultSet.getString( "name" ) );
	    computer.setManufacturerId((Long) resultSet.getObject( "company_id"));
	    computer.setDateIntroduced(resultSet.getDate("introduced"));
	    computer.setDateDiscontinued(resultSet.getDate("discontinued"));
	    
	    return computer;
	}

}
