package edu.pucmm.market.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VENTA")
public class Venta implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Venta")
    private long id;
    @Column(name = "Fecha_Compra")
    private Date fechaCompra;
    @Column(name = "Nombre_Cliente")
    private String nombreCliente;
    @ElementCollection
    @CollectionTable(name = "VENTA_DETALLE")
    private Set<VentaDetalle> ventaDetalles;

    public Venta(String nombreCliente, Set<VentaDetalle> ventaDetalles) {
	this.fechaCompra = new Date(System.currentTimeMillis());
	this.nombreCliente = nombreCliente;
	this.ventaDetalles = ventaDetalles;
    }

    public Venta() {
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

    public Set<VentaDetalle> getVentaDetalles() {
	return ventaDetalles;
    }

    public void setVentaDetalles(Set<VentaDetalle> ventaDetalles) {
	this.ventaDetalles = ventaDetalles;
    }

    public BigDecimal calcularTotal() {
	BigDecimal total = new BigDecimal(0);

	for (VentaDetalle detalle : this.ventaDetalles) {
	    total = total.add(new BigDecimal(detalle.getCantidad() * detalle.getPrecio().doubleValue()));
	}

	return total.setScale(2, RoundingMode.CEILING);
    }
}