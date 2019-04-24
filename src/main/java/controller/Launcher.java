/**
 * @author DE WISPELAERE Rémi
 */
package main.java.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.java.config.SpringConfig;
import main.java.model.Company;
import main.java.model.Computer;
import main.java.model.PageManager;
import main.java.dao.CompanyDao;
import main.java.dao.ComputerDao;

public class Launcher {

	static ComputerDao computerDao;
	static CompanyDao companyDao;
	
	static Scanner scanner = new Scanner(System.in);
	static Logger logger = Logger.getLogger(Launcher.class);
	
	static int nbCompanies;
	
	enum MainChoice{
		EXIT, ALL_COMPUTERS, ALL_COMPANIES, DETAILS, CREATE, UPDATE, DELETE, DELETE_COMPANY;
	}
	
	enum QueryChoice{
		BY_ID, BY_NAME;
	}
	
	enum PageAction{
		STOP, NEXT, PREV;
	}

	public static void main(String[] args){ 

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		computerDao = applicationContext.getBean("computerDao", ComputerDao.class);
		companyDao = applicationContext.getBean("companyDao", CompanyDao.class);

		nbCompanies = companyDao.findAll().size();
		
		logger.info("START");
		System.out.println("-_-_-_-_START_-_-_-_-");
		
		//Menu principal
		MainChoice choice = MainChoice.EXIT;
		do {
			printMenu();
			choice = MainChoice.values()[askChoice(7)];

			switch(choice) {
			case ALL_COMPUTERS: //List of computers
				printAllComputers();
				break;
			case ALL_COMPANIES: //List of companies
				printAllCompanies();
				break;
			case DETAILS: //Computer details
				searchComputerDetails();
				break;
			case CREATE: //Create a new computer
				createNewComputer();
				break;
			case UPDATE: //Update a computer
				updateComputer();
				break;
			case DELETE: //Delete computer
				deleteComputer();
				break;
			case DELETE_COMPANY: //Delete company and computers related
				deleteCompany();
				break;
			default:
				//	
			}

			//PAUSE//
			if(choice != MainChoice.EXIT) 
				pause();

		}while(choice != MainChoice.EXIT);

		logger.info("STOP");
		System.out.println("-_-_-_-_STOP_-_-_-_-");
		
		applicationContext.close();
	}

	/**
	 * Add a new computer to the DB
	 */
	public static void createNewComputer() {
		logger.info("create a new computer");
		//Name
		System.out.print("ENTER THE NAME : ");
		String nName = scanner.nextLine();

		//Company id
		Long nCompany = askCompanyId();

		//Introduced date
		Optional<Date> nIntroDate = askIntroducedDate();
		//Discontinued date
		Optional<Date> nDisconDate = askDiscontinuedDate();

		//Id est auto incrementee par le dao
		Computer computer = new Computer.ComputerBuilder()
				.withName(nName)
				.withCompanyId(nCompany)
				.withIntroducedDate(nIntroDate.orElse(null))
				.withDiscontinuedDate(nDisconDate.orElse(null))
				.build();
		computerDao.add(computer);
	}

	/**
	 * Modify a computer's details
	 */
	public static void updateComputer() {
		logger.info("update computer");
		System.out.println("Search which computer you want to update\n");
		Computer cpuToUpdate = searchComputerDetails().orElseThrow(IllegalArgumentException::new);

		//Name
		System.out.print("ENTER THE NEW NAME (EMPTY TO KEEP THE SAME [" + cpuToUpdate.getName() + "]) : " );
		String nName = scanner.nextLine();

		if(nName.isEmpty())
			nName = cpuToUpdate.getName();

		//Company id
		Long nCompany = askNewCompanyId(cpuToUpdate.getCompanyId());

		//Introduced date
		Optional<Date> nIntroDate = askNewIntroducedDate(cpuToUpdate.getIntroducedDate());
		//Discontinued date
		Optional<Date> nDisconDate = askNewDiscontinuedDate(cpuToUpdate.getDiscontinuedDate());

		Computer computer = new Computer.ComputerBuilder()
				.withId(cpuToUpdate.getId())
				.withName(nName)
				.withCompanyId(nCompany)
				.withIntroducedDate(nIntroDate.orElse(null))
				.withDiscontinuedDate(nDisconDate.orElse(null))
				.build();
		computerDao.update(computer);
		System.out.println(computerDao.findById(cpuToUpdate.getId()));
	}

	/**
	 * Delete a computer of the DB
	 */
	public static void deleteComputer() {
		logger.info("delete computer");
		System.out.println("Search which computer you want to delete\n");
		Computer cpuToDelete = searchComputerDetails().orElseThrow(IllegalArgumentException::new);

		printDeleteValidation();
		int choice2 = askChoice(2);

		if(choice2 == 1)
			computerDao.delete(cpuToDelete);
	}

