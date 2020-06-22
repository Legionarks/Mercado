package edu.pucmm.market.services;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BootStrapServices {

    public static void startDB() throws SQLException {
	Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void stopDB() throws SQLException {
	Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public static void crearTablas() throws SQLException {
	Connection connection = DBServicio.getInstancia().getConexion();
	Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
                "(\n" +
                "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  APELLIDO VARCHAR(100) NOT NULL,\n" +
                "  TELEFONO VARCHAR(25) NOT NULL,\n" +
                "  CARRERA VARCHAR(50) NOT NULL\n" +
                ");";
        
	statement.execute(sql);
	statement.close();
	connection.close();
    }

}