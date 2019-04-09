package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import model.Computer;

public class ComputerDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String SQL_FIND_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name = ?";
	private static final String SQL_FIND_BY_COMPANY = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE company_id = ?";
	private static final String SQL_FIND_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id = ?";
	private static final String SQL_SEARCH_BY_NAME = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE name LIKE ?";
	private static final String SQL_INSERT = "INSERT INTO computer (name, company_id, introduced, discontinued) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE computer SET name=?, company_id=?, introduced=?, discontinued=? WHERE id = ?";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = ?";

	private static final Logger logger = Logger.getLogger(ComputerDao.class);

	private DAOFactory daoFactory;

	//////// CONSTRUCTOR//////

	public ComputerDao(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	//////// QUERIES////////

	public Optional<Integer> add(Computer computer) throws DAOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeurAutoGenerees = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_INSERT, true, computer.getName(),
					computer.getCompanyId(), computer.getIntroducedDate().orElse(null), computer.getDiscontinuedDate().orElse(null));

			int statut = preparedStatement.executeUpdate();
			logger.info("Access to the data base : " + SQL_INSERT + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

			if (statut == 0) {
				logger.warn("Failed of the creation of the new computer, no line added");
				throw new DAOException("Failed of the creation of the new computer, no line added");
			}

			valeurAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeurAutoGenerees.next()) {
				computer.setId(valeurAutoGenerees.getInt(1));
				logger.info("\nNEW COMPUTER\n" + computer);
			} else {
				logger.warn("Failed of the creation of the new computer, no line added");
				throw new DAOException("Failed of the creation of the new computer, no line added");
			}
			
		} catch (SQLException e) {
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
		} finally {
			closeConnections(valeurAutoGenerees, preparedStatement, connexion);
		}

		return Optional.ofNullable(computer.getId());
	}

	public List<Computer> findAll() throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_ALL, false);
			
			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_ALL);
			
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
			
		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_ALL);
			throw new DAOException(e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return computers;
	}

	public Optional<Computer> findById(int id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_BY_ID, false, id);
			
			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");
			
			if (resultSet.next()) {
				computer = map(resultSet);

			} else{
				logger.info("No computer with the id " + id + " found");
			}

		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_ID + " (" + id + ")");
			throw new DAOException(e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return Optional.ofNullable(computer);
	}

	public Optional<Computer> findByName(String name) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_BY_NAME, false, name);
			
			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_BY_NAME + " (" + name + ")");
			
			if (resultSet.next()) {
				computer = map(resultSet);
			} else {
				logger.info("No computer with the name " + name + " found");
			}

		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_NAME + " (" + name + ")");
			throw new DAOException(e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return Optional.ofNullable(computer);
	}

	public List<Computer> searchByName(String search) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_SEARCH_BY_NAME, false, "%" + search + "%");
			
			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME + " (" + search + ")");
			
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException(e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return computers;
	}

	public List<Computer> findByCompany(String search) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_FIND_BY_COMPANY, false, search);
			
			resultSet = preparedStatement.executeQuery();
			logger.info("Access to the data base : " + SQL_FIND_BY_COMPANY + " (" + search + ")");
			
			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_COMPANY+ " (" + search + ")");
			throw new DAOException(e);
		} finally {
			closeConnections(resultSet, preparedStatement, connexion);
		}

		return computers;
	}

	public boolean update(Computer computer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_UPDATE, true, computer.getName(),
					computer.getCompanyId(), computer.getIntroducedDate().orElse(null), computer.getDiscontinuedDate().orElse(null), computer.getId());

			statut = preparedStatement.executeUpdate();
			logger.info("Access to the data base : " + SQL_UPDATE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");

			if (statut == 0) {
				logger.info("This computer does not exist");
				return false;
			}

			return true;
		} catch (SQLException e) {
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
		} finally {
			closeConnections(preparedStatement, connexion);
		}
	}

	public boolean delete(Computer computer) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initPreparedStatement(connexion, SQL_DELETE, true, computer.getId());

			statut = preparedStatement.executeUpdate();
			logger.info("Access to the data base : " + SQL_DELETE + "("
					+ computer.getName() + "," 
					+ computer.getCompanyId() + ","
					+ computer.getIntroducedDate().orElse(null) + ","
					+ computer.getDiscontinuedDate().orElse(null) + ")");
			
			if (statut == 0) {
				logger.info("This computer does not exist");
				return false;
			}

			return true;

		} catch (SQLException e) {
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

		} finally {
			closeConnections(preparedStatement, connexion);
		}
	}

	///////// MAPPING////////

	private static Computer map(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer.ComputerBuilder().build();

		computer.setId(resultSet.getInt("id"));
		computer.setName(resultSet.getString("name"));
		computer.setCompanyId((Long) resultSet.getObject("company_id"));
		computer.setIntroducedDate(resultSet.getDate("introduced"));
		computer.setDiscontinuedDate(resultSet.getDate("discontinued"));

		return computer;
	}

}
