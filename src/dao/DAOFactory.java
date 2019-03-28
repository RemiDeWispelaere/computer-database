package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {

	private static final String FICHIER_PROPERTIES       = "dao.properties";
	
	private HikariDataSource hikariDb;

	public DAOFactory(HikariDataSource db) {
		super();
		this.hikariDb = db;
	}

	/*
	 * Méthode chargée de récupérer les informations de connexion à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException {

		Properties properties = new Properties();
		String url;
		String nomUtilisateur;
		String motDePasse;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

		if ( fichierProperties == null ) {
			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
		}

		try {
			properties.load( fichierProperties );
			url = properties.getProperty("url");
			nomUtilisateur = properties.getProperty("nomUtilisateur");
			motDePasse = properties.getProperty("motDePasse");
		} catch ( IOException e ) {
			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
		}

		try {
			Class.forName( properties.getProperty("driver"));
		} catch ( ClassNotFoundException e ) {
			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(nomUtilisateur);
		hikariConfig.setPassword(motDePasse);

		return new DAOFactory(new HikariDataSource(hikariConfig));
	}

	Connection getConnection() throws SQLException {
		return hikariDb.getConnection();
	}

	public ComputerDao getComputerDao() {
		return new ComputerDao( this );
	}

	public CompanyDao getCompanyDao() {
		return new CompanyDao(this);
	}
}