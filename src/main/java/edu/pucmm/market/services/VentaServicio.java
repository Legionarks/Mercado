package edu.pucmm.market.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.pucmm.market.data.Producto;
import edu.pucmm.market.data.ProductoComprar;
import edu.pucmm.market.data.VentasProductos;

public class VentaServicio {

    public static List<VentasProductos> getVentas() {
	List<VentasProductos> lista = new ArrayList<VentasProductos>();
	VentasProductos ventasProductos;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    query = "SELECT * FROM VENTA";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		ventasProductos = new VentasProductos();
		ventasProductos.setId(resultSet.getLong("ID_VENTA"));
		ventasProductos.setFechaCompra(resultSet.getDate("FECHA_COMPRA"));
		ventasProductos.setNombreCliente(resultSet.getString("NOMBRE_CLIENTE"));
		ventasProductos.setListaProductos(getVentaProductos(resultSet.getLong("ID_VENTA")));

		lista.add(ventasProductos);
	    }
	} catch (SQLException e) {
	    Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return lista;
    }

    private static List<ProductoComprar> getVentaProductos(long id) {
	List<ProductoComprar> lista = new ArrayList<ProductoComprar>();
	ProductoComprar productoComprar;
	Producto producto;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    query = "SELECT * FROM VENTA_PRODUCTO WHERE ID_VENTA = ?";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setLong(1, id);

	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		productoComprar = new ProductoComprar();
		producto = ProductoServicio.buscarProducto(resultSet.getInt("ID_PRODUCTO"), true);
		productoComprar.setProducto(producto);
		productoComprar.setCantidad(resultSet.getInt("CANTIDAD"));

		lista.add(productoComprar);
	    }
	} catch (SQLException e) {
	    Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return lista;
    }

    public static VentasProductos buscarVenta(long id) {
	VentasProductos ventasProductos = null;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;
	ResultSet resultSet;

	try {
	    query = "SELECT * FROM VENTA WHERE ID_VENTA = ?";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setLong(1, id);

	    resultSet = prepareStatement.executeQuery();

	    while (resultSet.next()) {
		ventasProductos = new VentasProductos();
		ventasProductos.setId(resultSet.getLong("ID_VENTA"));
		ventasProductos.setFechaCompra(resultSet.getDate("FECHA_COMPRA"));
		ventasProductos.setNombreCliente(resultSet.getString("NOMBRE_CLIENTE"));
		ventasProductos.setListaProductos(getVentaProductos(resultSet.getLong("ID_VENTA")));
	    }
	} catch (SQLException e) {
	    Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ventasProductos;
    }

    public static boolean crearVenta(VentasProductos venta) {
	boolean ok = false;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;

	venta.setId(generarIdVenta());

	try {
	    query = "INSERT INTO VENTA(ID_VENTA, FECHA_COMPRA, NOMBRE_CLIENTE) VALUES(?,?,?)";
	    connection = DBServicio.getConexion();

	    prepareStatement = connection.prepareStatement(query);
	    prepareStatement.setLong(1, venta.getId());
	    prepareStatement.setDate(2, new Date(venta.getFechaCompra().getTime()));
	    prepareStatement.setString(3, venta.getNombreCliente());

	    prepareStatement.executeUpdate();
	    ok = venderProductos(venta.getId(), venta.getListaProductos());
	} catch (SQLException e) {
	    Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	    }
	}

	return ok;
    }

    private static long generarIdVenta() {
	long id = 0;

	do {
	    id = (long) (Math.random() * Long.MAX_VALUE);
	} while (buscarVenta(id) != null);

	return id;
    }

    private static boolean venderProductos(long idVenta, List<ProductoComprar> listaProductos) {
	boolean ok = false;
	int filas;

	Connection connection = null;
	String query;

	PreparedStatement prepareStatement;

	for (ProductoComprar productoComprar : listaProductos) {
	    ProductoServicio.crearProducto(productoComprar.getProducto(), true);

	    try {
		query = "INSERT INTO VENTA_PRODUCTO(ID_VENTA, ID_PRODUCTO, CANTIDAD) VALUES(?,?,?)";
		connection = DBServicio.getConexion();

		prepareStatement = connection.prepareStatement(query);
		prepareStatement.setLong(1, idVenta);
		prepareStatement.setInt(2, productoComprar.getProducto().getId());
		prepareStatement.setInt(3, productoComprar.getCantidad());

		filas = prepareStatement.executeUpdate();
		ok = filas > 0;
	    } catch (SQLException e) {
		Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
	    } finally {
		try {
		    connection.close();
		} catch (SQLException e) {
		    Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, e);
		}
	    }
	}

	return ok;
    }
}