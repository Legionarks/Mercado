package edu.pucmm.market.data;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO_EXISTENCIA")
public class ProductoExistencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	@OneToOne
	private Producto producto;
	@Column(name = "Precio")
	private BigDecimal precio;

	public ProductoExistencia(Producto producto, BigDecimal precio) {
		this.producto = producto;
		this.precio = precio;
	}

	public ProductoExistencia() {
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
}