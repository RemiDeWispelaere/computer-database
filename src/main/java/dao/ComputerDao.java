package main.java.dao;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import main.java.model.Computer;

@Repository("computerDao")
@Scope("singleton")
public class ComputerDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "FROM Computer";
	private static final String SQL_FIND_BY_NAME = "FROM Computer WHERE name = :name";
	private static final String SQL_FIND_BY_COMPANY = "FROM Computer WHERE company_id = :companyId";
	private static final String SQL_FIND_BY_ID = "FROM Computer WHERE id = :id";
	private static final String SQL_SEARCH_BY_NAME = "FROM Computer WHERE name LIKE :name";
	private static final String SQL_INSERT = "INSERT INTO Computer (name, company_id, introduced, discontinued) VALUES (:name, :companyId, :introducedDate, :discontinuedDate)";
	private static final String SQL_UPDATE = "UPDATE Computer SET name = :name, company_id = :companyId, introduced = :introducedDate, discontinued = :discontinuedDate WHERE id = :id";
	private static final String SQL_DELETE = "DELETE FROM computer WHERE id = :id";

	private static final Logger logger = Logger.getLogger(ComputerDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	//////// QUERIES////////

	public void add(Computer computer) throws DAOException {

		if (computer == null) {
			logger.warn("Failure to create the new computer, no line added");
			throw new DAOException("Failure to create the new computer, no line added");
		}

		try {			
			Session session = sessionFactory.openSession();
			session.save(computer);

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

		//			logger.info("Access to the data base : " + SQL_FIND_ALL);

		try {
			Session session = sessionFactory.openSession();
			return session.createQuery(SQL_FIND_ALL).list();

		}catch(HibernateException e) {
			logger.warn("\"Query failure : \" + SQL_FIND_ALL");
			throw new HibernateException(e);
		}
	}

	public Optional<Computer> findById(int id) throws DAOException {

		Computer computer = null;

		try {
			Session session = sessionFactory.openSession();
			computer = (Computer) session.get(Computer.class, id);

//			logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");

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
			Session session = sessionFactory.openSession();
			computer = (Computer) session.createQuery(SQL_FIND_BY_NAME)
					.setParameter("name", name)
					.uniqueResult();

//			logger.info("Access to the data base : " + SQL_FIND_BY_NAME + " (" + name + ")");

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
			Session session = sessionFactory.openSession();
			computers = session.createQuery(SQL_SEARCH_BY_NAME)
					.setParameter("name", "%"+search+"%")
					.list();

//			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME + " (" + search + ")");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException(e);
		}

		return computers;
	}

	public List<Computer> findByCompany(String search) throws DAOException {

		List<Computer> computers;

		try {
			Session session = sessionFactory.openSession();
			computers = session.createQuery(SQL_FIND_BY_COMPANY)
					.setParameter("companyId", search)
					.list();

//			logger.info("Access to the data base : " + SQL_FIND_BY_COMPANY + " (" + search + ")");

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
		//"UPDATE Computer SET name = :name, company_id = :companyId, introduced = :introducedDate, discontinued = :discontinuedDate WHERE id = :id";

		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(SQL_UPDATE)
				.setParameter("id", computer.getId())
				.setParameter("name", computer.getName())
				.setParameter("companyId", computer.getCompanyId())
				.setParameter("introducedDate", computer.getIntroducedDate().orElse(null))
				.setParameter("discontinuedDate", computer.getDiscontinuedDate().orElse(null));
			
			Transaction transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();

//			logger.info("Access to the data base : " + SQL_UPDATE + "("
//					+ computer.getName() + "," 
//					+ computer.getCompanyId() + ","
//					+ computer.getIntroducedDate().orElse(null) + ","
//					+ computer.getDiscontinuedDate().orElse(null) + ")");


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
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(computer);
			transaction.commit();

//			logger.info("Access to the data base : " + SQL_DELETE + "(" + computer.getId() + ")");
			
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

}
