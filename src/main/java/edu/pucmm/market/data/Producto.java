package edu.pucmm.market.data;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO")
public class Producto {

    @Id
    private int id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Precio")
    private BigDecimal precio;

    public Producto(int id, String nombre, BigDecimal precio) {
	this.id = id;
	this.nombre = nombre;
	this.precio = precio;
    }

    public Producto() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
	return precio;
    }

    public void setPrecio(BigDecimal precio) {
	this.precio = precio;
    }
}