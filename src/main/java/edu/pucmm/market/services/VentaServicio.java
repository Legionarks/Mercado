package edu.pucmm.market.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.pucmm.market.data.ProductoComprar;
import edu.pucmm.market.data.VentasProductos;

public class VentaServicio extends DBMServicio<VentasProductos>{

    public VentaServicio() {
	super(VentasProductos.class);
    }

    public List<VentasProductos> getVentas() {
	return buscarTodos();
    }

    public VentasProductos buscarVenta(long id) {
	return buscar(id);
    }

    public boolean crearVenta(VentasProductos venta) {
	return crear(venta);
    }

    private static boolean venderProductos(long idVenta, Set<ProductoComprar> listaProductos) {
	boolean ok = false;
	int filas;

	String query;
	query = "INSERT INTO VENTA_PRODUCTO(ID_VENTA, ID_PRODUCTO, CANTIDAD) VALUES(?,?,?)";

	return ok;
    }
}