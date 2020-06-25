package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pucmm.market.data.Usuario;

public class UsuarioServicio {

    public static Usuario autenticarUsuario(String idUsuario) {
	Usuario usuario = null;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    query = "SELECT * FROM USUARIO WHERE ID_USUARIO = ?";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setString(1, idUsuario);

	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		usuario = new Usuario();
		usuario.setUsuario(resultSet.getString("ID_USUARIO"));
		usuario.setNombre(resultSet.getString("NOMBRE"));
		usuario.setPassword(resultSet.getString("PASSWORD"));
	    }
	} catch (SQLException e) {
	    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return usuario;
    }

    public static boolean crearUsuario(Usuario usuario) {
	boolean ok = false;
	int filas;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;

	try {
	    query = "INSERT INTO USUARIO(ID_USUARIO, NOMBRE, PASSWORD) VALUES(?,?,?)";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setString(1, usuario.getUsuario());
	    prepareStatement.setString(2, usuario.getNombre());
	    prepareStatement.setString(3, usuario.getPassword());

	    filas = prepareStatement.executeUpdate();
	    ok = filas > 0;
	} catch (SQLException e) {
	    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ok;
    }
}