package edu.pucmm.market.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.pucmm.market.data.CarroCompra;
import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.ProductoComprar;

public class SimularDatos {

    public SimularDatos() {
	this.usuarioDefecto();
	this.productosDefecto();
	this.ventasDefecto();
    }

    private void usuarioDefecto() {
	Mercado.crearUsuario("admin", "admin", "admin");
    }

    private void productosDefecto() {
	Mercado.crearEditarProducto(1, "Apio", BigDecimal.valueOf(55.25));
	Mercado.crearEditarProducto(2, "Recaito", BigDecimal.valueOf(20.00));
	Mercado.crearEditarProducto(3, "Pollo", BigDecimal.valueOf(385.95));
	Mercado.crearEditarProducto(4, "Paquete de pan", BigDecimal.valueOf(80.20));
	Mercado.crearEditarProducto(5, "Queso", BigDecimal.valueOf(90.15));
	Mercado.crearEditarProducto(6, "Manzana", BigDecimal.valueOf(15.15));
	Mercado.crearEditarProducto(7, "Piña", BigDecimal.valueOf(45.00));
    }

    private void ventasDefecto() {
	CarroCompra carro = new CarroCompra(1);
	List<ProductoComprar> lista = new ArrayList<ProductoComprar>();
	carro.setListaProductos(lista);
	lista.add(new ProductoComprar(Mercado.getProductos().get(1), 5));
	lista.add(new ProductoComprar(Mercado.getProductos().get(3), 11));
	lista.add(new ProductoComprar(Mercado.getProductos().get(5), 3));
	Mercado.procesarCompra("Jorge", carro);

	CarroCompra carroBibi = new CarroCompra(2);
	List<ProductoComprar> listaBibi = new ArrayList<ProductoComprar>();
	carroBibi.setListaProductos(listaBibi);
	listaBibi.add(new ProductoComprar(Mercado.getProductos().get(1), 4));
	Mercado.procesarCompra("Bibi", carroBibi);
	
	CarroCompra carroJohn = new CarroCompra(3);
	List<ProductoComprar> listaJohn = new ArrayList<ProductoComprar>();
	carroJohn.setListaProductos(listaJohn);
	listaJohn.add(new ProductoComprar(Mercado.getProductos().get(1), 5));
	listaJohn.add(new ProductoComprar(Mercado.getProductos().get(2), 9));
	listaJohn.add(new ProductoComprar(Mercado.getProductos().get(3), 3));
	listaJohn.add(new ProductoComprar(Mercado.getProductos().get(4), 1));
	listaJohn.add(new ProductoComprar(Mercado.getProductos().get(5), 8));
	Mercado.procesarCompra("John", carroJohn);
    }
}