package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

public class VentasProductos {

    private long id;
    private Date fechaCompra;
    private String nombreCliente;
    private List<ProductoComprar> listaProductos;

    public VentasProductos(long id, String nombreCliente, List<ProductoComprar> listaProductos) {
	this.id = id;
	this.fechaCompra = new Date(System.currentTimeMillis());
	this.nombreCliente = nombreCliente;
	this.listaProductos = listaProductos;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Date getFechaCompra() {
	return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
	this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
	return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
	this.nombreCliente = nombreCliente;
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
}