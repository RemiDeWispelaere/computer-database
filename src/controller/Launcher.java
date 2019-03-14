package controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			case 1: //List of computers
				printAllComputer();
				break;
			case 2: //List of companies
				printAllCompanies();
				break;
			case 3: //Computer details
				searchComputerDetails();
				break;
			case 4: //Create a new computer
				createNewComputer();
				break;
			case 5: //Update a computer
				updateComputer();
				break;
			case 6: //Delete computer
				deleteComputer();
				break;
			default:
				//	
			}
			
			//PAUSE//
			if(choice != 0) 
				pause();
			
		}while(choice != 0);
		
		
		System.out.println("-_-_-_-_-_STOP-_-_-_-_-");

	}
	
	public static void createNewComputer() {
		//Name
		System.out.print("ENTER THE NAME : ");
		String nName = scanner.nextLine();
		
		//Company id
		int nCompany = askCompanyId();
		
		//Introduced date
		Date nIntroDate = askIntroducedDate();
		//Discontinued date
		Date nDisconDate = askDiscontinuedDate();
		
		computerDao.add(new Computer(0, nName, nCompany, nIntroDate, nDisconDate)); //Id est auto incrementee par le dao
	}
	
	public static void updateComputer() {
		System.out.println("Search which computer you want to update\n");
		Computer cpuToUpdate = searchComputerDetails();
		
		//Name
		System.out.print("ENTER THE NEW NAME (EMPTY TO KEEP THE SAME [" + cpuToUpdate.getName() + "]) : " );
		String nName = scanner.nextLine();
		
		if(nName.isEmpty())
			nName = cpuToUpdate.getName();
		
		//Company id
		int nCompany = askNewCompanyId(cpuToUpdate.getManufacturerId());
		
		//Introduced date
		Date nIntroDate = askNewIntroducedDate(cpuToUpdate.getDateIntroduced());
		//Discontinued date
		Date nDisconDate = askNewDiscontinuedDate(cpuToUpdate.getDateDiscontinued());
		
		computerDao.update(new Computer(cpuToUpdate.getId(), nName, nCompany, nIntroDate, nDisconDate));
		System.out.println(computerDao.findById(cpuToUpdate.getId()));
	}
	
	public static void deleteComputer() {
		System.out.println("Search which computer you want to delete\n");
		Computer cpuToDelete = searchComputerDetails();
		
		printDeleteValidation();
		int choice2 = askChoice(2);
		
		if(choice2 == 1)
			computerDao.delete(cpuToDelete);
	}
	
	public static Computer searchComputerDetails() {
		printComputerQueryMenu();
		int choice2 = askChoice(2);
		
		switch(choice2) {
		case 1://find by Id
			System.out.print("ENTER THE ID : ");
			try {
				int id = Integer.valueOf(scanner.nextLine());
				
				Computer cpu = computerDao.findById(id);
				System.out.println(cpu);
				
				return cpu;
			}catch(NumberFormatException e) {
				System.out.println("INVALID ID (integer only)");
			}
			break;
		case 2://find by Name
			System.out.print("ENTER THE NAME : ");
			String name = scanner.nextLine();
			
			Computer cpu = computerDao.findByName(name);
			System.out.println(cpu);
			
			return cpu;
		default:
			//
		}
		
		return null;
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
	
	public static void printComputerQueryMenu() {
		System.out.println("_____COMPUTER DETAILS MENU_____\n\n"
				+ "[1] Find by Id\n"
				+ "[2] Find by Name\n"
				+ "[0] EXIT\n"
				+ "_______________________\n\n");
	}
	
	public static void printDeleteValidation() {
		System.out.println("_____ARE YOU SUR ?_____\n\n"
				+ "[1] YES, I'M SUR\n"
				+ "[2] CANCEL\n"
				+ "_______________________\n\n");
	}
	
	public static void printAllComputer() {
		for(Computer cpu : computerDao.findAll()) {
			System.out.println(cpu);
		}
	}
	
	public static void printAllCompanies() {
		for(Company company : companyDao.findAll()) {
			System.out.println(company);
		}
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
	
	public static int askCompanyId() {
		String stCompany;
		do {
			System.out.print("ENTER THE MANUFACTURER ID (TYPE [LIST] TO PRINT THE LIST OF MANUFACTURER) : ");
			stCompany = scanner.nextLine();
			 
			//Print the list of all companies in the DB
			if(stCompany.toUpperCase().equals("LIST")) {
				printAllCompanies();
				System.out.println("ENTER THE MANUFACTURER ID : ");
				stCompany = scanner.nextLine();
			}
			
		}while(!validChoice(stCompany, 43)); 
		//TO DO gerer le nombre de company limite dynamiquement
		
		return Integer.valueOf(stCompany);
	}
	
	public static int askNewCompanyId(int currentId) {
		String stCompany;
		do {
			System.out.print("ENTER THE NEW MANUFACTURER ID (TYPE [LIST] TO PRINT THE LIST OF MANUFACTURER | EMPTY TO KEEP THE SAME ["+ currentId +"]) : ");
			stCompany = scanner.nextLine();
			 
			if(stCompany.isEmpty())
				return currentId;
			
			//Print the list of all companies in the DB
			if(stCompany.toUpperCase().equals("LIST")) {
				printAllCompanies();
				System.out.println("ENTER THE MANUFACTURER ID : ");
				stCompany = scanner.nextLine();
			}
			
		}while(!validChoice(stCompany, 43)); 
		//TO DO gerer le nombre de company limite dynamiquement
		
		return Integer.valueOf(stCompany);
	}
	
	public static Date askIntroducedDate() {
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		System.out.print("WHEN WAS THE COMPUTER INTRODUCED ? (FORMAT dd mm yyyy | LET EMPTY IF YOU DO NOT KNOW) : ");
		stDate = scanner.nextLine();
		
		if(stDate.isEmpty())
			return null;
		
		try {
			return new Date(dateFormat.parse(stDate).getTime());
			
		}catch(ParseException e) {
			System.out.println("INVALID DATE");
			return null;
		}
	}
	
	public static Date askNewIntroducedDate(Date currentDate) {
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		System.out.print("WHEN WAS THE COMPUTER INTRODUCED ? (FORMAT dd mm yyyy | EMPTY TO KEEP THE SAME [" + currentDate + "]) : ");
		stDate = scanner.nextLine();
		
		if(stDate.isEmpty())
			return currentDate;
		
		try {
			return new Date(dateFormat.parse(stDate).getTime());
			
		}catch(ParseException e) {
			System.out.println("INVALID DATE");
			return null;
		}
	}
	
	//TO DO verifier que la date est bien posterieur a celle d'introduction
	public static Date askDiscontinuedDate() {
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		System.out.print("WHEN WAS THE COMPUTER DISCONTINUED ? (FORMAT dd mm yyyy | LET EMPTY IF YOU DO NOT KNOW) : ");
		stDate = scanner.nextLine();
		
		if(stDate.isEmpty())
			return null;
		
		try {
			return new Date(dateFormat.parse(stDate).getTime());
			
		}catch(ParseException e) {
			System.out.println("INVALID DATE");
			return null;
		}
	}
	
	public static Date askNewDiscontinuedDate(Date currentDate) {
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
		
		System.out.print("WHEN WAS THE COMPUTER DISCONTINUED ? (FORMAT dd mm yyyy | EMPTY TO KEEP THE SAME [" + currentDate + "]) : ");
		stDate = scanner.nextLine();
		
		if(stDate.isEmpty())
			return currentDate;
		
		try {
			return new Date(dateFormat.parse(stDate).getTime());
			
		}catch(ParseException e) {
			System.out.println("INVALID DATE");
			return null;
		}
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

	////////PAUSE////////
	public static void pause() {
		System.out.println("\n[ENTER TO RETURN TO THE MAIN MENU]");
		scanner.nextLine();
	}
}
