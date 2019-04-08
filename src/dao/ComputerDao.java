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
import static dao.DAOUtilitaire.*;

public class ComputerDao {

	private static final String SQL_FIND_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String SQL_FIND_ALL_WITH_LIMIT = "SELECT id, name, introduced, discontinued, company_id FROM computer LIMIT ?";
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
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, computer.getName(),
					computer.getCompanyId(), computer.getIntroducedDate().orElse(null), computer.getDiscontinuedDate().orElse(null));
			logger.info("accès à la base de données : " + preparedStatement);
			int statut = preparedStatement.executeUpdate();

			if (statut == 0) {
				throw new DAOException("Echec de la creation de -computer-, aucune ligne ajoutee dans la table");
			}

			valeurAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeurAutoGenerees.next()) {
				computer.setId(valeurAutoGenerees.getInt(1));
				System.out.println("\nNEW COMPUTER\n" + computer);
			} else {
				logger.error("ECHEC de la requete (" + SQL_INSERT + ")");
				throw new DAOException("Echec de la creation de -computer-, aucune ligne ajoutee dans la table");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeurAutoGenerees, preparedStatement, connexion);
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
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_ALL, false);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
		}

		return computers;
	}

	public List<Computer> findAllWithLimit(int limit) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_ALL_WITH_LIMIT, false, limit);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
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
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_BY_ID, false, id);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				computer = map(resultSet);

			} else
				System.out.println("\n This computer does not exist");

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
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
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_BY_NAME, false, name);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				computer = map(resultSet);
			} else
				System.out.println("\n This computer does not exist");

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			;
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
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SEARCH_BY_NAME, false, "%" + search + "%");
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return computers;
	}
	
	public List<Computer> searchByCompany(String search) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computers = new ArrayList<Computer>();

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_FIND_BY_COMPANY, false, search);
			logger.info("accès à la base de données : " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				computers.add(map(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return computers;
	}

	public boolean update(Computer cpu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, true, cpu.getName(),
					cpu.getCompanyId(), cpu.getIntroducedDate().orElse(null), cpu.getDiscontinuedDate().orElse(null), cpu.getId());
			logger.info("accès à la base de données : " + preparedStatement);
			statut = preparedStatement.executeUpdate();

			if (statut == 0) {
				System.out.println("\n This computer does not exist");
				return false;
			}

			return true;
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
	}

	public boolean delete(Computer cpu) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		int statut;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, true, cpu.getId());
			logger.info("accès à la base de données : " + preparedStatement);
			statut = preparedStatement.executeUpdate();

			if (statut == 0) {
				System.out.println("\n This computer does not exist");
				return false;
			}

			return true;

		} catch (SQLException e) {
			throw new DAOException(e);

		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
			;
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
