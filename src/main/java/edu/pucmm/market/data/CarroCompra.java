package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class CarroCompra {

    private long id;
    private Set<ProductoCarro> carro;

    public CarroCompra() {
	this.id = (long) Math.random() + Long.MAX_VALUE;
	this.carro = new HashSet<ProductoCarro>();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Set<ProductoCarro> getCarro() {
	return carro;
    }

    public void setCarro(Set<ProductoCarro> carro) {
	this.carro = carro;
    }

    public BigDecimal calcularTotal() {
	BigDecimal total = new BigDecimal(0);

	for (ProductoCarro auxCarro : this.carro) {
	    total = total.add(new BigDecimal(auxCarro.getCantidad() * auxCarro.getExistencia().getPrecio().doubleValue()));
	}

	return total.setScale(2, RoundingMode.CEILING);
    }

    public void agregarProducto(ProductoExistencia existencia, int cantidad) {
	ProductoCarro auxCarro = this.buscarProducto(existencia.getProducto());

	if (auxCarro != null) {
	    auxCarro.setCantidad(auxCarro.getCantidad() + cantidad);
	} else {
	    this.carro.add(new ProductoCarro(existencia , cantidad));
	}
    }

    private ProductoCarro buscarProducto(Producto producto) {
	for (ProductoCarro auxCarro : this.carro) {
	    if (auxCarro.getExistencia().getProducto().equals(producto)) {
		return auxCarro;
	    }
	}

	return null;
    }

    public void limpiarCarrito() {
	this.carro = new HashSet<ProductoCarro>();
    }

    public void eliminarProducto(Producto producto) {
	this.carro.remove(this.buscarProducto(producto));
    }

    public int cantidadProductos() {
	int cantidad = 0;

	for (ProductoCarro producto : this.carro) {
	    cantidad += producto.getCantidad();
	}

	return cantidad;
    }
}