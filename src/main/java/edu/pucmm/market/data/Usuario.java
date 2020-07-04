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
    @Column(name = "Contrase�a")
    private String contrase�a;

    public Usuario(String usuario, String nombre, String contrase�a) {
	this.usuario = usuario;
	this.nombre = nombre;
	this.contrase�a = contrase�a;
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

    public String getContrase�a() {
	return contrase�a;
    }

    public void setContrase�a(String contrase�a) {
	this.contrase�a = contrase�a;
    }
}