package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductoCarro {

	private ProductoExistencia existencia;
	private int cantidad;

	public ProductoCarro(ProductoExistencia existencia, int cantidad) {
		this.existencia = existencia;
		this.cantidad = cantidad;
	}

	public ProductoExistencia getExistencia() {
		return existencia;
	}

	public void setExistencia(ProductoExistencia existencia) {
		this.existencia = existencia;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal calcularTotal() {
		double total = 0;

		total += this.cantidad * this.existencia.getPrecio().doubleValue();

		return BigDecimal.valueOf(total).setScale(2, RoundingMode.CEILING);
	}
}