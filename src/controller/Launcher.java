package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import dao.DAOFactory;
import model.Company;
import model.Computer;
import dao.CompanyDaoImpl;
import dao.ComputerDaoImpl;
import controller.Cli;

public class Launcher {

	static DAOFactory daoFactory = DAOFactory.getInstance();
	static ComputerDaoImpl computerDao = (ComputerDaoImpl) daoFactory.getComputerDao();
	static CompanyDaoImpl companyDao = (CompanyDaoImpl) daoFactory.getCompanyDao();
	
	static Scanner scanner = new Scanner(System.in);
	
	public Launcher() {
		
	}
	
	public static void main(String[] args) throws org.apache.commons.cli.ParseException{ 
		
		System.out.println("-_-_-_-_-_INIT-_-_-_-_-");
		 
		//////////////////////////////////////////////////////////////
		/*CLI par ligne de commande
		  * A retirer une fois l'interface d'utilisation finie
		  */
		////////CLI////////
		/*final Options options = Cli.configParameters();
		final CommandLineParser parser = new DefaultParser();
		final CommandLine line = parser.parse(options, args);
		
		//Table name option
		String tableName = line.getOptionValue("table", "computer");
		
		//Query type option
		char queryType = line.getOptionValue("querytype", "s").charAt(0);
		
		//Name argument for the query
		String nameQuery = "";
		if(line.hasOption("nameArg")) {
			nameQuery = line.getOptionValue("nameArg");
		}
		
		//Limit option
		int limitNumber;
		if(line.hasOption("limit")) {
			try {
				limitNumber = Integer.valueOf(line.getOptionValue("limit"));
			}catch(Exception e) {
				System.out.println("Invalid argument for the limit option, integer was expected");
			}
		}*/
		///////////////////////////////////////////////////////////////////////////////////
		
		int choice;
		
		System.out.println("-_-_-_-_-_START-_-_-_-_-");
		
		do {
			printMenu();
			choice = askChoice(6);
			
			switch(choice) {
			case 1:
				for(Computer cpu : computerDao.findAll()) {
					System.out.println(cpu);
				}
				break;
			case 2:
				for(Company company : companyDao.findAll()) {
					System.out.println(company);
				}
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			default:
				//	
			}
		}while(choice != 0);
		
		
		System.out.println("-_-_-_-_-_STOP-_-_-_-_-");

	}
	
	////////PRINTS////////
	public static void printMenu() {
		System.out.println("_____MAIN MENU_____\n\n"
				+ "[1] List of computers\n"
				+ "[2] List of companies\n"
				+ "[3] Show computer details\n"
				+ "[4] Create a new computer\n"
				+ "[5] Update a computers\n"
				+ "[6] Delete a computers\n\n"
				+ "[0] EXIT\n"
				+ "_______________________\n\n");
	}
	
	
	////////CHOICE////////
	
	public static int askChoice(int upper) {
		System.out.print("CHOICE : ");
		String choice = scanner.nextLine();
		
		while(!validChoice(choice, upper)) {
			System.out.print("CHOICE : ");
			choice = scanner.nextLine();
		}
		
		return Integer.valueOf(choice);
	}
	
	public static boolean validChoice(String choice, int upper) {
		int iChoice;
		
		try{
			iChoice = Integer.valueOf(choice);
		}catch (Exception e){
			System.out.println("INVALID CHOICE (only integer)");
			return false;
		}
		
		if(iChoice >= 0 && iChoice <= upper)
			return true;
		else {
			System.out.println("INVALID CHOICE (out of limit)");
			return false;
		}
	}

}
