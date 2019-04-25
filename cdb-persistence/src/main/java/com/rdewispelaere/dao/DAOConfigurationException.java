package com.rdewispelaere.dao;

public class DAOConfigurationException extends RuntimeException {

	////////CONSTRUCTORS////////

	private static final long serialVersionUID = 1L;

	public DAOConfigurationException( String message ) {
        super( message );
    }

    public DAOConfigurationException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DAOConfigurationException( Throwable cause ) {
        super( cause );
    }
}
