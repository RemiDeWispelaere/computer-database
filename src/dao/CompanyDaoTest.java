package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import model.Company;

public class CompanyDaoTest {

	private DAOFactory daoFactory = DAOFactory.getInstance();
	private CompanyDaoImpl companyDao = (CompanyDaoImpl) daoFactory.getCompanyDao();
	
	@Test
	public void testFindAll() {
		
		List<Company> list = companyDao.findAll();
		
		assertTrue(list.size() >= 0);
		assertEquals("Netronics", list.get(3).getName());
	}

	@Test
	public void testFindAllWithLimit() {
		List<Company> list = companyDao.findAllWithLimit(10);
		
		assertTrue(list.size() <= 10);
		assertEquals("Netronics", list.get(3).getName());
	}
}
