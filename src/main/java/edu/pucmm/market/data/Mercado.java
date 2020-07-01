package edu.pucmm.market.data;

import java.math.BigDecimal;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.pucmm.market.services.ProductoServicio;
import edu.pucmm.market.services.UsuarioServicio;
import edu.pucmm.market.services.VentaServicio;

public class Mercado {

    public Mercado() {
    }

    public List<VentasProductos> getVentasProductos() {
	return VentaServicio.getVentas();
    }

    public List<Producto> getProductos() {
	return ProductoServicio.getProductos(false);
    }

    public boolean eliminarProducto(int id) {
	boolean ok = false;
	Producto auxProducto = buscarProducto(id);

	if (auxProducto != null) {
	    ok = ProductoServicio.eliminarProducto(auxProducto);
	}

	return ok;
    }

    public Producto buscarProducto(int id) {
	return ProductoServicio.buscarProducto(id, false);
    }

    public boolean crearEditarProducto(int id, String nombre, BigDecimal precio) {
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

    public Usuario autenticarUsuario(String idUsuario, String password, boolean encrypted) {
	Usuario usuario = UsuarioServicio.autenticarUsuario(idUsuario);
	StrongPasswordEncryptor passwordEncryptor;
	String encryptedPassword;

	if (usuario != null) {
	    passwordEncryptor = new StrongPasswordEncryptor();
	    encryptedPassword = passwordEncryptor.encryptPassword(usuario.getPassword());
	    
	    if (encrypted ? passwordEncryptor.checkPassword(usuario.getPassword(), password)
		    : passwordEncryptor.checkPassword(password, encryptedPassword)) {
		return usuario;
	    } else {
		usuario = null;
	    }
	}

	return usuario;
    }

    public boolean procesarCompra(String cliente, CarroCompra carrito) {
	boolean ok = false;
	VentasProductos ventasProductos;

	if (!(carrito.getListaProductos().isEmpty())) {
	    ventasProductos = new VentasProductos(1, cliente, carrito.getListaProductos());
	    ok = VentaServicio.crearVenta(ventasProductos);
	    carrito.limpiarCarrito();
	}

	return ok;
    }

    public VentasProductos buscarVenta(long id) {
	return VentaServicio.buscarVenta(id);
    }

    public boolean crearUsuario(String usuario, String nombre, String password) {
	boolean ok = false;

	ok = UsuarioServicio.crearUsuario(new Usuario(usuario, nombre, password));

	return ok;
    }
}