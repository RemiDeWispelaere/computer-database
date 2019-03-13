package controller;

import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.ComputerDaoImpl;

public class Launcher {

	private DAOFactory daoFactory = DAOFactory.getInstance();
	private ComputerDaoImpl computerDao = (ComputerDaoImpl) this.daoFactory.getComputerDao();
	
	public ComputerController computerController;
	static List<String> messages = new ArrayList<String>();
	
	public Launcher() {
		this.computerController = new ComputerController();
		messages = computerController.executerTests();
	}
	
	public static void main(String[] args) { 

		Launcher launcher = new Launcher();
		
		System.out.println("-_-_-_-_-_START-_-_-_-_-");
		
		
		
		
		System.out.println("-_-_-_-_-_STOP-_-_-_-_-");

	}

}
