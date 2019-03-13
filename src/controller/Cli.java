package controller;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Cli {

	public static Options configParameters() {
		
		final Option tableOption = Option.builder("t")
				.longOpt("table")
				.desc("Table name to use")
				.hasArg(true)
				.argName("tableName")
				.required(false)
				.build();
		
		final Option queryTypeOption = Option.builder("q")
				.longOpt("querytype")
				.desc("Type of the current query : SELECT (s) | INSERT (i) | UPDATE (u) | DELETE (d)")
				.hasArg(true)
				.argName("type")
				.required(false)
				.build();
		
		final Option nameArgumentOption = Option.builder("n")
				.longOpt("nameArg")
				.desc("name argument of the query")
				.hasArg(true)
				.argName("name")
				.required(false)
				.build();
		
		final Option limitOption = Option.builder("l")
				.longOpt("limit")
				.desc("LIMIT argument of the current query")
				.hasArg(true)
				.argName("limitNumber")
				.required(false)
				.build();
		
		final Options options = new Options();
		options.addOption(tableOption);
		options.addOption(queryTypeOption);
		options.addOption(nameArgumentOption);
		options.addOption(limitOption);
		
		return options;
	}
	
}
