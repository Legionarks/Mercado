package edu.pucmm.market.utils;

import java.math.BigDecimal;

import org.jasypt.util.text.AES256TextEncryptor;

import edu.pucmm.market.data.Carro;
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
	this.mercado.crearEditarProducto(1, "Apio", BigDecimal.valueOf(55.25));
	this.mercado.crearEditarProducto(2, "Recaito", BigDecimal.valueOf(20.00));
	this.mercado.crearEditarProducto(3, "Pollo", BigDecimal.valueOf(385.95));
	this.mercado.crearEditarProducto(4, "Paquete de pan", BigDecimal.valueOf(80.20));
	this.mercado.crearEditarProducto(5, "Queso", BigDecimal.valueOf(90.15));
	this.mercado.crearEditarProducto(6, "Manzana", BigDecimal.valueOf(15.15));
	this.mercado.crearEditarProducto(7, "Piña", BigDecimal.valueOf(45.00));	
    }

    private void ventasDefecto() {
	Carro carro;
	
	carro = new Carro();
	carro.agregarProducto(this.mercado.getExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.getExistencias().get(3), 1);
	carro.agregarProducto(this.mercado.getExistencias().get(5), 7);
	this.mercado.procesarCompra("Jorge", carro);
	
	carro = new Carro();
	carro.agregarProducto(this.mercado.getExistencias().get(2), 5);
	this.mercado.procesarCompra("Bibi", carro);
	
	carro = new Carro();
	carro.agregarProducto(this.mercado.getExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.getExistencias().get(5), 2);
	carro.agregarProducto(this.mercado.getExistencias().get(4), 7);
	carro.agregarProducto(this.mercado.getExistencias().get(2), 14);
	carro.agregarProducto(this.mercado.getExistencias().get(1), 61);
	carro.agregarProducto(this.mercado.getExistencias().get(3), 1);
	this.mercado.procesarCompra("John", carro);
    }
}