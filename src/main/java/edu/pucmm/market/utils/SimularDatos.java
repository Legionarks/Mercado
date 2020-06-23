package edu.pucmm.market.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.pucmm.market.data.Mercado;
import edu.pucmm.market.data.Producto;
import edu.pucmm.market.data.ProductoComprar;
import edu.pucmm.market.data.Usuario;
import edu.pucmm.market.data.VentasProductos;

public class SimularDatos {

    public SimularDatos() {
	this.usuarioDefecto();
	this.productosDefecto();
	this.ventasDefecto();
    }

    private void usuarioDefecto() {
	Mercado.getUsuarios().add(new Usuario("admin", "admin", "admin"));
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
	List<ProductoComprar> carro = new ArrayList<ProductoComprar>();
	carro.add(new ProductoComprar(Mercado.getProductos().get(1), 5));
	carro.add(new ProductoComprar(Mercado.getProductos().get(3), 11));
	carro.add(new ProductoComprar(Mercado.getProductos().get(5), 3));
	Mercado.getVentasProductos().add(new VentasProductos(1, "Jorge", carro));

	List<ProductoComprar> carroBibi = new ArrayList<ProductoComprar>();
	carroBibi.add(new ProductoComprar(Mercado.getProductos().get(1), 4));
	Mercado.getVentasProductos().add(new VentasProductos(2, "Bibi", carroBibi));

	List<ProductoComprar> carroJohn = new ArrayList<ProductoComprar>();
	carroJohn.add(new ProductoComprar(Mercado.getProductos().get(1), 5));
	carroJohn.add(new ProductoComprar(Mercado.getProductos().get(2), 9));
	carroJohn.add(new ProductoComprar(Mercado.getProductos().get(3), 3));
	carroJohn.add(new ProductoComprar(Mercado.getProductos().get(4), 1));
	carroJohn.add(new ProductoComprar(Mercado.getProductos().get(5), 8));
	Mercado.getVentasProductos().add(new VentasProductos(3, "John", carroJohn));
    }
}