	public static void deleteCompany() {
		logger.info("delete company and computers related");
		System.out.println("Search which company you want to delete\n");
		Company companyToDelete = searchCompanyDetails().orElseThrow(IllegalArgumentException::new);
		
		printDeleteValidation();
		int choice2 = askChoice(2);
		
		if(choice2 == 1)
			companyDao.deleteCompany(companyToDelete);
	}
	
	/**
	 * Get a computer (search by Id or by Name)
	 * 
	 * @return the computer found by the search
	 */
	public static Optional<Computer> searchComputerDetails() {
		logger.info("computer details");
		printComputerQueryMenu();
		QueryChoice choice = QueryChoice.values()[askChoice(2) - 1];

		switch(choice) {
		case BY_ID://find by Id
			System.out.print("ENTER THE ID : ");
			try {
				int id = Integer.valueOf(scanner.nextLine());
				Optional<Computer> cpuOpt = computerDao.findById(id);
				Computer cpu = null;
				
				if(cpuOpt.isPresent()) {
					cpu = cpuOpt.get();
					System.out.println(cpu);
				}

				return Optional.ofNullable(cpu);
			}catch(NumberFormatException e) {
				System.out.println("INVALID ID (integer only)");
			}
			break;
		case BY_NAME://find by Name
			System.out.print("ENTER THE NAME : ");
			String name = scanner.nextLine();

			Optional<Computer> cpuOpt = computerDao.findByName(name);
			Computer cpu = null;
			
			if(cpuOpt.isPresent()) {
				cpu = cpuOpt.get();
				System.out.println(cpu);
			}

			return Optional.ofNullable(cpu);
		default:
			//
		}

		return Optional.empty();
	}
	
	public static Optional<Company> searchCompanyDetails(){
		logger.info("company details");
		printCompanyQueryMenu();
		QueryChoice choice = QueryChoice.values()[askChoice(2) - 1];

		switch(choice) {
		case BY_ID://find by Id
			System.out.print("ENTER THE ID : ");
			try {
				Long id = Long.valueOf(scanner.nextLine());
				Optional<Company> companyOpt = companyDao.findById(id);
				Company company = null;
				
				if(companyOpt.isPresent()) {
					company = companyOpt.get();
					System.out.println(company);
				}

				return Optional.ofNullable(company);
			}catch(NumberFormatException e) {
				System.out.println("INVALID ID (integer only)");
			}
			break;
		case BY_NAME://find by Name
			System.out.print("ENTER THE NAME : ");
			String name = scanner.nextLine();

			Optional<Company> companyOpt = companyDao.findByName(name);
			Company company = null;
			
			if(companyOpt.isPresent()) {
				company = companyOpt.get();
				System.out.println(company);
			}

			return Optional.ofNullable(company);
		default:
			//
		}

		return Optional.empty();
	}

	////////PRINTS////////
	/**
	 * Print the main menu on the console (6 options)
	 */
	public static void printMenu() {
		System.out.println("_____MAIN MENU_____\n\n"
				+ "[1] List of computers\n"
				+ "[2] List of companies\n"
				+ "[3] Show computer details\n"
				+ "[4] Create a new computer\n"
				+ "[5] Update a computers\n"
				+ "[6] Delete a computers\n"
				+ "[7] Delete a company\n\n"
				+ "[0] EXIT\n"
				+ "_______________________\n\n");
	}

	/**
	 * Print the difference query options on the console to search for a computer (2 options)
	 */
	public static void printComputerQueryMenu() {
		System.out.println("_____COMPUTER DETAILS MENU_____\n\n"
				+ "[1] Find by Id\n"
				+ "[2] Find by Name\n"
				+ "[0] EXIT\n"
				+ "_______________________\n\n");
	}
	
	/**
	 * Print the difference query options on the console to search for a company (2 options)
	 */
	public static void printCompanyQueryMenu() {
		System.out.println("_____COMPANY DETAILS MENU_____\n\n"
				+ "[1] Find by Id\n"
				+ "[2] Find by Name\n"
				+ "[0] EXIT\n"
				+ "_______________________\n\n");
	}

	/**
	 * Print the validation menu of a deletion (2 options)
	 */
	public static void printDeleteValidation() {
		System.out.println("_____ARE YOU SUR ?_____\n\n"
				+ "[1] YES, I'M SUR\n"
				+ "[2] CANCEL\n"
				+ "_______________________\n\n");
	}

