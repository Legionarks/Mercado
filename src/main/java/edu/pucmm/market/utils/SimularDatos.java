package edu.pucmm.market.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.data.Carro;
import edu.pucmm.market.data.Foto;
import edu.pucmm.market.data.Mercado;

public class SimularDatos {

    private final Mercado mercado;
    private final AES256TextEncryptor encriptador;

    public SimularDatos(Mercado mercado, AES256TextEncryptor encriptador) {
	this.mercado = mercado;
	this.encriptador = encriptador;

	this.usuarioDefecto();
	this.productosDefecto();
	this.ventasDefecto();
    }

    public Mercado getMercado() {
	return mercado;
    }

    private void usuarioDefecto() {
	this.mercado.crearUsuario("admin", "admin", encriptador.encrypt("admin"));
    }

    private void productosDefecto() {
	Set<Foto> fotos = new HashSet<Foto>();
	this.mercado.crearEditarProducto(1, "Apio", "", BigDecimal.valueOf(55.25), fotos);
	this.mercado.crearEditarProducto(2, "Recaito", "", BigDecimal.valueOf(20.00), fotos);
	this.mercado.crearEditarProducto(3, "Pollo", "", BigDecimal.valueOf(385.95), fotos);
	this.mercado.crearEditarProducto(4, "Paquete de pan", "", BigDecimal.valueOf(80.20), fotos);
	this.mercado.crearEditarProducto(5, "Queso", "", BigDecimal.valueOf(90.15), fotos);
	this.mercado.crearEditarProducto(6, "Manzana", "", BigDecimal.valueOf(15.15), fotos);
	this.mercado.crearEditarProducto(7, "Piña", "", BigDecimal.valueOf(45.00), fotos);
	this.mercado.crearEditarProducto(8, "Queso", "", BigDecimal.valueOf(90.15), fotos);
	this.mercado.crearEditarProducto(9, "Manzana", "", BigDecimal.valueOf(15.15), fotos);
	this.mercado.crearEditarProducto(10, "Piña", "", BigDecimal.valueOf(45.00), fotos);
	this.mercado.crearEditarProducto(11, "Queso", "", BigDecimal.valueOf(90.15), fotos);
	this.mercado.crearEditarProducto(12, "Manzana", "", BigDecimal.valueOf(15.15), fotos);
	this.mercado.crearEditarProducto(13, "Piña", "", BigDecimal.valueOf(45.00), fotos);
    }

    private void ventasDefecto() {
	Carro carro;

	carro = new Carro();
	carro.agregarProducto(this.mercado.buscarExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.buscarExistencias().get(3), 1);
	carro.agregarProducto(this.mercado.buscarExistencias().get(5), 7);
	this.mercado.procesarCompra("Jorge", carro);

	carro = new Carro();
	carro.agregarProducto(this.mercado.buscarExistencias().get(2), 5);
	this.mercado.procesarCompra("Bibi", carro);

	carro = new Carro();
	carro.agregarProducto(this.mercado.buscarExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.buscarExistencias().get(5), 2);
	carro.agregarProducto(this.mercado.buscarExistencias().get(4), 7);
	carro.agregarProducto(this.mercado.buscarExistencias().get(2), 14);
	carro.agregarProducto(this.mercado.buscarExistencias().get(1), 61);
	carro.agregarProducto(this.mercado.buscarExistencias().get(3), 1);
	this.mercado.procesarCompra("John", carro);
    }

    private String archivoBase64(String nombre) {
	String base64 = "";
	
	try {
	    File archivo = new File(nombre);
	    byte[] contenido = Files.readAllBytes(archivo.toPath());
	    base64 = Base64.getEncoder().encodeToString(contenido);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return base64;
    }
}