package edu.pucmm.market.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO_COMPRA")
public class ProductoComprar implements Serializable {

    @Id
    private Producto producto;
    @Column(name = "Cantidad")
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
