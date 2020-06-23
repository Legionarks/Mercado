package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.Server;

public class DBServicio {

    private static final String URL = "jdbc:h2:tcp://localhost/~/Mercado";

    public DBServicio() throws SQLException {
	this.registrarDriver();
	iniciarDB();
	probarConexion();
	crearTablas();
    }

    private void registrarDriver() {
	try {
	    Class.forName("org.h2.Driver");
	} catch (ClassNotFoundException e) {
	    Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
    public static void iniciarDB() throws SQLException {
	Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void pararDB() throws SQLException {
	Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public static Connection getConexion() {
	Connection connection = null;

	try {
	    connection = DriverManager.getConnection(URL, "sa", "");
	} catch (SQLException e) {
	    Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
	}

	return connection;
    }

    public static void probarConexion() {
	try {
	    getConexion().close();
	    System.out.println("Conexion realizada satisfactoriamente...");
	} catch (SQLException e) {
	    Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
    public static void crearTablas() throws SQLException {
	Connection connection = getConexion();
	Statement statement = connection.createStatement();

        String usuario = "CREATE TABLE IF NOT EXISTS USUARIO\n" +
                "(\n" +
                "  USUARIO VARCHAR(50) PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PASSWORD VARCHAR(100) NOT NULL,\n" +
                ");";
        
        String producto = "CREATE TABLE IF NOT EXISTS PRODUCTO\n" +
                "(\n" +
                "  ID_PRODUCTO INT PRIMARY KEY,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PRECIO DECIMAL NOT NULL,\n" +
                ");";
        
        String venta = "CREATE TABLE IF NOT EXISTS VENTA\n" +
                "(\n" +
                "  ID_VENTA BIGINT PRIMARY KEY,\n" +
                "  FECHA_COMPRA DATE NOT NULL,\n" +
                "  NOMBRE_CLIENTE VARCHAR(100) NOT NULL,\n" +
                ");";
        
        String venta_producto = "CREATE TABLE IF NOT EXISTS VENTA_PRODUCTO\n" +
                "(\n" +
                "  ID_VENTA BIGINT,\n" +
                "  ID_PRODUCTO INT,\n" +
                "  CANTIDAD INT NOT NULL,\n" +
                "  FOREIGN KEY (ID_VENTA) REFERENCES VENTA(ID_VENTA),\n" +
                "  FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTO(ID_PRODUCTO),\n" +
                "  PRIMARY KEY(ID_VENTA, ID_PRODUCTO),\n" +
                ");";
        
	statement.execute(usuario);
	statement.execute(producto);
	statement.execute(venta);
	statement.execute(venta_producto);

	statement.close();
	connection.close();
    }
}