package edu.pucmm.market.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.ProductoComprar;

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
	CarroCompra carro = new CarroCompra(1);
	List<ProductoComprar> lista = new ArrayList<ProductoComprar>();
	carro.setListaProductos(lista);
	lista.add(new ProductoComprar(this.mercado.getProductos().get(1), 5));
	lista.add(new ProductoComprar(this.mercado.getProductos().get(3), 11));
	lista.add(new ProductoComprar(this.mercado.getProductos().get(5), 3));
	this.mercado.procesarCompra("Jorge", carro);

	CarroCompra carroBibi = new CarroCompra(2);
	List<ProductoComprar> listaBibi = new ArrayList<ProductoComprar>();
	carroBibi.setListaProductos(listaBibi);
	listaBibi.add(new ProductoComprar(this.mercado.getProductos().get(1), 4));
	this.mercado.procesarCompra("Bibi", carroBibi);
	
	CarroCompra carroJohn = new CarroCompra(3);
	List<ProductoComprar> listaJohn = new ArrayList<ProductoComprar>();
	carroJohn.setListaProductos(listaJohn);
	listaJohn.add(new ProductoComprar(this.mercado.getProductos().get(1), 5));
	listaJohn.add(new ProductoComprar(this.mercado.getProductos().get(2), 9));
	listaJohn.add(new ProductoComprar(this.mercado.getProductos().get(3), 3));
	listaJohn.add(new ProductoComprar(this.mercado.getProductos().get(4), 1));
	listaJohn.add(new ProductoComprar(this.mercado.getProductos().get(5), 8));
	this.mercado.procesarCompra("John", carroJohn);
    }
}