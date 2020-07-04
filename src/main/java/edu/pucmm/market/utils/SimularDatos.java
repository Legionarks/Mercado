package edu.pucmm.market.utils;

import java.math.BigDecimal;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;

public class SimularDatos {
    
    private Mercado mercado;

    public SimularDatos(Mercado mercado) {
	this.mercado = mercado;
	
	this.usuarioDefecto();
	this.productosDefecto();
	this.ventasDefecto();
    }

    public Mercado getMercado() {
	return mercado;
    }

    public void setMercado(Mercado mercado) {
	this.mercado = mercado;
    }

    private void usuarioDefecto() {
	this.mercado.crearUsuario("admin", "admin", "admin");
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
	CarroCompra carro;
	
	carro = new CarroCompra();
	carro.agregarProducto(this.mercado.getExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.getExistencias().get(3), 1);
	carro.agregarProducto(this.mercado.getExistencias().get(5), 7);
	this.mercado.procesarCompra("Jorge", carro);
	
	carro = new CarroCompra();
	carro.agregarProducto(this.mercado.getExistencias().get(2), 5);
	this.mercado.procesarCompra("Bibi", carro);
	
	carro = new CarroCompra();
	carro.agregarProducto(this.mercado.getExistencias().get(0), 5);
	carro.agregarProducto(this.mercado.getExistencias().get(5), 2);
	carro.agregarProducto(this.mercado.getExistencias().get(4), 7);
	carro.agregarProducto(this.mercado.getExistencias().get(2), 14);
	carro.agregarProducto(this.mercado.getExistencias().get(1), 61);
	carro.agregarProducto(this.mercado.getExistencias().get(3), 1);
	this.mercado.procesarCompra("John", carro);
    }
}