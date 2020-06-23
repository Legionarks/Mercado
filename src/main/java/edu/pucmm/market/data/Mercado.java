package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.List;

import edu.pucmm.market.services.ProductoServicio;
import edu.pucmm.market.services.UsuarioServicio;
import edu.pucmm.market.services.VentaServicio;

public class Mercado {

    public Mercado() {
    }

    public static List<VentasProductos> getVentasProductos() {
	return VentaServicio.getVentas();
    }

    public static List<Producto> getProductos() {
	return ProductoServicio.getProductos(false);
    }

    public static boolean eliminarProducto(int id) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (auxProducto != null) {
	    ok = ProductoServicio.eliminarProducto(auxProducto);
	}

	return ok;
    }

    public static Producto buscarProducto(int id) {
	return ProductoServicio.buscarProducto(id, false);
    }

    public static boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (id == -1 || auxProducto == null) {
	    auxProducto = new Producto(1, nombre, precio);
	    ok = ProductoServicio.crearProducto(auxProducto, false);
	} else {
	    auxProducto.setNombre(nombre);
	    auxProducto.setPrecio(precio);
	    ok = ProductoServicio.editarProducto(auxProducto);
	}

	return ok;
    }

    public static Usuario autenticarUsuario(String idUsuario, String password) {
	Usuario usuario = UsuarioServicio.autenticarUsuario(idUsuario, password);
	System.out.println(usuario);

	return usuario;
    }

    public static boolean procesarCompra(String cliente, CarroCompra carrito) {
	boolean ok = false;
	VentasProductos ventasProductos;

	if (!(carrito.getListaProductos().isEmpty())) {
	    ventasProductos = new VentasProductos(1, cliente, carrito.getListaProductos());
	    ok = VentaServicio.crearVenta(ventasProductos);
	    carrito.limpiarCarrito();
	}

	return ok;
    }

    public static VentasProductos buscarVenta(long id) {
	return VentaServicio.buscarVenta(id);
    }

    public static boolean crearUsuario(String usuario, String nombre, String password) {
	boolean ok = false;

	ok = UsuarioServicio.crearUsuario(new Usuario(usuario, nombre, password));

	return ok;
    }
}