package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CarroCompra {

    private long id;
    private List<ProductoComprar> listaProductos;

    public CarroCompra(long id) {
	this.id = (long) Math.random() + Long.MAX_VALUE;
	this.listaProductos = new ArrayList<ProductoComprar>();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public List<ProductoComprar> getListaProductos() {
	return listaProductos;
    }
    
    public void setListaProductos(List<ProductoComprar> listaProductos) {
	this.listaProductos = listaProductos;
    }

    public BigDecimal calcularTotal() {
	BigDecimal total = new BigDecimal(0);

	for (ProductoComprar compra : this.listaProductos) {
	    total = total.add(new BigDecimal(compra.getCantidad() * compra.getProducto().getPrecio().doubleValue()));
	}

	return total.setScale(2, RoundingMode.CEILING);
    }

    public void agregarProducto(Producto producto, int cantidad) {
	ProductoComprar compra = this.buscarProducto(producto.getId());

	if (compra != null) {
	    compra.setCantidad(compra.getCantidad() + cantidad);
	} else {
	    this.listaProductos.add(new ProductoComprar(producto, cantidad));
	}
    }

    private ProductoComprar buscarProducto(int id) {
	for (ProductoComprar compra : listaProductos) {
	    if (compra.getProducto().getId() == id) {
		return compra;
	    }
	}

	return null;
    }

    public void limpiarCarrito() {
	this.listaProductos = new ArrayList<ProductoComprar>();
    }

    public void eliminarProducto(Producto producto) {
	this.listaProductos.remove(this.buscarProducto(producto.getId()));
    }

    public int cantidadProductos() {
	int cantidad = 0;

	for (ProductoComprar productoComprar : this.listaProductos) {
	    cantidad += productoComprar.getCantidad();
	}

	return cantidad;
    }
}