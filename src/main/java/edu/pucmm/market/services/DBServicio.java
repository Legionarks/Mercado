package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBServicio {

    private static DBServicio instancia;
    private static final String URL = "jdbc:h2:tcp://localhost/~/Mercado";

    private DBServicio() {
	registrarDriver();
    }

    public static DBServicio getInstancia() {
	if (instancia == null) {
	    instancia = new DBServicio();
	}
	return instancia;
    }

    private void registrarDriver() {
	try {
	    Class.forName("org.h2.Driver");
	} catch (ClassNotFoundException e) {
	    Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, e);
	}
    }

    public Connection getConexion() {
	Connection connection = null;

	try {
	    connection = DriverManager.getConnection(URL, "sa", "");
	} catch (SQLException e) {
	    Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, e);
	}

	return connection;
    }

    public void probarConexion() {
	try {
	    this.getConexion().close();
	    System.out.println("Conexion realizada satisfactoriamente...");
	} catch (SQLException e) {
	    Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, e);
	}
    }
}