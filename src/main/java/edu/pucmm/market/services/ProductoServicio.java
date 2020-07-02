package edu.pucmm.market.services;

import java.util.List;

import edu.pucmm.market.data.Producto;

public class ProductoServicio extends DBMServicio<Producto> {

    public ProductoServicio() {
	super(Producto.class);
    }

    public List<Producto> getProductos(boolean comprado) {        
        return buscarTodos();
    }

    public Producto buscarProducto(int id, boolean comprado) {
	return buscar(id);
    }

    public boolean crearProducto(Producto producto, boolean comprado) {
	return crear(producto);
    }

    public boolean editarProducto(Producto producto) {
	return editar(producto);
    }

    public boolean eliminarProducto(Producto producto) {
	return eliminar(producto);
    }
}