	/**
	 * Print the list of all the computers in the DB
	 * The list is printed on many pages if needed
	 * A DAO is used for the request
	 */
	public static void printAllComputers() {
		logger.info("print all computers");
		List<Computer> allComputers = computerDao.findAll();
		PageManager<Computer> pageManager = new PageManager<>(allComputers);
		Optional<PageAction> choice;

		do {
			pageManager.printCurrentPage();
			pageManager.printPageActions();
			choice = askPageAction();

			switch(choice.get()) {
			case NEXT:
				pageManager.next();
				break;
			case PREV:
				pageManager.previous();
				break;
			default:
				//
			}

		}while(!choice.get().equals(PageAction.STOP));
	}

	/**
	 * Print the list of all the companies in the DB
	 * The list is printed on many pages if needed
	 * A DAO is used for the request
	 */
	public static void printAllCompanies() {
		logger.info("print all companies");
		List<Company> allCompanies = companyDao.findAll();
		PageManager<Company> pageManager = new PageManager<>(allCompanies);
		Optional<PageAction> choice;

		do {
			pageManager.printCurrentPage();
			pageManager.printPageActions();
			choice = askPageAction();

			switch(choice.get()) {
			case NEXT:
				pageManager.next();
				break;
			case PREV:
				pageManager.previous();
				break;
			default:
				//
			}

		}while(!choice.get().equals(PageAction.STOP));
	}

	////////CHOICE////////

	/**
	 * Ask the user to take a decision, mainly used after the print of a menu
	 * 
	 * @param max The maximum value possible of the current menu
	 * @return A valid decision
	 */
	public static int askChoice(int max) {
		System.out.print("CHOICE : ");
		String choice = scanner.nextLine();

		while(!validChoice(choice, max)) {
			System.out.print("CHOICE : ");
			choice = scanner.nextLine();
		}

		logger.info("user chosen : " + choice); 
		return Integer.valueOf(choice);
	}

	/**
	 * Ask the user to take a decision about the current page, used after the print of a page
	 * 
	 * @return A valid decision (NEXT || PREV || STOP)
	 */
	public static Optional<PageAction> askPageAction() {
		System.out.print("ACTION : ");
		String choice = scanner.nextLine();

		while(!validPageAction(choice)) {
			System.out.print("ACTION : ");
			choice = scanner.nextLine();
		}

		if(choice.isEmpty() || choice.toUpperCase().equals("NEXT")) {
			logger.info("user page action : " + PageAction.NEXT);
			return Optional.ofNullable(PageAction.NEXT);
		}
		else if(choice.toUpperCase().equals("PREV") || choice.toUpperCase().equals("PREVIOUS")) {
			logger.info("user page action : " + PageAction.PREV);
			return Optional.ofNullable(PageAction.PREV);
		}
		else if(choice.toUpperCase().equals("STOP") || choice.toUpperCase().equals("0")) {
			logger.info("user page action : " + PageAction.STOP);
			return Optional.ofNullable(PageAction.STOP);
		}
		
		logger.info("user page action : wrong choice");
		return Optional.empty();
	}

	/**
	 * Ask the user to give a company's id, used during the creation of a new computer 
	 * @return A valid company Id enter by the user
	 */
	public static Long askCompanyId() {
		logger.info("ask company id (new computer)");
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

		}while(!validChoice(stCompany, nbCompanies)); 

