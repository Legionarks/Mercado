package edu.pucmm.market.data;

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
}