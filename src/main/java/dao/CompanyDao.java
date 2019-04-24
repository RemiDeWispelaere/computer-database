package main.java.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.java.model.Company;

@Repository("companyDao")
@Scope("singleton")
public class CompanyDao implements DAOUtilitaire{

	private static final String SQL_FIND_ALL = "FROM Company";
	private static final String SQL_FIND_BY_ID = "FROM Company WHERE id = :id";
	private static final String SQL_FIND_BY_NAME = "FROM Company WHERE name = :name";
	private static final String SQL_SEARCH_BY_NAME = "FROM Company WHERE name LIKE :name";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM Company WHERE id = :companyId";
	private static final String SQL_DELETE_COMPUTERS = "DELETE FROM Computer WHERE company_id = :companyId";

	private static final Logger logger  = Logger.getLogger(CompanyDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	////////QUERIES////////

	public List<Company> findAll() throws DAOException {

		//logger.info("Access to the data base : " + SQL_FIND_ALL);

		try {
			Session session = sessionFactory.openSession();
			return session.createQuery(SQL_FIND_ALL).list();

		}catch(DataAccessException e){
			logger.warn("Query failure : " + SQL_FIND_ALL);
			throw new DAOException("Query failure : " + e);
		}
	}

	public Optional<Company> findById(Long id) throws DAOException {

		Company company = null;

		if(id != null) {
			try {
				Session session = sessionFactory.openSession();
				company = session.get(Company.class, id);

				//				logger.info("Access to the data base : " + SQL_FIND_BY_ID + " (" + id + ")");

			} catch(EmptyResultDataAccessException e) {
				logger.warn("No company with the id " + id + " found");

			} catch (DataAccessException e) {
				logger.warn("Query failure : " + SQL_FIND_BY_ID+ " (" + id + ")");
				throw new DAOException("Query failure : " + e);
			}
		}

		return Optional.ofNullable(company);
	}

	public Optional<Company> findByName(String name) throws DAOException {

		Company company = null;

		try {
			Session session = sessionFactory.openSession();
			company = (Company) session.createQuery(SQL_FIND_BY_NAME)
					.setParameter("name", name)
					.uniqueResult();

			//			logger.info("Access to the data base : " + SQL_FIND_BY_NAME+ " (" + name + ")");

		} catch (EmptyResultDataAccessException e) {
			logger.warn("No company named " + name + " found");

		}
		catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_FIND_BY_NAME+ " (" + name + ")");
			throw new DAOException("Query failure : " + e);
		}

		return Optional.ofNullable(company);
	}

	public List<Company> searchByName(String search) throws DAOException {

		List<Company> companies = new ArrayList<Company>();

		try {
			Session session = sessionFactory.openSession();
			companies = session.createQuery(SQL_SEARCH_BY_NAME)
					.setParameter("name", "%"+search+"%")
					.list();

			logger.info("Access to the data base : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_SEARCH_BY_NAME+ " (" + search + ")");
			throw new DAOException("Query failure : " + e);
		}

		return companies;
	}

	@Transactional(rollbackFor = DAOException.class)
	public void deleteCompany(Company company) throws DAOException{

		if (company == null) {
			logger.warn("Failure to remove the company");
			throw new DAOException("Failure to remove the company");
		}

		try {
			Session session = sessionFactory.openSession();
			Query deleteComputersQuery = session.createQuery(SQL_DELETE_COMPUTERS)
					.setParameter("companyId", company.getId());
			Transaction transaction = session.beginTransaction();

			deleteComputersQuery.executeUpdate();

			//			logger.info("Access to the data base : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");

			session.delete(company);

			//			logger.info("Access to the data base : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");

			transaction.commit();

		} catch (EmptyResultDataAccessException e) {
			logger.warn("Delete company failed - no line deleted");

		} catch (DataAccessException e) {
			logger.warn("Query failure : " + SQL_DELETE_COMPUTERS+ " (" + company.getId() + ")");
			logger.warn("Query failure : " + SQL_DELETE_BY_ID+ " (" + company.getId() + ")");
			throw new DAOException("Query failure : " + e);
		}
	}

}
