package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import model.Computer;


public class ComputerDaoTest {

	private final String COMPUTER_TEST_NAME = "NANI";
	private DAOFactory daoFactory = DAOFactory.getInstance();
	private ComputerDaoImpl computerDao = (ComputerDaoImpl) daoFactory.getComputerDao();
	
	@After
	public void afterTest() {
		
		Computer computer = computerDao.findByName(COMPUTER_TEST_NAME);
		if(computer != null) 
			computerDao.delete(computer);
	}
	
	@Test
	public void testFindById() {
		
		Computer computer = computerDao.findById(4);
		Computer computerNull = computerDao.findById(600);
		
		assertEquals("CM-5e", computer.getName());
		assertNull(computerNull);
	}
	
	@Test
	public void testFindByName() {
		
		Computer computer = computerDao.findByName("CM-5e");
		Computer computerNull = computerDao.findByName("yyyyyy");
		
		assertEquals(4, computer.getId());
		assertNull(computerNull);
	}
	
	@Test
	public void testFindAll() {
		
		List<Computer> list = computerDao.findAll();
		
		assertTrue(list.size() >= 0);
		assertEquals("CM-5e", list.get(3).getName());
	}

	@Test
	public void testFindAllWithLimit() {
		List<Computer> list = computerDao.findAllWithLimit(10);
		
		assertTrue(list.size() <= 10);
		assertEquals("CM-5e", list.get(3).getName());
	}
	
	@Test
	public void testAdd() {
		
		assertNull(computerDao.findByName(COMPUTER_TEST_NAME)); 
		Computer newComputer = new Computer(0,COMPUTER_TEST_NAME, 1L, null, null);
		
		computerDao.add(newComputer);
		
		Computer computer = computerDao.findByName(COMPUTER_TEST_NAME);		
		assertNotNull(computer);
	}
	
	@Test
	public void testDelete() {
		
		Computer newComputer = new Computer(0,COMPUTER_TEST_NAME, 1L, null, null);
		
		computerDao.add(newComputer);
		computerDao.delete(newComputer);
		
		Computer computerNull = computerDao.findByName(COMPUTER_TEST_NAME);
		assertNull(computerNull);
	}
	
	@Test
	public void testUpdate() {
		
		Computer newComputer = new Computer(0, COMPUTER_TEST_NAME, 1L, null, null);
		
		computerDao.add(newComputer);
		
		Computer computerToUpdate = computerDao.findByName(COMPUTER_TEST_NAME); //obligatoire pour avoir le bon ID
		computerToUpdate.setManufacturerId(3L);
		
		computerDao.update(computerToUpdate);
		
		Computer computer = computerDao.findByName(COMPUTER_TEST_NAME);
		assertEquals((Long) 3L, computer.getManufacturerId());
	}
}
