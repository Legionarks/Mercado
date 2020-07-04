package edu.pucmm.market.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    private String usuario;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Contraseña")
    private String contraseña;

    public Usuario(String usuario, String nombre, String contraseña) {
	this.usuario = usuario;
	this.nombre = nombre;
	this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public String getUsuario() {
	return usuario;
    }

    public void setUsuario(String usuario) {
	this.usuario = usuario;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getContraseña() {
	return contraseña;
    }

    public void setContraseña(String contraseña) {
	this.contraseña = contraseña;
    }
}