package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductoComprar {

    private Producto producto;
    private int cantidad;

    public ProductoComprar(Producto producto, int cantidad) {
	this.producto = producto;
	this.cantidad = cantidad;
    }

    public ProductoComprar() {
    }

    public Producto getProducto() {
	return producto;
    }

    public void setProducto(Producto producto) {
	this.producto = producto;
    }

    public int getCantidad() {
	return cantidad;
    }

    public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
    }

    public BigDecimal calcularTotal() {
	BigDecimal total = BigDecimal.valueOf(this.cantidad * this.producto.getPrecio().doubleValue());

	return total.setScale(2, RoundingMode.CEILING);
    }
}
