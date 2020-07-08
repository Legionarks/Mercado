package edu.pucmm.market.data;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Embeddable
@Table(name = "PRODUCTO")
public class Producto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Producto")
    private int id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion")
    private String descripcion;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "FOTO_PRODUCTO")
    private Set<Foto> fotos;
    
    public Producto(String nombre, String descripcion, Set<Foto> fotos) {
	this.nombre = nombre;
	this.descripcion = descripcion;
	this.fotos = fotos;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Foto> getFotos() {
	return fotos;
    }

    public void setFotos(Set<Foto> fotos) {
	this.fotos = fotos;
    }
    
    public Foto buscarFoto(String nombre) {
	Foto foto = null;
	
	for (Foto auxFoto : this.fotos) {
	    if (auxFoto.getNombre().equals(nombre)) {
		foto = auxFoto;
	    }
	}
	
	return foto;
    }
    
    public boolean eliminarFoto(String nombre) {
	boolean ok = false;
	Foto foto = this.buscarFoto(nombre);
	
	if (foto != null) {
	    this.fotos.remove(foto);
	    ok = true;
	}
	
	return ok;
    }
}