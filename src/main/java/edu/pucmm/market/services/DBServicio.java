package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.Server;

public class DBServicio {

	private static Server tcpServer;
	private static Server webServer;
	private static final String URL = "jdbc:h2:tcp://localhost/~/Mercado";

	public DBServicio() throws SQLException {
		iniciarDB();
		probarConexion();
	}

	public void iniciarDB() throws SQLException {
		tcpServer = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
		webServer = Server.createWebServer("-webPort", "9090", "-webAllowOthers", "-webDaemon").start();
	}

	public void pararDB() throws SQLException {
		webServer.shutdown();
		tcpServer.shutdown();
	}

	private Connection getConexion() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(URL, "sa", "");
		} catch (SQLException e) {
			Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
		}

		return connection;
	}

	public void probarConexion() {
		try {
			getConexion().close();
		} catch (SQLException e) {
			Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}