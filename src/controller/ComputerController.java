package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.Computer;

public class ComputerController {

	private List<Computer> queryResult;
	private List<String> messages;
	
	/////////CONSTRUCTORS//////////////
	
	public ComputerController() {
		this.queryResult = null;
		this.messages = new ArrayList<String>();
	}
	
	/////////////TEST///////////
	
	public List<String> executerTests() {
	    /* Chargement du driver JDBC pour MySQL */
	    try {
	        messages.add( "Chargement du driver..." );
	        Class.forName( "com.mysql.jdbc.Driver" );
	        messages.add( "Driver chargé !" );
	    } catch ( ClassNotFoundException e ) {
	        messages.add( "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
	                + e.getMessage() );
	    }

	    /* Connexion à la base de données */
	    String url = "jdbc:mysql://localhost:3306/computer-database-db";
	    String utilisateur = "admincdb";
	    String motDePasse = "qwerty1234";
	    
	    Connection connexion = null;
	    Statement statement = null;
	    ResultSet resultat = null;
	    try {
	        messages.add( "Connexion à la base de données..." );
	        connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
	        messages.add( "Connexion réussie !" );

	        /* Création de l'objet gérant les requêtes */
	        statement = connexion.createStatement();
	        messages.add( "Objet requête créé !" );

	        /* Exécution d'une requête de lecture */
	        resultat = statement.executeQuery( "SELECT id, name FROM computer;" );
	        messages.add( "Requête \"SELECT id, name FROM computer;\" effectuée !" );
	 
	        /* Récupération des données du résultat de la requête de lecture */
	        while ( resultat.next() ) {
	            int idCompany = resultat.getInt( "id" );
	            String nameCompany = resultat.getString( "name" );
	            /* Formatage des données pour affichage dans la JSP finale. */
	            messages.add( "Données retournées par la requête : id = " + idCompany + ", name = " +
	            		 nameCompany + ".");
	        }
	    } catch ( SQLException e ) {
	        messages.add( "Erreur lors de la connexion : <br/>"
	                + e.getMessage() );
	    } finally {
	        messages.add( "Fermeture de l'objet ResultSet." );
	        if ( resultat != null ) {
	            try {
	                resultat.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        messages.add( "Fermeture de l'objet Statement." );
	        if ( statement != null ) {
	            try {
	                statement.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	        messages.add( "Fermeture de l'objet Connection." );
	        if ( connexion != null ) {
	            try {
	                connexion.close();
	            } catch ( SQLException ignore ) {
	            }
	        }
	    }

	    return messages;
	}
}
