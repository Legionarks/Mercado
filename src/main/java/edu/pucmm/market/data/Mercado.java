package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.pucmm.market.services.ProductoServicio;

public class Mercado {

    private static final List<Usuario> USUARIOS = new ArrayList<Usuario>();
    private static final List<VentasProductos> VENTAS_PRODUCTOS = new ArrayList<VentasProductos>();
    // private static final List<Producto> PRODUCTOS = new ArrayList<Producto>();

    public Mercado() {
    }

    public static List<Usuario> getUsuarios() {
	return USUARIOS;
    }

    public static List<VentasProductos> getVentasProductos() {
	return VENTAS_PRODUCTOS;
    }

    public static List<Producto> getProductos() {
	// return PRODUCTOS;
	return ProductoServicio.getProductos();
    }

    public static boolean eliminarProducto(int id) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (auxProducto != null) {
	    // PRODUCTOS.remove(auxProducto);
	    // ok = true;
	    ok = ProductoServicio.eliminarProducto(auxProducto);
	}

	return ok;
    }

    public static Producto buscarProducto(int id) {
	// for (Producto auxProducto : PRODUCTOS) {
	// if (auxProducto.getId() == id) {
	// return auxProducto;
	// }
	// }
	//
	// return null;
	return ProductoServicio.buscarProducto(id);
    }

    private static int generarIdProducto() {
	int id;

	do {
	    id = (int) (Math.random() * Integer.MAX_VALUE);
	} while (buscarProducto(id) != null);

	return id;
    }

    public static boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (id == -1 || auxProducto == null) {
	    auxProducto = new Producto(generarIdProducto(), nombre, precio);
	    // PRODUCTOS.add(auxProducto);
	    // ok = true;
	    ok = ProductoServicio.crearProducto(auxProducto);
	} else {
	    // auxProducto.setNombre(nombre);
	    // auxProducto.setPrecio(precio);
	    // ok = true;
	    ok = ProductoServicio.editarProducto(auxProducto);
	}

	return ok;
    }

    public static Usuario autenticarUsuario(String nombreUsuario, String password) {
	Usuario usuario = buscarUsuario(nombreUsuario);

	if (usuario != null) {
	    if (usuario.getPassword().equals(password)) {
		return usuario;
	    }
	}

	return null;
    }

    private static Usuario buscarUsuario(String nombreUsuario) {
	for (Usuario auxUsuario : USUARIOS) {
	    if (auxUsuario.getUsuario().equals(nombreUsuario)) {
		return auxUsuario;
	    }
	}

	return null;
    }

    public static void procesarCompra(String cliente, CarroCompra carrito) {
	if (!(carrito.getListaProductos().isEmpty())) {
	    VENTAS_PRODUCTOS.add(new VentasProductos(generarIdVenta(), cliente, carrito.getListaProductos()));
	    carrito.limpiarCarrito();
	}
    }

    private static long generarIdVenta() {
	long id = 0;

	do {
	    id = (long) (Math.random() * Long.MAX_VALUE);
	} while (buscarVenta(id) != null);

	return id;
    }

    private static VentasProductos buscarVenta(long id) {
	for (VentasProductos auxVenta : VENTAS_PRODUCTOS) {
	    if (auxVenta.getId() == id) {
		return auxVenta;
	    }
	}

	return null;
    }
}