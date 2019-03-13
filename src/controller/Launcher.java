package controller;

import java.util.ArrayList;
import java.util.List;

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
	
	public Launcher() {
		
	}
	
	public static void main(String[] args) throws org.apache.commons.cli.ParseException{ 
		
		System.out.println("-_-_-_-_-_INIT-_-_-_-_-");
		
		////////CLI////////
		final Options options = Cli.configParameters();
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
		}
		
		System.out.println("-_-_-_-_-_START-_-_-_-_-");
		
		System.out.println(computerDao.findByName(nameQuery));
		
		System.out.println("-_-_-_-_-_STOP-_-_-_-_-");

	}

}
