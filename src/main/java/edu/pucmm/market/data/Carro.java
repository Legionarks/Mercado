package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class Carro {

    private long id;
    private Set<ProductoCarro> productos;

    public Carro() {
	this.id = (long) Math.random() + Long.MAX_VALUE;
	this.productos = new HashSet<ProductoCarro>();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }
    
    public Set<ProductoCarro> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoCarro> productos) {
        this.productos = productos;
    }

    public BigDecimal calcularTotal() {
	double total = 0;

	for (ProductoCarro compra : this.productos) {
	    total += compra.getCantidad() * compra.getExistencia().getPrecio().doubleValue();
	}

	return BigDecimal.valueOf(total).setScale(2, RoundingMode.CEILING);
    }

    public void agregarProducto(ProductoExistencia existencia, int cantidad) {
	ProductoCarro compra = this.buscarExistencia(existencia);

	if (compra != null) {
	    compra.setCantidad(compra.getCantidad() + cantidad);
	} else {
	    this.productos.add(new ProductoCarro(existencia, cantidad));
	}
    }

    private ProductoCarro buscarExistencia(ProductoExistencia existencia) {
	for (ProductoCarro compra : this.productos) {
	    if (compra.getExistencia().getProducto().getId() == existencia.getProducto().getId()) {
		return compra;
	    }
	}

	return null;
    }

    public void limpiarCarrito() {
	this.productos = new HashSet<ProductoCarro>();
    }

    public void eliminarProducto(ProductoExistencia existencia) {
	this.productos.remove(this.buscarExistencia(existencia));
    }

    public int cantidadProductos() {
	int cantidad = 0;

	for (ProductoCarro compra : this.productos) {
	    cantidad += compra.getCantidad();
	}

	return cantidad;
    }
}