package edu.pucmm.market.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "VENTA")
public class VentasProductos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Fecha_Compra")
    private Date fechaCompra;
    @Column(name = "Nombre_Cliente")
    private String nombreCliente;
    @OneToMany
    private Set<ProductoComprar> listaProductos;

    public VentasProductos(long id, String nombreCliente, Set<ProductoComprar> listaProductos) {
	this.id = id;
	this.fechaCompra = new Date(System.currentTimeMillis());
	this.nombreCliente = nombreCliente;
	this.listaProductos = listaProductos;
    }

    public VentasProductos() {
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

    public Set<ProductoComprar> getListaProductos() {
	return listaProductos;
    }

    public void setListaProductos(Set<ProductoComprar> listaProductos) {
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