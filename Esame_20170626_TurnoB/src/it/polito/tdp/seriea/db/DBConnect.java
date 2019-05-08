package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class DBConnect {

	private static String jdbcURL = "jdbc:mysql://localhost/serie_a?user=root";

	private static Connection connection;

	public static Connection getConnection() {

		try {
			if (connection == null || connection.isClosed())
				connection = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;

	}

}
