package com.rdewispelaere.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DAOUtilitaire {

    public default void closeConnections( ResultSet resultSet ) {
        if ( resultSet != null ) {
            try {
                resultSet.close();
            } catch ( SQLException e ) {
                System.out.println( "Ã‰chec de la fermeture du ResultSet : " + e.getMessage() );
            }
        }
    }

    public default void closeConnections( Statement statement ) {
        if ( statement != null ) {
            try {
                statement.close();
            } catch ( SQLException e ) {
                System.out.println( "Ã‰chec de la fermeture du Statement : " + e.getMessage() );
            }
        }
    }

    public default void closeConnections( Connection connexion ) {
        if ( connexion != null ) {
            try {
                connexion.close();
            } catch ( SQLException e ) {
                System.out.println( "Ã‰chec de la fermeture de la connexion : " + e.getMessage() );
            }
        }
    }

    public default void closeConnections( Statement statement, Connection connexion ) {
        closeConnections( statement );
        closeConnections( connexion );
    }

    public default void closeConnections( ResultSet resultSet, Statement statement, Connection connexion ) {
        closeConnections( resultSet );
        closeConnections( statement );
        closeConnections( connexion );
    }

    public default PreparedStatement initPreparedStatement( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
}