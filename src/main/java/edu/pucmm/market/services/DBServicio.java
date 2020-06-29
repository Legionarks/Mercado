package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.Server;

public class DBServicio {

    private static Server server;
    private static final String URL = "jdbc:h2:tcp://localhost/~/Mercado";

    public DBServicio() throws SQLException {
	this.registrarDriver();
	this.iniciarDB();
	probarConexion();
    }

    private void registrarDriver() {
	try {
	    Class.forName("org.h2.Driver");
	} catch (ClassNotFoundException e) {
	    Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
    public void iniciarDB() throws SQLException {
	server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
    }

    public void pararDB() throws SQLException {
	server.shutdown();
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
	} catch (SQLException e) {
	    Logger.getLogger(DBServicio.class.getName()).log(Level.SEVERE, null, e);
	}
    }
    
    public void crearTablas() throws SQLException {
	Connection connection = getConexion();
	Statement statement = connection.createStatement();

        String usuario = "CREATE TABLE IF NOT EXISTS USUARIO\n" +
                "(\n" +
                "  ID_USUARIO VARCHAR(50) PRIMARY KEY,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PASSWORD VARCHAR(100) NOT NULL\n" +
                ");";
        
        String producto = "CREATE TABLE IF NOT EXISTS PRODUCTO\n" +
                "(\n" +
                "  ID_PRODUCTO INT PRIMARY KEY,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PRECIO DECIMAL NOT NULL\n" +
                ");";
        
        String producto_comprado = "CREATE TABLE IF NOT EXISTS PRODUCTO_COMPRADO\n" +
                "(\n" +
                "  ID_PRODUCTO INT PRIMARY KEY,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  PRECIO DECIMAL NOT NULL\n" +
                ");";
        
        String venta = "CREATE TABLE IF NOT EXISTS VENTA\n" +
                "(\n" +
                "  ID_VENTA BIGINT PRIMARY KEY,\n" +
                "  FECHA_COMPRA DATE NOT NULL,\n" +
                "  NOMBRE_CLIENTE VARCHAR(100) NOT NULL\n" +
                ");";
        
        String venta_producto = "CREATE TABLE IF NOT EXISTS VENTA_PRODUCTO\n" +
                "(\n" +
                "  ID_VENTA BIGINT,\n" +
                "  ID_PRODUCTO INT,\n" +
                "  CANTIDAD INT NOT NULL,\n" +
                "  FOREIGN KEY (ID_VENTA) REFERENCES VENTA(ID_VENTA),\n" +
                "  FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTO_COMPRADO(ID_PRODUCTO),\n" +
                "  PRIMARY KEY(ID_VENTA, ID_PRODUCTO)\n" +
                ");";
        
	statement.execute(usuario);
	statement.execute(producto);
	statement.execute(producto_comprado);
	statement.execute(venta);
	statement.execute(venta_producto);

	statement.close();
	connection.close();
    }
    
    public void borrarTablas() throws SQLException {
	Connection connection = getConexion();
	Statement statement = connection.createStatement();

        String usuario = "DROP TABLE IF EXISTS USUARIO;";
        String producto = "DROP TABLE IF EXISTS PRODUCTO;";
        String venta_producto = "DROP TABLE IF EXISTS VENTA_PRODUCTO;";
        String producto_comprado = "DROP TABLE IF EXISTS PRODUCTO_COMPRADO;";
        String venta = "DROP TABLE IF EXISTS VENTA;";
        
	statement.execute(usuario);
	statement.execute(producto);
	statement.execute(venta_producto);
	statement.execute(producto_comprado);
	statement.execute(venta);

	statement.close();
	connection.close();
    }    
}