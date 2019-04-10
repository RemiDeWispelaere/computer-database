package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Repository("daoFactory")
@Scope("singleton")
public class DAOFactory {

//	private static final String FICHIER_PROPERTIES       = "dao.properties";
	
	@Autowired
	private HikariDataSource hikariDb;

//	public DAOFactory(HikariDataSource db) {
//		super();
//		this.hikariDb = db;
//	}

	/*
	 * Méthode chargée de récupérer les informations de connexion à la base de
	 * données, charger le driver JDBC et retourner une instance de la Factory
	 */
//	public static DAOFactory getInstance() throws DAOConfigurationException {
//
//		Properties properties = new Properties();
//		String url;
//		String nomUtilisateur;
//		String motDePasse;
//		String autoCommit;
//
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );
//
//		if ( fichierProperties == null ) {
//			throw new DAOConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
//		}
//
//		try {
//			properties.load( fichierProperties );
//			url = properties.getProperty("url");
//			nomUtilisateur = properties.getProperty("nomUtilisateur");
//			motDePasse = properties.getProperty("motDePasse");
//			autoCommit = properties.getProperty("autoCommit");
//			
//		} catch ( IOException e ) {
//			throw new DAOConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
//		}
//
//		try {
//			Class.forName( properties.getProperty("driver"));
//		} catch ( ClassNotFoundException e ) {
//			throw new DAOConfigurationException( "Le driver est introuvable dans le classpath.", e );
//		}
//
//		HikariConfig hikariConfig = new HikariConfig();
//		hikariConfig.setJdbcUrl(url);
//		hikariConfig.setUsername(nomUtilisateur);
//		hikariConfig.setPassword(motDePasse);
//		hikariConfig.setAutoCommit(autoCommit.equals("false") ? false : true);
//
//		return new DAOFactory(new HikariDataSource(hikariConfig));
//	}

	Connection getConnection() throws SQLException {
		return hikariDb.getConnection();
	}
}