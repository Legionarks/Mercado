package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pucmm.market.data.Producto;

public class ProductoServicio {

    public static List<Producto> getProductos(boolean comprado) {
	List<Producto> lista = new ArrayList<Producto>();
	Producto producto;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    if (!(comprado)) {
		query = "SELECT * FROM PRODUCTO";
	    } else {
		query = "SELECT * FROM PRODUCTO_COMPRADO";
	    }
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		producto = new Producto();
		producto.setId(resultSet.getInt("ID_PRODUCTO"));
		producto.setNombre(resultSet.getString("NOMBRE"));
		producto.setPrecio(resultSet.getBigDecimal("PRECIO"));

		lista.add(producto);
	    }
	} catch (SQLException e) {
	    Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return lista;
    }

    public static Producto buscarProducto(int id, boolean comprado) {
	Producto producto = null;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    if (!(comprado)) {
		query = "SELECT * FROM PRODUCTO WHERE ID_PRODUCTO = ?";

	    } else {
		query = "SELECT * FROM PRODUCTO_COMPRADO WHERE ID_PRODUCTO = ?";
	    }
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setInt(1, id);

	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		producto = new Producto();
		producto.setId(resultSet.getInt("ID_PRODUCTO"));
		producto.setNombre(resultSet.getString("NOMBRE"));
		producto.setPrecio(resultSet.getBigDecimal("PRECIO"));
	    }
	} catch (SQLException e) {
	    Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return producto;
    }

    public static boolean crearProducto(Producto producto, boolean comprado) {
	boolean ok = false;
	int filas;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	
	producto.setId(generarIdProducto(comprado));

	try {
	    if (!(comprado)) {
		query = "INSERT INTO PRODUCTO(ID_PRODUCTO, NOMBRE, PRECIO) VALUES(?,?,?)";
	    } else {
		query = "INSERT INTO PRODUCTO_COMPRADO(ID_PRODUCTO, NOMBRE, PRECIO) VALUES(?,?,?)";		
	    }
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setInt(1, producto.getId());
	    prepareStatement.setString(2, producto.getNombre());
	    prepareStatement.setBigDecimal(3, producto.getPrecio());

	    filas = prepareStatement.executeUpdate();
	    ok = filas > 0;
	} catch (SQLException e) {
	    Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ok;
    }

    public static boolean editarProducto(Producto producto) {
	boolean ok = false;
	int filas;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;

	try {
	    query = "UPDATE PRODUCTO SET NOMBRE = ?, PRECIO = ? WHERE ID_PRODUCTO = ?";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setString(1, producto.getNombre());
	    prepareStatement.setBigDecimal(2, producto.getPrecio());
	    prepareStatement.setInt(3, producto.getId());

	    filas = prepareStatement.executeUpdate();
	    ok = filas > 0;
	} catch (SQLException e) {
	    Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ok;
    }

    public static boolean eliminarProducto(Producto producto) {
	boolean ok = false;
	int filas;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;

	try {
	    query = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO = ?";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setInt(1, producto.getId());

	    filas = prepareStatement.executeUpdate();
	    ok = filas > 0;
	} catch (SQLException e) {
	    Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ok;
    }
    
    private static int generarIdProducto(boolean comprado) {
	int id;

	do {
	    id = (int) (Math.random() * Integer.MAX_VALUE);
	} while (buscarProducto(id, comprado) != null);

	return id;
    }
}