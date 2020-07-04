package edu.pucmm.market.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Embeddable
@Table(name = "VENTA_DETALLE")
public class VentaDetalle implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @OneToOne
    private Producto producto;
    @Column(name = "Precio")
    private BigDecimal precio;
    @Column(name = "Cantidad")
    private int cantidad;

    public VentaDetalle(Producto producto, BigDecimal precio, int cantidad) {
	this.producto = producto;
	this.precio = precio;
	this.cantidad = cantidad;
    }

    public VentaDetalle() {
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
	return cantidad;
    }

    public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
    }

    public BigDecimal calcularTotal() {
	double total = 0;
	
	total = this.cantidad * this.precio.doubleValue();

	return BigDecimal.valueOf(total).setScale(2, RoundingMode.CEILING);
    }
}
