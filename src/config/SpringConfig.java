package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dao.DAOConfigurationException;

@Configuration
@ComponentScan("dao")
@ComponentScan("dto")
@ComponentScan("service")
public class SpringConfig {

	private static final String FICHIER_PROPERTIES = "dao.properties";

	@Bean
	public HikariDataSource getDataSource() {
		Properties properties = new Properties();
		String url;
		String nomUtilisateur;
		String motDePasse;
		String autoCommit;

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
			autoCommit = properties.getProperty("autoCommit");

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
		hikariConfig.setAutoCommit(autoCommit.equals("false") ? false : true);
		
		return new HikariDataSource(hikariConfig);
	}
}