		logger.info("company id chosen (new computer) : " + stCompany);
		return Long.valueOf(stCompany);
	}

	/**
	 * Ask the user to give a company's id, used during the update of an existing computer
	 * 
	 * @param currentId The company's Id of the computer the user is updating
	 * @return A valid company Id enter by the user
	 */
	public static Long askNewCompanyId(Long currentId) {
		logger.info("ask company id (update computer)");
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

		}while(!validChoice(stCompany, nbCompanies));

		logger.info("company id chosen (update computer) : " + stCompany);
		return Long.valueOf(stCompany);
	}

	/**
	 * Ask the user to give the date of the introduce of the new computer, used during the creation of a new computer
	 * 
	 * @return A valid Date for the new computer (can be null)
	 */
	public static Optional<Date> askIntroducedDate() {
		logger.info("ask introduce date (new computer)");
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

		System.out.print("WHEN WAS THE COMPUTER INTRODUCED ? (FORMAT dd mm yyyy | LET EMPTY IF YOU DO NOT KNOW) : ");
		stDate = scanner.nextLine();

		if(stDate.isEmpty()) {
			logger.info("introduce date chosen (new computer) : none");
			return Optional.empty();
		}
			

		try {
			logger.info("introduce date chosen (new computer) : " + stDate);
			return Optional.ofNullable(new Date(dateFormat.parse(stDate).getTime()));

		}catch(ParseException e) {
			logger.info("introduce date chosen (new computer) : invalid entry");
			System.out.println("INVALID DATE");
			return Optional.empty();
		}
	}

	/**
	 * Ask the user to give the date of the introduce of a computer, used during the update of a computer
	 * 
	 * @param currentDate The introduced date of the computer the user is updating
	 * @return A valid Date for the computer (can be null)
	 */
	public static Optional<Date> askNewIntroducedDate(Optional<Date> currentDate) {
		logger.info("ask introduce date (update computer)");
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

		System.out.print("WHEN WAS THE COMPUTER INTRODUCED ? (FORMAT dd mm yyyy | EMPTY TO KEEP THE SAME [" + currentDate + "]) : ");
		stDate = scanner.nextLine();

		if(stDate.isEmpty()) {
			logger.info("introduce date chosen (update computer) : none");
			return currentDate;
		}
			

		try {
			logger.info("introduce date chosen (update computer) : " + stDate);
			return Optional.of(new Date(dateFormat.parse(stDate).getTime()));

		}catch(ParseException e) {
			logger.info("introduce date chosen (update computer) : invalid entry");
			System.out.println("INVALID DATE");
			return Optional.empty();
		}
	}

	/**
	 * Ask the user to give the date of the discontinue of the new computer, used during the creation of a new computer
	 * 
	 * @return A valid discontinued date for the new computer (can be null | Warning : can be prior to the introduced date)
	 */
	//TODO verifier que la date est bien posterieur a celle d'introduction
	public static Optional<Date> askDiscontinuedDate() {
		logger.info("ask discontinue date (new computer)");
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

		System.out.print("WHEN WAS THE COMPUTER DISCONTINUED ? (FORMAT dd mm yyyy | LET EMPTY IF YOU DO NOT KNOW) : ");
		stDate = scanner.nextLine();

		if(stDate.isEmpty()) {
			logger.info("discontue date chosen (new computer) : none");
			return Optional.empty();
		}
			

		try {
			logger.info("discontinue date chosen (new computer) : " + stDate);
			return Optional.of(new Date(dateFormat.parse(stDate).getTime()));

		}catch(ParseException e) {
			logger.info("discontinue date chosen (new computer) : invalid entry");
			System.out.println("INVALID DATE");
			return Optional.empty();
		}
	}

	/**
	 * Ask the user to give the date of the discontinue of a computer, used during the update of a computer
	 * 
	 * @param currentDate The discontinued date of the computer the user is updating
	 * @return A valid discontinued date for the computer (can be null | Warning : can be prior to the introduced date)
	 */
	public static Optional<Date> askNewDiscontinuedDate(Optional<Date> currentDate) {
		logger.info("ask discontinue date (update computer)");
		String stDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");

		System.out.print("WHEN WAS THE COMPUTER DISCONTINUED ? (FORMAT dd mm yyyy | EMPTY TO KEEP THE SAME [" + currentDate + "]) : ");
		stDate = scanner.nextLine();

		if(stDate.isEmpty()) {
			logger.info("discontinue date chosen (update computer) : none");
			return currentDate;
		}

		try {
			logger.info("discontinue date chosen (update computer) : " + stDate);
			return Optional.of(new Date(dateFormat.parse(stDate).getTime()));

		}catch(ParseException e) {
			logger.info("discontinue date chosen (update computer) : invalid entry");
			System.out.println("INVALID DATE");
			return Optional.empty();
		}
	}

	/**
	 * Called to check if the user's choice is valid
	 * 
	 * @param choice The choice input by the user
	 * @param max The maximum value possible of the current menu
	 * @return true when the choice is valid, false if it's not
	 */
	public static boolean validChoice(String choice, int max) {
		int iChoice;

		try{
			iChoice = Integer.valueOf(choice);
		}catch (Exception e){
			System.out.println("INVALID CHOICE (only integer)");
			return false;
		}

		if(iChoice >= 0 && iChoice <= max)
			return true;
		else {
			System.out.println("INVALID CHOICE (out of limit)");
			return false;
		}
	}

	/**
	 * Called to check if the user's action is valid while navigating between pages
	 * @param choice The choice input by the user
	 * @return true if the input is valid, false if it's not
	 */
	public static boolean validPageAction(String choice) {
		if(choice.isEmpty() || choice.toUpperCase().equals("NEXT") ||
				choice.toUpperCase().equals("PREV") || choice.toUpperCase().equals("PREVIOUS") ||
				choice.toUpperCase().equals("STOP") || choice.toUpperCase().equals("0"))
			return true;
		else {
			System.out.println("INVALID ACTION");
			return false;
		}
	}

	/**
	 * Wait an input before continue the execution 
	 */
	////////PAUSE////////
	public static void pause() {
		System.out.println("\n[ENTER TO RETURN TO THE MAIN MENU]");
		scanner.nextLine();
	}
}
